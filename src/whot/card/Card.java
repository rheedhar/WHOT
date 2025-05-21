package whot.card;

public class Card implements Comparable<Card> {
    private final Suit suit;
    private final Rank rank;


    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public Rank getRank() {
        return rank;
    }

    public Suit getSuit() {
        return suit;
    }

    @Override
    public int compareTo(Card other) {
        return Integer.compare(this.rank.getNum(), other.rank.getNum());
    }

    @Override
    public String toString() {
        return rank + " of " + suit;
    }
}
