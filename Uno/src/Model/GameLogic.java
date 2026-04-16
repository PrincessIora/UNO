package Model;

public class GameLogic {
    public static boolean isValidMove(Card played, Card topCard) {
        return played.getColor() == topCard.getColor()
                || played.getValue() == topCard.getValue()
                || played.getColor() == Card.Color.WILD;
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
}
