package Model;

public class AIPlayer {
    public Card chooseMove(Player ai, Card topCard) {
        for (Card c : ai.getHand()) {
            if (GameLogic.isValidMove(c, topCard)) return c;
        }
        return null; // draw if none valid
    }
}
