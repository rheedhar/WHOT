package whot;

public class Game {
    public static void main(String[] args) {
        MainDeck deck = new MainDeck();

        deck.sort();
        System.out.println(deck.getDeck());
    }
}
