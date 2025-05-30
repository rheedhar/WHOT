package deck;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import whot.card.Card;
import whot.card.Rank;
import whot.card.Suit;
import whot.deck.MainDeck;
import whot.player.Player;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MainDeckTest {
    private MainDeck mainDeck;

    @BeforeEach
    void setUp() {
        mainDeck = new MainDeck();
        mainDeck.getDeck().clear();
    }

    @Test
    @DisplayName("Test that sort method in deck class sorts cards by rank in ascending order")
    void testSortByRank() {
        // given
        mainDeck.getDeck().add(new Card(Suit.CIRCLE, Rank.SEVEN));
        mainDeck.getDeck().add(new Card(Suit.STAR, Rank.FOUR));
        mainDeck.getDeck().add(new Card(Suit.SQUARE, Rank.TWO));

        // when
        mainDeck.sort();

        // then
        List<Card> sortedCards = new ArrayList<>(mainDeck.getDeck());
        assertEquals(Rank.TWO, sortedCards.get(0).getRank());
        assertEquals(Rank.FOUR, sortedCards.get(1).getRank());
        assertEquals(Rank.SEVEN, sortedCards.get(2).getRank());

    }

    @Test
    @DisplayName("Test that sort method in deck class sorts cards by suit in ascending order")
    void testSortBySuit() {
        // given
        mainDeck.getDeck().add(new Card(Suit.CIRCLE, Rank.SEVEN));
        mainDeck.getDeck().add(new Card(Suit.STAR, Rank.FOUR));
        mainDeck.getDeck().add(new Card(Suit.SQUARE, Rank.TWO));

        // when
        mainDeck.sort("suit");

        // then
        List<Card> sortedCards = new ArrayList<>(mainDeck.getDeck());
        assertEquals(Suit.CIRCLE, sortedCards.get(0).getSuit());
        assertEquals(Suit.SQUARE, sortedCards.get(1).getSuit());
        assertEquals(Suit.STAR, sortedCards.get(2).getSuit());

    }

    @Test
    @DisplayName("Test that sort method with invalid key throws error")
    void testSortByInvalidKey() {
        mainDeck.getDeck().add(new Card(Suit.CIRCLE, Rank.SEVEN));
        mainDeck.getDeck().add(new Card(Suit.STAR, Rank.FOUR));
        mainDeck.getDeck().add(new Card(Suit.SQUARE, Rank.TWO));

        // when & then
        assertThrows(IllegalArgumentException.class, () -> mainDeck.sort("rank"));
    }

    @Test
    @DisplayName("Test that shuffle cards method in main deck return card in a different order than it was created")
    void testShuffleCards() {
        mainDeck.getDeck().add(new Card(Suit.CIRCLE, Rank.SEVEN));
        mainDeck.getDeck().add(new Card(Suit.STAR, Rank.FOUR));
        mainDeck.getDeck().add(new Card(Suit.SQUARE, Rank.TWO));
        List<Card> originalOrder = new ArrayList<>(mainDeck.getDeck());

        // when
        mainDeck.shuffleCards();

        // then
        boolean orderChanged = false;
        // run shuffle a few times to account for randomness. hopefully it does change the fifth time
        for (int i = 0; i < 5; i++) {
            mainDeck.shuffleCards();
            List<Card> shuffledOrder = new ArrayList<>(mainDeck.getDeck());
            if (!shuffledOrder.equals(originalOrder)) {
                orderChanged = true;
                break;
            }
        }
        assertTrue(orderChanged);
    }

    @Test
    @DisplayName("Test deck is initialized with correct number of cards")
    void testDeckInitialization() {
        mainDeck = new MainDeck();
        int expectedSize = 54; // Sum of all cards from your mapping
        assertEquals(expectedSize, mainDeck.getDeck().size());
    }

    @Test
    @DisplayName("Test dealCards gives players correct number of cards and sets playDeck")
    void testDealCards() {
        mainDeck = new MainDeck();
        Player player1 = new Player("Mia");
        Player player2 = new Player("Ayo");
        List<Player> players = List.of(player1, player2);

        mainDeck.dealCards(players, 5);

        assertEquals(5, player1.getPlayerCards().size());
        assertEquals(5, player2.getPlayerCards().size());
        assertNotNull(mainDeck.getPlayDeck());
        assertEquals(54 - 5*2 - 1, mainDeck.getDeck().size()); // 2 players + 1 call card
    }

    @Test
    @DisplayName("Test getTopCard removes and returns last card")
    void testGetTopCard() {
        mainDeck.getDeck().add(new Card(Suit.CIRCLE, Rank.SEVEN));
        mainDeck.getDeck().add(new Card(Suit.STAR, Rank.FOUR));
        mainDeck.getDeck().add(new Card(Suit.SQUARE, Rank.TWO));
        int deckSize = mainDeck.getDeck().size();

        Card topCard = mainDeck.getTopCard();

        assertNotNull(topCard);
        assertEquals(Suit.SQUARE, topCard.getSuit());
        assertEquals(Rank.TWO, topCard.getRank());
        assertEquals(deckSize - 1, mainDeck.getDeck().size());
    }
}
