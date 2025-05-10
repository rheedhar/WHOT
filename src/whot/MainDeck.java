package whot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainDeck extends Deck {
    private static int numCards = 54;

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
                deck.add(card);
            }
        }

    }

   public MainDeck() {
        initializeDeck();
   }

   @Override
   public String toString() {
        return deck.toString();
   }
}
