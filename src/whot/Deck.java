package whot;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;

public abstract class Deck {

    private final ArrayDeque<Card> deck = new ArrayDeque<>();


    public void sort() {
        ArrayList<Card> tempDeck = new ArrayList<>(deck);
        tempDeck.sort((a, b) -> Integer.compare(a.getRank().getNum(), b.getRank().getNum()));
        deck.clear();
        deck.addAll(tempDeck);
    }

    public void sort(String by) {
        if(by.compareToIgnoreCase("suit") == 0) {
            ArrayList<Card> tempDeck = new ArrayList<>(deck);
            tempDeck.sort((a, b) -> a.getSuit().name().compareTo(b.getSuit().name()));
            deck.clear();
            deck.addAll(tempDeck);
        } else {
            throw new IllegalArgumentException("Invalid sort option: " + by);
        }
    }

    public ArrayDeque<Card> getDeck() {
        return deck;
    }

    public void shuffleCards() {
        ArrayList<Card> tempDeck = new ArrayList<>(deck);
        Collections.shuffle(tempDeck);
        deck.clear();
        deck.addAll(tempDeck);
    }

}
