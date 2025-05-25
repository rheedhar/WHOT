package whot;


import whot.io.ConsoleIO;

public class Main {
    public static void main(String[] args) {
        ConsoleIO io = new ConsoleIO();
        Game whotGame = new Game(io);
        whotGame.start();

    }
}
