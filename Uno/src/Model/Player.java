package Model;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private List<Card> hand = new ArrayList<>();
    private boolean isAI;
    private int score;

    public void drawCard(Deck deck) {
        hand.add(deck.drawCard());
    }

    public void playCard(Card selected) {
        hand.remove(selected);
    }

    public boolean hasUno(){
        return hand.size() == 1;
    }

    public String getName() {
        return name;
    }

    public List<Card> getHand() {
        return hand;
    }

    public boolean isAI() {
        return isAI;
    }

    public int getScore() {
        return score;
    }
}
