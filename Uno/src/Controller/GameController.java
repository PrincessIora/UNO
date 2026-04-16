package Controller;

import Model.*;
import View.UnoView;

import java.util.List;

public class GameController {
    private GameLogic logic = new GameLogic();
    private GameState state;
    private UnoView view;
    private boolean isProcessingTurn = false;
    private boolean unoWindowActive = false;
    private Player unoPlayer = null;

    public void setView(UnoView view) {
        this.view = view;
    }
    public void setState(GameState state) {
        this.state = state;
    }

    public void onCardClicked(Card selected) {

        if (isProcessingTurn) return;

        Player current = state.getCurrentPlayer();

        if (current.isAI()) return; // 👈 prevent player acting during AI turn

        if (!current.getHand().contains(selected)) return;

        if (GameLogic.isValidMove(selected, state.getTopCard(), state.getActiveColor())) {

            isProcessingTurn = true;

            playCard(selected);

            handlePostTurn(current);

        } else {
            current.drawCard(state.getDeck());
            endTurn();
        }

        view.refreshAll();
    }
    private void nextTurn() {
        state.advanceTurn();
    }

    private void playCard(Card selected) {
        Player current = state.getCurrentPlayer();
        current.setCalledUno(false);
        current.playCard(selected);

        state.getDeck().getDiscardPile().add(selected);
        state.setTopCard(selected);
        if (selected.getColor() == Card.Color.WILD) {
            state.setActiveColor(null); // UI will choose
            view.promptWildColor(current);
        } else {
            state.setActiveColor(selected.getColor());
        }

        logic.applyCardEffect(selected, state);

        // UNO rule tracking
        if (current.getHand().size() == 1) {

            unoWindowActive = true;
            unoPlayer = current;

            view.enableUnoButton(true);

            // ⏳ 2-second grace window
            new Thread(() -> {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ignored) {}

                if (!current.hasCalledUno()) {
                    current.drawCard(state.getDeck());
                    current.drawCard(state.getDeck());
                }

                unoWindowActive = false;
                view.enableUnoButton(false);

                javafx.application.Platform.runLater(() -> view.refreshAll());

            }).start();
        }
    }

public void drawCard() {
        Player current = state.getCurrentPlayer();
        Card drawn = state.getDeck().drawCard();
        current.getHand().add(drawn);

        // Allow immediate play if valid
    if (GameLogic.isValidMove(drawn, state.getTopCard(), state.getActiveColor())) {
        // optional: auto-play for AI or prompt for player
    } else {
        state.advanceTurn();
        processAITurns();
    }
    view.refreshAll();

}

    private AIPlayer aiLogic = new AIPlayer();

    private void processAITurns() {

        while (state.getCurrentPlayer().isAI()) {

            Player ai = state.getCurrentPlayer();
            Card move = aiLogic.chooseMove(
                    ai,
                    state.getTopCard(),
                    state.getActiveColor()
            );

            if (move != null) {
                playCard(move);
            } else {
                ai.drawCard(state.getDeck());
            }

            state.advanceTurn();
        }
    }

    private boolean checkWin(Player p) {
        return p.getHand().isEmpty();
    }
    private void handlePostTurn(Player current) {

        if (checkWin(current)) {
            int points = logic.calculateScore(current, state);
            current.addScore(points);
            view.showWinScreen(current);
            return;
        }

        endTurn();
    }

    private void endTurn() {
        state.advanceTurn();
        processAITurns();
        isProcessingTurn = false;
    }

    public void callUno() {
        if (unoWindowActive && unoPlayer == state.getCurrentPlayer()) {
            unoPlayer.setCalledUno(true);
            unoWindowActive = false;
            view.enableUnoButton(false);
        }
    }

    public void restartGame(List<Player> players) {
        GameState newState = new GameState();
        newState.initializeGame(players);

        this.state = newState;
        this.isProcessingTurn = false;
        this.unoWindowActive = false;

        view.bindState(newState);
        view.refreshAll();
    }

    public void exitGame() {
        javafx.application.Platform.exit();
    }

}