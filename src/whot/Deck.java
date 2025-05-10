package whot;

import java.util.ArrayList;

public abstract class Deck {

    protected final ArrayList<Card> deck = new ArrayList<>();

    public void sort() {
        deck.sort((a, b) -> Integer.compare(a.getRank().getNum(), b.getRank().getNum()));
    }

    public void sort(String by) {
        if(by.compareToIgnoreCase("suit") == 0) {
            deck.sort((a, b) -> a.getSuit().compareTo(b.getSuit()));
        } else {
            throw new IllegalArgumentException("Invalid sort option: " + by);
        }
    }

    public ArrayList<Card> getDeck() {
        return deck;
    }

}
