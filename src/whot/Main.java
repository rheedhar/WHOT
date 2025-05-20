package whot;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        Game whotGame = new Game();

        // Welcome players to the game
        whotGame.welcomePlayers();

        //get number of players
        int numPlayers = whotGame.getNumberOfPlayers(keyboard);

        // create players and store in list
        List<Player> players = whotGame.createPlayerObjects(keyboard, numPlayers);

        // create deck and deal cards to each player
        MainDeck mainDeck = new MainDeck();
        PlayDeck playdeck = mainDeck.dealCards(5);

        // start game play
        whotGame.startGame(players, mainDeck, playdeck, keyboard);

    }
}
