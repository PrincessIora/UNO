package Model;

import java.util.ArrayList;
import java.util.List;

public class GameState {
    private List<Player> players = new ArrayList<Player>();
    private Deck deck;
    private int currentPlayerIndex;
    private Card topCard;
    private boolean clockwise;
    private Card.Color activeColor;
    private boolean unoRequired;
    private Player pendingWinner;

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
        if(players.isEmpty()){
            return null;
        }
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


    public void initializeGame(List<Player> initialPlayers) {
        this.players.clear();
        this.players.addAll(initialPlayers);

        this.deck = new Deck();
        this.deck.initializeDeck();
        this.deck.shuffle(deck.getDrawPile());

        this.currentPlayerIndex = 0;
        this.clockwise = true;

        // give each player cards
        for (Player p : players) {
            for (int i = 0; i < 7; i++) {
                p.drawCard(deck);
            }
        }

        // set starting card
        this.topCard = deck.drawCard();
        deck.getDiscardPile().add(topCard);
        this.activeColor = topCard.getColor();
    }

    public Card.Color getActiveColor() {
        return activeColor;
    }

    public void setActiveColor(Card.Color color) {
        this.activeColor = color;
    }

    public boolean isUnoRequired() {
        return unoRequired;
    }

    public void setUnoRequired(boolean unoRequired) {
        this.unoRequired = unoRequired;
    }



}
