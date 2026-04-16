package Model;

public class AIPlayer {

    public Card chooseMove(Player ai, Card topCard) {
        Card fallback = null;

        for (Card c : ai.getHand()) {
            if (GameLogic.isValidMove(c, topCard)) {
                if(c.getColor() != Card.Color.WILD) return c;
                fallback = c;
            }
        }
        return fallback;
    }
}
