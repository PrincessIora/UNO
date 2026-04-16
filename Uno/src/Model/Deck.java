package Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private List<Card> drawPile = new ArrayList<>();
    private List<Card> discardPile = new ArrayList<>();

    public void initializeDeck() { /* create UNO deck */ }
    public void shuffle() { Collections.shuffle(drawPile); }
    public Card drawCard() { return drawPile.remove(0); }
}
