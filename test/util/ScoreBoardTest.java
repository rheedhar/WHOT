package util;

import io.MockConsoleIO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import whot.io.ConsoleIO;
import whot.util.ScoreBoard;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ScoreBoardTest {
    private ScoreBoard scoreBoard;
    private MockConsoleIO mockIO;

    @BeforeEach
    void setup() {
        mockIO = new MockConsoleIO();
        scoreBoard = new ScoreBoard(mockIO);
    }

    @Test
    @DisplayName("Test that a new player is added to player scores with a score of 0")
    void testRegisterPlayer() {
        String playerName = "Alicia";

        scoreBoard.registerPlayer(playerName);

        HashMap<String, Integer> playersScores = scoreBoard.getPlayerScores();
        assertTrue(playersScores.containsKey(playerName));
        assertEquals(0, playersScores.get(playerName));
    }

    @Test
    @DisplayName("Test that increment player score increments the score of the player")
    void testIncrementPlayerScore() {
        // given
        String playerName = "Bob";
        scoreBoard.registerPlayer(playerName);

        // when
        scoreBoard.incrementPlayerScore(playerName);

        // then
        HashMap<String, Integer> playersScores = scoreBoard.getPlayerScores();
        assertTrue(playersScores.containsKey(playerName));
        assertEquals(1, playersScores.get(playerName));
    }

    @Test
    @DisplayName("Test getPlayerScores returns a reference and not the original map")
    void testGetPlayerScores() {
        String playerName = "Jamie";
        scoreBoard.registerPlayer(playerName);
        HashMap<String, Integer> returnedScores = scoreBoard.getPlayerScores();

        returnedScores.put(playerName, 100);
        HashMap<String, Integer> actualScores = scoreBoard.getPlayerScores();
        assertEquals(0, actualScores.get(playerName));
    }

    @Test
    @DisplayName("Test printScoreBoard prints the scores for all players")
    void testPrintScoreBoard() {
        String player1Name = "Jamie";
        String player2Name = "Reb";
        scoreBoard.registerPlayer(player1Name);
        scoreBoard.registerPlayer(player2Name);

        scoreBoard.printScoreBoard();

        assertTrue(mockIO.getOutput().contains("Jamie: 0 point(s)"));
        assertTrue(mockIO.getOutput().contains("Reb: 0 point(s)"));
    }


}
