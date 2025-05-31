package deck;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import whot.card.Card;
import whot.card.Rank;
import whot.card.Suit;
import whot.deck.PlayDeck;

import java.util.ArrayDeque;

import static org.junit.jupiter.api.Assertions.*;

public class PlayDeckTest {
    PlayDeck playDeck;

    @BeforeEach
    void setUp() {
        Card card = new Card(Suit.CIRCLE, Rank.THREE);
        playDeck = new PlayDeck(card);

    }

    @Test
    @DisplayName("Test add card method adds card to top of deck")
    void testAddCard(){
        Card card = new Card(Suit.CIRCLE, Rank.TWO);

        playDeck.addCard(card);

        assertEquals(2, playDeck.getDeck().size());
        assertEquals(card, playDeck.getDeck().getLast());
    }

    @Test
    @DisplayName("Test fill main deck method")
    void testFillMainDeck() {
        Card card1 = new Card(Suit.CIRCLE, Rank.SEVEN);
        Card card2 = new Card(Suit.TRIANGLE, Rank.FOUR);
        playDeck.getDeck().add(card1);
        playDeck.getDeck().add(card2);

        ArrayDeque<Card> results = playDeck.fillMainDeck();

        assertEquals(2, results.size());
        assertTrue(results.contains(card1));
        assertEquals(1, playDeck.getDeck().size());
        assertEquals(Suit.TRIANGLE, playDeck.getDeck().getLast().getSuit());
        assertEquals(Rank.FOUR, playDeck.getDeck().getLast().getRank());
    }


}
