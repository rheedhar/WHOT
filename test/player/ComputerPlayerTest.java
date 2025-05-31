package player;

import io.MockConsoleIO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import whot.card.Card;
import whot.card.Rank;
import whot.card.SpecialCard;
import whot.card.Suit;
import whot.deck.MainDeck;
import whot.deck.PlayDeck;
import whot.player.ComputerPlayer;

import static org.junit.jupiter.api.Assertions.*;

public class ComputerPlayerTest {
    private ComputerPlayer computerPlayer;
    private MainDeck mainDeck;
    private PlayDeck playDeck;
    private MockConsoleIO mockIO;

    @BeforeEach
    void setup() {
        mockIO = new MockConsoleIO();
        computerPlayer = new ComputerPlayer("Mia", mockIO);
        mainDeck = new MainDeck() {
            @Override
            public Card getTopCard() {
                return new Card(Suit.CIRCLE, Rank.SEVEN);
            }
        };
         playDeck = new PlayDeck(mainDeck.getTopCard());
    }

    @Test
    @DisplayName("Test that play method is valid when card matches by suit")
    void testPlayWithMatchingSuit() {
        computerPlayer.getPlayerCards().add(new Card(Suit.CIRCLE, Rank.FOUR));

        computerPlayer.play(mainDeck, playDeck);

        assertEquals(0, computerPlayer.getPlayerCards().size());
        assertEquals(Rank.FOUR, playDeck.getDeck().peekLast().getRank());
        assertTrue(computerPlayer.isComputerPlayerWinner());
        assertTrue(mockIO.getOutput().contains("Mia's turn to play"));
        assertTrue(mockIO.getOutput().contains("Mia played"));
        assertTrue(mockIO.getOutput().contains("Winner: Mia"));

    }

    @Test
    @DisplayName("Test that play method is valid when card matches by rank")
    void testPlayWithMatchingRank() {
        computerPlayer.getPlayerCards().add(new Card(Suit.TRIANGLE, Rank.SEVEN));

        computerPlayer.play(mainDeck, playDeck);

        assertEquals(0, computerPlayer.getPlayerCards().size());
        assertEquals(Rank.SEVEN, playDeck.getDeck().peekLast().getRank());
        assertTrue(computerPlayer.isComputerPlayerWinner());
        assertTrue(mockIO.getOutput().contains("Mia's turn to play"));
        assertTrue(mockIO.getOutput().contains("Mia played"));
        assertTrue(mockIO.getOutput().contains("Winner: Mia"));

    }

    @Test
    @DisplayName("Test that play method goes to market when no valid play")
    void testPlayWhenNoValidCard() {
        computerPlayer.getPlayerCards().add(new Card(Suit.SQUARE, Rank.TEN)); // doesn't match

        computerPlayer.play(mainDeck, playDeck);

        assertEquals(2, computerPlayer.getPlayerCards().size()); // 1 hand + 1 drawn
        assertNotEquals(Rank.TEN, playDeck.getDeck().peekLast().getRank());
        assertFalse(computerPlayer.isComputerPlayerWinner());
        assertTrue(mockIO.getOutput().contains("Mia's turn to play"));
        assertFalse(mockIO.getOutput().contains("Mia played"));
        assertFalse(mockIO.getOutput().contains("Winner: Mia"));
    }

    @Test
    @DisplayName("Test that special card is defined when a player plays a special card")
    void testPlaySetsSpecialCardWhenSpecialCardIsPlayed() {
        computerPlayer.getPlayerCards().add(new Card(Suit.CIRCLE, Rank.TWO));
        computerPlayer.getPlayerCards().add(new Card(Suit.CIRCLE, Rank.THREE)); // adding a second card so player is not declared winner yet

        // when
        computerPlayer.play(mainDeck, playDeck);

        // then
        assertTrue(mockIO.getOutput().contains("Mia's turn to play"));
        assertTrue(mockIO.getOutput().contains("Mia played"));
        assertEquals(SpecialCard.PICK_TWO, computerPlayer.getSpecialCard());
    }

    @Test
    @DisplayName("Test that special card of 1 ofr 8 allows player to play again")
    void testPlaySpecialCardOneOrEight() {
        computerPlayer.getPlayerCards().add(new Card(Suit.CIRCLE, Rank.ONE));
        computerPlayer.getPlayerCards().add(new Card(Suit.CIRCLE, Rank.THREE));

        computerPlayer.play(mainDeck, playDeck);
        assertTrue(mockIO.getOutput().contains("Mia gets to play another card"));
    }

    @Test
    @DisplayName("Test that play pick two or three method is valid when a player plays pick two or three ")
    void testPlayPickTwoOrThreeWithADefendTwoCard() {
        mainDeck = new MainDeck() {
            @Override
            public Card getTopCard() {
                return new Card(Suit.CIRCLE, Rank.TWO);
            }
        };
        playDeck = new PlayDeck(mainDeck.getTopCard());
        computerPlayer.getPlayerCards().add(new Card(Suit.TRIANGLE, Rank.TWO));

        // when
        computerPlayer.playPickTwoOrThree(mainDeck, playDeck, 2);

        // then
        assertEquals(2, computerPlayer.getNumberOfMarketCards());
        assertEquals(0, computerPlayer.getPlayerCards().size());
        assertEquals(Suit.TRIANGLE, playDeck.getDeck().peekLast().getSuit());
        assertTrue(computerPlayer.isComputerPlayerWinner());
        assertTrue(mockIO.getOutput().contains("Mia's turn to play"));
        assertTrue(mockIO.getOutput().contains("Mia, you have to pick 2 cards from market or defend"));
        assertTrue(mockIO.getOutput().contains("Mia played"));
        assertTrue(mockIO.getOutput().contains("Winner: Mia"));
    }

    @Test
    @DisplayName("Test that play pick two or three method goes to pick two when no valid play")
    void testPlayPickTwoORThreeWithNoDefendCard() {
        mainDeck = new MainDeck() {
            @Override
            public Card getTopCard() {
                return new Card(Suit.CIRCLE, Rank.TWO);
            }
        };
        playDeck = new PlayDeck(mainDeck.getTopCard());
        computerPlayer.getPlayerCards().add(new Card(Suit.CIRCLE, Rank.THREE));

        // when
        computerPlayer.playPickTwoOrThree(mainDeck, playDeck, 3);

        // then
        assertEquals(3, computerPlayer.getNumberOfMarketCards());
        assertEquals(4, computerPlayer.getPlayerCards().size()); // 1 hand + 3 from market
        assertEquals(SpecialCard.NONE, computerPlayer.getSpecialCard());
        assertFalse(computerPlayer.isComputerPlayerWinner());
        assertTrue(mockIO.getOutput().contains("Mia's turn to play"));
        assertTrue(mockIO.getOutput().contains("Mia, you have to pick 3 cards from market or defend"));
        assertFalse(mockIO.getOutput().contains("Mia played"));
        assertFalse(mockIO.getOutput().contains("Winner: Mia"));

    }

    @Test
    @DisplayName("Test play general market method adds a card to player cards")
    void testPlayGeneralMarket() {
        computerPlayer.getPlayerCards().add(new Card(Suit.CIRCLE, Rank.SEVEN));

        // when
        computerPlayer.playGeneralMarket(mainDeck);

        // then
        assertEquals(2, computerPlayer.getPlayerCards().size());
        assertEquals(SpecialCard.NONE, computerPlayer.getSpecialCard());
        assertTrue(mockIO.getOutput().contains("Mia is going to general market"));

    }


    @Test
    @DisplayName("Test that setWhotRequest method selects the suit of the first card of the player")
    void testSetWhotRequestChoosesFirstCardSuit() {
        computerPlayer.getPlayerCards().add(new Card(Suit.STAR, Rank.FOUR));
        computerPlayer.setWhotRequest();

        assertEquals("STAR", computerPlayer.getWhotRequest());
        assertTrue(mockIO.getOutput().contains("Mia requested a STAR"));
    }

    @Test
    @DisplayName("Test whot request method with valid request")
    void testPlayWhotWithValidRequest() {
        mainDeck = new MainDeck() {
            @Override
            public Card getTopCard() {
                return new Card(Suit.WHOT, Rank.TWENTY);
            }
        };
        playDeck = new PlayDeck(mainDeck.getTopCard());
        String whotRequest = "circle";
        computerPlayer.getPlayerCards().add(new Card(Suit.CIRCLE, Rank.THREE));

        // when
        computerPlayer.playWhot(mainDeck, playDeck, whotRequest);

        // then
        assertEquals(whotRequest, computerPlayer.getWhotRequest());
        assertEquals(SpecialCard.NONE, computerPlayer.getSpecialCard());
        assertTrue(mockIO.getOutput().contains("Mia's turn to play"));
        assertTrue(mockIO.getOutput().contains("Mia can either play a card with a suit of " + whotRequest + ", defend with a suit of whot or go to market"));
        assertTrue(mockIO.getOutput().contains("Mia played"));
    }

    @Test
    @DisplayName("Test whot request method with invalid request goes to market")
    void testPlayWhotWithInvalidRequest() {
        mainDeck = new MainDeck() {
            @Override
            public Card getTopCard() {
                return new Card(Suit.WHOT, Rank.TWENTY);
            }
        };
        playDeck = new PlayDeck(mainDeck.getTopCard());
        String whotRequest = "triangle";
        computerPlayer.getPlayerCards().add(new Card(Suit.CIRCLE, Rank.THREE));

        // when
        computerPlayer.playWhot(mainDeck, playDeck, whotRequest);

        // then
        assertEquals(whotRequest, computerPlayer.getWhotRequest());
        assertEquals(SpecialCard.WHOT, computerPlayer.getSpecialCard());
        assertEquals(1, computerPlayer.getNumberOfMarketCards());
        assertTrue(mockIO.getOutput().contains("Mia's turn to play"));
        assertTrue(mockIO.getOutput().contains("Mia can either play a card with a suit of " + whotRequest + ", defend with a suit of whot or go to market"));
        assertFalse(mockIO.getOutput().contains("Mia played"));
    }

}
