package Controller;

import Model.Card;
import Model.GameLogic;
import Model.GameState;
import Model.Player;

public class GameController {
    private GameLogic logic = new GameLogic();
    private GameState state;

    public void onCardClicked(Card selected) {
        Player current = state.getCurrentPlayer();
        if(!current.getHand().contains(selected)) {
            return;
        }
        if (logic.isValidMove(selected, state.getTopCard())) {
            playCard(selected);
            nextTurn();
        } else {
            current.drawCard(state.getDeck());
        }

    }

    private void nextTurn() {
        state.advanceTurn();
    }

    private void playCard(Card selected) {
        Player current = state.getCurrentPlayer();

        current.playCard(selected);
        state.getDeck().getDiscardPile().add(selected);
        state.setTopCard(selected);

        logic.applyCardEffect(selected, state);

        if (current.getHand().isEmpty()) {
            System.out.println(current.getName() + "wins");
        }
    }


}