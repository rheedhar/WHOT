package whot;

import java.util.ArrayDeque;

public class PlayDeck extends Deck {

    public PlayDeck(Card card){
        getDeck().addLast(card);
    }

    public Card addCard(Player player, Card card) {
        if(card.getRank().getNum() != 20) {
            Card currentCard = getDeck().peekLast();
            System.out.println("Comparing: " + currentCard + " vs " + card);
            System.out.println("Ranks: " + currentCard.getRank().getNum() + " vs " + card.getRank().getNum());
            System.out.println("Suits: " + currentCard.getSuit().getValue() + " vs " + card.getSuit().getValue());
            boolean rankMatch = currentCard.getRank().getNum() == card.getRank().getNum();
            boolean suitMatch = currentCard.getSuit().getValue().equals(card.getSuit().getValue());
            if(rankMatch || suitMatch) {
                getDeck().addLast(card);
                return card;
            } else {
                return null;
            }
        } else {
            getDeck().addLast(card);
            return card;
        }
    }

    public ArrayDeque<Card> fillMainDeck() {
        Card topCard = getDeck().removeLast();
        ArrayDeque<Card> tempDeck = getDeck().clone();

        getDeck().clear();
        getDeck().addLast(topCard);

        return tempDeck;

    }
}
