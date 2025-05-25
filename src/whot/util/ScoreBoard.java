package whot.util;

import java.util.HashMap;
import java.util.Map;

public class ScoreBoard {
    private final HashMap<String, Integer> playerScores = new HashMap<>();

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
        System.out.println("==========Score Board===========");
        for(Map.Entry<String, Integer> entry: playerScores.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " points(s)");
        }
        System.out.println("================================");
    }
}
