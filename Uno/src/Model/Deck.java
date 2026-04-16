package Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private List<Card> drawPile = new ArrayList<>();
    private List<Card> discardPile = new ArrayList<>();

    public void initializeDeck() {
        drawPile.clear();
        discardPile.clear();


        for(Card.Color color: Card.Color.values()) {
            if (color == Card.Color.WILD) continue;

            //Number Cards 19 in each color
            for (int i = 0; i < 19; i++) {
                drawPile.add(new Card(color, Card.Value.ZERO));
                drawPile.add(new Card(color, Card.Value.ONE));
                drawPile.add(new Card(color, Card.Value.TWO));
                drawPile.add(new Card(color, Card.Value.THREE));
                drawPile.add(new Card(color, Card.Value.FOUR));
                drawPile.add(new Card(color, Card.Value.FIVE));
                drawPile.add(new Card(color, Card.Value.SIX));
                drawPile.add(new Card(color, Card.Value.SEVEN));
                drawPile.add(new Card(color, Card.Value.EIGHT));
                drawPile.add(new Card(color, Card.Value.NINE));
            }

            //action cards 2 of each color
            for (int i = 0; i < 2 ; i++) {
                drawPile.add(new Card(color, Card.Value.REVERSE));
                drawPile.add(new Card(color, Card.Value.SKIP));
                drawPile.add(new Card(color, Card.Value.DRAW_TWO));
            }

            // Wild Cards 4 of each
            for (int i = 0; i < 4; i++) {
                drawPile.add(new Card(Card.Color.WILD, Card.Value.WILD));
                drawPile.add(new Card(Card.Color.WILD, Card.Value.WILD_DRAW_FOUR));
            }

        }
    }

    public void shuffle(List<Card> cards) { Collections.shuffle(cards); }

    public void reshuffle() {
        Card top = discardPile.remove(discardPile.size()-1);
        drawPile.addAll(discardPile);
        discardPile.clear();
        discardPile.add(top);

        shuffle(drawPile);
    }

    public Card drawCard() {
        if (drawPile.isEmpty()) {
            reshuffle();
        }
        return drawPile.remove(0);
    }

    public List<Card> getDrawPile() {
        return drawPile;
    }

    public List<Card> getDiscardPile() {
        return discardPile;
    }
}
