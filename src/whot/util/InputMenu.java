package whot.util;

import whot.io.IOHandler;
import whot.player.Player;

import java.util.List;

public final class InputMenu {

    private InputMenu() {}

    public static int continuePlaying(IOHandler io) {
        while (true) {
            io.println("Do you want to play another round?");
            io.println("Enter 1 to play another round");
            io.println("Enter 2 to exit game");

            try {
                int playerChoice = Integer.parseInt(io.readLine());

                if (List.of(1, 2).contains(playerChoice)) {
                    return playerChoice;
                } else {
                    io.println("Please enter a valid menu choice");
                }
            } catch (NumberFormatException e) {
                io.println("Please enter a valid integer");
            }
        }
    }





    public static int getPlayerMenuChoice(IOHandler io) {
        while (true) {
            io.println("- Enter 1 to go to market");
            io.println("- Enter 2 to play card");
            try {
                int playerChoice = Integer.parseInt(io.readLine());

                if (List.of(1, 2).contains(playerChoice)) {
                    return playerChoice;
                } else {
                    io.println("Please enter a valid menu choice");
                }
            } catch (NumberFormatException e) {
                io.println("Please enter a valid integer");
            }

        }
    }

    public static int generalMarketMenu(IOHandler io) {
        while (true) {
            io.println("- Enter 1 to go to market");
            try {
                int playerChoice = Integer.parseInt(io.readLine());
                if (playerChoice == 1) {
                    return playerChoice;
                } else {
                    io.println("Please enter a valid choice");
                }
            } catch (NumberFormatException e) {
                io.println("Please enter a valid integer");
            }

        }
    }

    public static int getCardPosition(Player currentPlayer, IOHandler io) {
        while (true) {
            io.println("Enter position of card you will like to play");
            try {
                int cardPosition = Integer.parseInt(io.readLine());

                if (cardPosition - 1 >= 0 && cardPosition - 1 < currentPlayer.getPlayerCards().size()) {
                    return cardPosition;
                } else {
                    io.println("Please enter a valid card position");
                }
            } catch (NumberFormatException e) {
                io.println("Please enter a valid integer");
            }
        }

    }


}
