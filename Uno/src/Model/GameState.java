package Model;

import java.util.List;

public class GameState {
    private List<Player> players;
    private Deck deck;
    private int currentPlayerIndex;
    private Card topCard;
    private boolean clockwise;
}
