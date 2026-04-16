package Model;

import java.util.List;

public class GameState {
    private List<Player> players;
    private Deck deck;
    private int currentPlayerIndex;
    private Card topCard;
    private boolean clockwise;

    public List<Player> getPlayers() {
        return players;
    }

    public Deck getDeck() {
        return deck;
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public void setTopCard(Card topCard) {
        this.topCard = topCard;
    }
    public void advanceTurn(){
        if(clockwise){
            currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        } else {
            currentPlayerIndex = (currentPlayerIndex - 1 + players.size()) % players.size();
        }
    }

    public void reverseDirection(){
            clockwise = !clockwise;
    }

    public Card getTopCard() {
        return topCard;
    }

    public boolean isClockwise() {
        return clockwise;
    }


}
