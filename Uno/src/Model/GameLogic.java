package Model;

public class GameLogic {
    public static boolean isValidMove(Card played, Card top, Card.Color activeColor) {
        if (played.getColor() == Card.Color.WILD) return true;

        return played.getColor() == activeColor
                || played.getValue() == top.getValue();
    }

    public void applyCardEffect(Card card, GameState state) {
        switch (card.getValue()) {
            case SKIP:
                skipNextPlayer(state);
                break;
            case REVERSE:
                reverseDirection(state);
                break;
            case DRAW_TWO:
                forceDraw(state, 2);
                break;
            case WILD_DRAW_FOUR:
                forceDraw(state, 4);
                break;
        }
    }

    public void skipNextPlayer(GameState state) {
        state.advanceTurn();
    }
    public void reverseDirection(GameState state) {
        state.reverseDirection();
    }
    public void forceDraw(GameState state, int count) {
        state.advanceTurn(); // move to next player
        Player next = state.getCurrentPlayer();

        for (int i = 0; i < count; i++){
            next.drawCard(state.getDeck());
        }

        state.advanceTurn(); // SKIP their turn
    }

    public int calculateScore(Player winner, GameState state) {

        int score = 0;

        for (Player p : state.getPlayers()) {
            if (p == winner) continue;

            for (Card c : p.getHand()) {
                switch (c.getValue()) {
                    case ZERO: score += 0; break;
                    case ONE: case TWO: case THREE: case FOUR:
                    case FIVE: case SIX: case SEVEN: case EIGHT: case NINE:
                        score += c.getValue().ordinal();
                        break;
                    case SKIP:
                    case REVERSE:
                    case DRAW_TWO:
                        score += 20;
                        break;
                    case WILD:
                    case WILD_DRAW_FOUR:
                        score += 50;
                        break;
                }
            }
        }

        return score;
    }
}
