package whot.io;

import java.util.Scanner;

public class ConsoleIO implements IOHandler {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void print(String message) {
        System.out.print(message);
    }

    @Override
    public void println(String message) {
        System.out.println(message);
    }

    @Override
    public void println() {
        System.out.println();
    }

    @Override
    public String readLine() {
        return scanner.nextLine();
    }

    public void delay(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            System.err.println("Thread was interrupted " + e.getMessage());
        }
    }
}
