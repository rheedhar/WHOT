package whot;

import whot.player.Player;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        Game whotGame = new Game();

        // Welcome players to the game
        whotGame.welcomePlayers();

        //get number of players
        int gameOption = whotGame.setGameOption(keyboard);

        // create players and store in list
        List<Player> players = whotGame.createPlayerObjects(keyboard, gameOption);


        // start game play
        whotGame.setGameRound(1);
        whotGame.startGame(players, keyboard);

    }
}
