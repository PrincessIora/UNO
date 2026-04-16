package Model;

public class Card {
    public enum Color { RED, BLUE, GREEN, YELLOW, WILD }
    public enum Value { ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE,
        SKIP, REVERSE, DRAW_TWO, WILD, WILD_DRAW_FOUR }

    private Color color;
    private Value value;

    public Card (Color color, Value value) {
        this.color = color;
        this.value = value;
    }
    public Color getColor() {
        return color;
    }

    public Value getValue() {
        return value;
    }

    public String getImagePath(){
        return "/Resources/" +
                color.name().toLowerCase() + "_" +
                value.name().toLowerCase() + ".png";
    }
}
