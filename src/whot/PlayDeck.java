package whot;

import java.util.ArrayDeque;

public class PlayDeck extends Deck {

    public PlayDeck(Card card){
        getDeck().addLast(card);
    }

    public void addCard(Card card) {
        getDeck().addLast(card);
    }

    public ArrayDeque<Card> fillMainDeck() {
        Card topCard = getDeck().removeLast();
        ArrayDeque<Card> tempDeck = getDeck().clone();

        getDeck().clear();
        getDeck().addLast(topCard);

        return tempDeck;

    }
}
