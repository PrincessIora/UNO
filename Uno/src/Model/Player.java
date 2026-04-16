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

    public Player (String name , boolean isAi){
        this.name = name;
        this.isAI = isAi;
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

    private boolean calledUno = false;

    public boolean hasCalledUno() {
        return calledUno;
    }

    public void setCalledUno(boolean calledUno) {
        this.calledUno = calledUno;
    }

    public void addScore(int points) {
        this.score += points;
    }


}
