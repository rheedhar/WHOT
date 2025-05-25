package whot.io;

public interface IOHandler {
    void print(String message);
    void println(String message);
    void println();
    String readLine();
    void delay(long milliseconds);
}
