package io;

import whot.io.IOHandler;

import java.util.LinkedList;
import java.util.Queue;

public class MockConsoleIO implements IOHandler {

    private final Queue<String> inputQueue = new LinkedList<>();
    private final StringBuilder output = new StringBuilder();

    // simulate user typing
    public void addInput(String input) {
        inputQueue.add(input);
    }


    @Override
    public void print(String message) {
        output.append(message);
    }

    @Override
    public void println(String message) {
        output.append(message).append("\n");
    }

    @Override
    public void println() {
        output.append(" ");
    }

    @Override
    public String readLine(){
        return inputQueue.poll();
    }

    @Override
    public void delay(long millisecs) {

    }

    public String getOutput() {
        return output.toString();
    }
}
