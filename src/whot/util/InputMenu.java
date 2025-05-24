package whot.util;

import whot.player.Player;

import java.util.List;
import java.util.Scanner;
import java.util.Set;

public final class InputMenu {

    private InputMenu() {}

    public static int continuePlaying(Scanner keyboard) {
        while (true) {
            System.out.println("Do you want to play another round?");
            System.out.println("Enter 1 to play another round");
            System.out.println("Enter 2 to exit game");

            try {
                int playerChoice = Integer.parseInt(keyboard.nextLine());

                if (List.of(1, 2).contains(playerChoice)) {
                    return playerChoice;
                } else {
                    System.out.println("Please enter a valid menu choice");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer");
            }
        }
    }





    public static int getPlayerMenuChoice(Scanner keyboard) {
        while (true) {
            System.out.println("- Enter 1 to go to market");
            System.out.println("- Enter 2 to play card");
            try {
                int playerChoice = Integer.parseInt(keyboard.nextLine());

                if (List.of(1, 2).contains(playerChoice)) {
                    return playerChoice;
                } else {
                    System.out.println("Please enter a valid menu choice");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer");
            }

        }
    }

    public static int generalMarketMenu(Scanner keyboard) {
        while (true) {
            System.out.println("- Enter 1 to go to market");
            try {
                int playerChoice = Integer.parseInt(keyboard.nextLine());
                if (playerChoice == 1) {
                    return playerChoice;
                } else {
                    System.out.println("Please enter a valid choice");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer");
            }

        }
    }

    public static int getCardPosition(Player currentPlayer, Scanner keyboard) {
        while (true) {
            System.out.println("Enter position of card you will like to play");
            try {
                int cardPosition = Integer.parseInt(keyboard.nextLine());

                if (cardPosition - 1 >= 0 && cardPosition - 1 < currentPlayer.getPlayerCards().size()) {
                    return cardPosition;
                } else {
                    System.out.println("Please enter a valid card position");
                }
            } catch (NumberFormatException e) {
                System.err.println("Please enter a valid integer");
            }
        }

    }


}
