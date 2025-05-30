package util;

import io.MockConsoleIO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import whot.card.Card;
import whot.card.Rank;
import whot.card.Suit;
import whot.player.Player;
import whot.util.InputMenu;

import static org.junit.jupiter.api.Assertions.*;

public class InputMenuTest {
    private MockConsoleIO mockIO;

    @BeforeEach
    void createMockIOHandlerObject() {
        mockIO = new MockConsoleIO();
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "2"})
    @DisplayName("Test continue playing method with valid inputs")
    void testContinuePlayingWithValidMenuChoice(String inputValue) {
        // given
        mockIO.addInput(inputValue);

        // when
        int actualResult = InputMenu.continuePlaying(mockIO);
        String output = mockIO.getOutput();

        //then
        assertEquals(Integer.parseInt(inputValue), actualResult);
        assertTrue(output.contains("Do you want to play another round?"));
        assertTrue(output.contains("Enter 1 to play another round"));
        assertTrue(output.contains("Enter 2 to exit game"));
    }

    @Test
    @DisplayName("Test continue playing method with invalid inputs")
    void testContinuePlayingWithInvalidThenValidMenuChoice() {
        // given
        mockIO.addInput("abc");
        mockIO.addInput("5");
        mockIO.addInput("2");

        // when
        int actualResults = InputMenu.continuePlaying(mockIO);

        // then
        String output = mockIO.getOutput();
        assertEquals(2, actualResults);
        assertTrue(output.contains("Do you want to play another round?"));
        assertTrue(output.contains("Enter 1 to play another round"));
        assertTrue(output.contains("Enter 2 to exit game"));
        assertTrue(output.contains("Please enter a valid menu choice"));
        assertTrue(output.contains("Please enter a valid integer"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "2"})
    void testGetPlayerMenuChoiceWithValidInput(String inputValue) {
        // given
        mockIO.addInput(inputValue);

        // when
        int actualResult = InputMenu.getPlayerMenuChoice(mockIO);
        String output = mockIO.getOutput();

        //then
        assertEquals(Integer.parseInt(inputValue), actualResult);
        assertTrue(output.contains("- Enter 1 to go to market"));
        assertTrue(output.contains("- Enter 2 to play card"));
        assertFalse(output.contains("Please enter a valid integer"));
    }

    @Test
    void testGetPlayerMenuChoiceWithInvalidInputsThenValidInputs() {
        // given
        mockIO.addInput("abc");
        mockIO.addInput("5");
        mockIO.addInput("2");

        // when
        int actualResults = InputMenu.getPlayerMenuChoice(mockIO);

        // then
        String output = mockIO.getOutput();
        assertEquals(2, actualResults);
        assertTrue(output.contains("- Enter 1 to go to market"));
        assertTrue(output.contains("- Enter 2 to play card"));
        assertTrue(output.contains("Please enter a valid menu choice"));
        assertTrue(output.contains("Please enter a valid integer"));
    }

    @Test
    void testGeneralMarketMenuWithValidInputs() {
        // given
        mockIO.addInput("1");

        // when
        int actualResults = InputMenu.generalMarketMenu(mockIO);

        // then
        String output = mockIO.getOutput();
        assertEquals(1, actualResults);
        assertTrue(output.contains("- Enter 1 to go to market"));
    }

    @Test
    void testGeneralMarketMenuWithInvalidInputsThenValidInputs() {
        // given
        mockIO.addInput("abc");
        mockIO.addInput("5");
        mockIO.addInput("1");

        // when
        int actualResults = InputMenu.getPlayerMenuChoice(mockIO);

        // then
        String output = mockIO.getOutput();
        assertEquals(1, actualResults);
        assertTrue(output.contains("- Enter 1 to go to market"));
        assertTrue(output.contains("Please enter a valid menu choice"));
        assertTrue(output.contains("Please enter a valid integer"));
    }

    @Test
    void testGetCardPositionWithValidInputs() {
        // given
        Player player1 = new Player("test player 1");
        player1.getPlayerCards().add(new Card(Suit.CIRCLE, Rank.FIVE));
        player1.getPlayerCards().add(new Card(Suit.CIRCLE, Rank.FOUR));
        player1.getPlayerCards().add(new Card(Suit.TRIANGLE, Rank.SEVEN));
        mockIO.addInput("1");

        // when
        int actualResult = InputMenu.getCardPosition(player1, mockIO);

        // then
        String output = mockIO.getOutput();
        assertEquals(1, actualResult);
        assertTrue(output.contains("Enter position of card you will like to play"));
        assertFalse(output.contains("Please enter a valid card position"));
        assertFalse(output.contains("Please enter a valid integer"));
    }

    @Test
    void testGetCardPositionWithInvalidInputThenValidInputs() {
        Player player1 = new Player("test player 1");
        player1.getPlayerCards().add(new Card(Suit.SQUARE, Rank.FIVE));
        player1.getPlayerCards().add(new Card(Suit.CIRCLE, Rank.FOUR));
        player1.getPlayerCards().add(new Card(Suit.TRIANGLE, Rank.SEVEN));
        mockIO.addInput("0");
        mockIO.addInput("abc");
        mockIO.addInput("3");

        // when
        int actualResults = InputMenu.getCardPosition(player1, mockIO);
        String output = mockIO.getOutput();

        // then
        assertEquals(3, actualResults);
        assertTrue(output.contains("Enter position of card you will like to play"));
        assertTrue(output.contains("Please enter a valid card position"));
        assertTrue(output.contains("Please enter a valid integer"));
    }



}
