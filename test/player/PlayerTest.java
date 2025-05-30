package player;

import io.MockConsoleIO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import whot.Main;
import whot.card.Card;
import whot.card.Rank;
import whot.card.Suit;
import whot.deck.MainDeck;
import whot.player.Player;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlayerTest {
    private MockConsoleIO mockIO;
    private Player player;
    private MainDeck mainDeck;

    @BeforeEach
    void setup() {
        mockIO = new MockConsoleIO();
        String playerName = "Ayo";
        player = new Player(playerName);

        mainDeck = new MainDeck() {
            @Override
            public Card getTopCard() {
                return new Card(Suit.CIRCLE, Rank.SEVEN);
            }
        };
    }


    @Test
    @DisplayName("Test get formatted player card")
    void testGetFormattedPlayerCards() {
        player.getPlayerCards().add(new Card(Suit.CIRCLE, Rank.SEVEN));
        player.getPlayerCards().add(new Card(Suit.TRIANGLE, Rank.SEVEN));

        String actualResults = player.getFormattedPlayerCards();

        assertTrue(actualResults.contains("[(1) SEVEN of CIRCLE"));
        assertTrue(actualResults.contains("SEVEN of TRIANGLE"));
    }

    @Test
    @DisplayName("Test play card returns correct card played")
    void testPlayCard() {
        player.getPlayerCards().add(new Card(Suit.CIRCLE, Rank.SEVEN));
        player.getPlayerCards().add(new Card(Suit.SQUARE, Rank.SEVEN));

        Card cardPlayed = player.playCard(2);

        assertEquals(Suit.SQUARE, cardPlayed.getSuit());
        assertEquals(Rank.SEVEN, cardPlayed.getRank());
    }

    @Test
    @DisplayName("Test player goes to makret to pick a card")
    void testGoMarket() {
        player.getPlayerCards().add(new Card(Suit.CIRCLE, Rank.ONE));
        player.getPlayerCards().add(new Card(Suit.TRIANGLE, Rank.EIGHT));
        int numCardsToPick = 1;

        // when
        player.goMarket(mainDeck, numCardsToPick, mockIO);

        // then
        assertEquals(3, player.getPlayerCards().size());
        assertEquals(Suit.CIRCLE, player.getPlayerCards().get(player.getPlayerCards().size() - 1).getSuit());
        assertEquals(Rank.SEVEN, player.getPlayerCards().get(player.getPlayerCards().size() - 1).getRank());
    }
}
