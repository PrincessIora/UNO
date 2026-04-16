package Model;

public class AIPlayer {

    public Card chooseMove(Player ai, Card topCard, Card.Color activeColor) {

        Card best = null;

        for (Card c : ai.getHand()) {

            if (!GameLogic.isValidMove(c, topCard, activeColor)) continue;

            // Priority system
            switch (c.getValue()) {
                case DRAW_TWO:
                case SKIP:
                case REVERSE:
                    return c; // aggressive play
                case WILD_DRAW_FOUR:
                    best = c;
                    break;
                case WILD:
                    if (best == null) best = c;
                    break;
                default:
                    if (best == null) best = c;
            }
        }

        return best;
    }
}
