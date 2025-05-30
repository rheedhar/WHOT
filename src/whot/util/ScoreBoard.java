package whot.util;

import whot.io.IOHandler;

import java.util.HashMap;
import java.util.Map;

public class ScoreBoard {
    private final HashMap<String, Integer> playerScores = new HashMap<>();
    private final IOHandler io;

    public ScoreBoard(IOHandler io) {
        this.io = io;
    }

    public void registerPlayer(String name) {
        playerScores.put(name, 0);
    }

    public HashMap<String, Integer> getPlayerScores() {
        return new HashMap<>(playerScores);
    }

    public void incrementPlayerScore(String playerName) {
        playerScores.put(playerName, playerScores.get(playerName) + 1);
    }

    public void printScoreBoard() {
        io.println("==========Score Board===========");
        for(Map.Entry<String, Integer> entry: playerScores.entrySet()) {
            io.println(entry.getKey() + ": " + entry.getValue() + " point(s)");
        }
        io.println("================================");
    }
}
