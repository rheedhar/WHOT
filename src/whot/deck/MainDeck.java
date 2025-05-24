package whot.deck;

import whot.player.Player;
import whot.card.Card;
import whot.card.Rank;
import whot.card.Suit;

import java.util.*;

public class MainDeck extends Deck {
    private int numCards;
    private PlayDeck playDeck;

    public MainDeck() {
        initializeDeck();
        numCards = getDeck().size();
    }


    protected final void initializeDeck() {
        HashMap<Suit, List<Rank>> suitToRanks = new HashMap<>();

        suitToRanks.put(Suit.CIRCLE, List.of(Rank.ONE, Rank.TWO, Rank.THREE, Rank.FOUR, Rank.FIVE, Rank.SEVEN,
                Rank.EIGHT, Rank.TEN, Rank.ELEVEN, Rank.TWELVE, Rank.THIRTEEN, Rank.FOURTEEN));
        suitToRanks.put(Suit.TRIANGLE, List.of(Rank.ONE, Rank.TWO, Rank.THREE, Rank.FOUR, Rank.FIVE, Rank.SEVEN,
                Rank.EIGHT, Rank.TEN, Rank.ELEVEN, Rank.TWELVE, Rank.THIRTEEN, Rank.FOURTEEN));
        suitToRanks.put(Suit.CROSS, List.of(Rank.ONE, Rank.TWO, Rank.THREE, Rank.FIVE, Rank.SEVEN,
                Rank.TEN, Rank.ELEVEN, Rank.THIRTEEN, Rank.FOURTEEN));
        suitToRanks.put(Suit.SQUARE, List.of(Rank.ONE, Rank.TWO, Rank.THREE, Rank.FIVE, Rank.SEVEN, Rank.TEN,
                Rank.ELEVEN, Rank.THIRTEEN, Rank.FOURTEEN));
        suitToRanks.put(Suit.STAR, List.of(Rank.ONE, Rank.TWO, Rank.THREE, Rank.FOUR, Rank.FIVE, Rank.SEVEN, Rank.EIGHT));
        suitToRanks.put(Suit.WHOT, List.of(Rank.TWENTY, Rank.TWENTY, Rank.TWENTY, Rank.TWENTY, Rank.TWENTY));

        for (Map.Entry<Suit, List<Rank>> entry: suitToRanks.entrySet()){
            Suit suit = entry.getKey();
            List<Rank> ranks = entry.getValue();

            for (Rank rank: ranks) {
                Card card = new Card(suit, rank);
                getDeck().addLast(card);
            }
        }

    }

    public PlayDeck dealCards(List<Player> players, int numberOfCardsPerPlayer) {
        shuffleCards();
        if (!getDeck().isEmpty()) {
            for (Player player: players) {
                int i = numberOfCardsPerPlayer;
                List<Card> playerCards = player.getPlayerCards();
                while(i != 0) {
                    playerCards.add(getDeck().removeLast());
                    i--;
                }
            }
            playDeck = new PlayDeck(getDeck().removeLast());
            numCards = getDeck().size();
        }

        return playDeck; // TODO: returning a private reference might be bad
    }


    public Card getTopCard() {
        Card topCard = getDeck().removeLast();

        numCards = getDeck().size();
        if (numCards == 0) {
            fillDeckFromPlayDeck();
        }
        return topCard;
    }

    private void fillDeckFromPlayDeck() {
        ArrayDeque<Card> tempDeck = playDeck.fillMainDeck();
        for (Card card: tempDeck) {
            getDeck().addLast(card);
        }
        shuffleCards();

        numCards = getDeck().size();
    }



   @Override
   public String toString() {
        return getDeck().toString();
   }
}
