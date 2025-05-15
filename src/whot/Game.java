package whot;

import java.util.*;

public class Game {

    public static HashMap<String, Integer> scores = new HashMap<>();
    public static int gameRound;


    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }


    public static void main(String[] args) {
        int numPlayers = 0;
        List<Player> players = new ArrayList<>();

        while (true) {
            Scanner keyboard = new Scanner(System.in);
            System.out.println("---------Welcome to Whot Game--------");
            System.out.println("How many players are playing?");

            while (!(numPlayers >= 2)) {
                System.out.println("At least 2 users must play. \nPlease enter a number greater than or equal to 2: ");
                numPlayers = keyboard.nextInt();
                keyboard.nextLine();
            }

            System.out.println("Please enter the name for each player");
            int playerNum = 1;
            while (playerNum <= numPlayers) {
                System.out.println("Enter name for Player " + playerNum + ":");
                String playerName = keyboard.nextLine();

                if (playerName.isBlank()) {
                    System.out.println("Name for player cannot be empty");
                    continue;
                }

                players.add(new Player(playerName));
                playerNum++;
            }

            // create deck and deal cards to each player
            MainDeck mainDeck = new MainDeck();
            PlayDeck playdeck = mainDeck.dealCards(5);


            while(true) {
                int i = 0;
                while (i < players.size()) {
                    Player currentPlayer = players.get(i);
                    System.out.print("Call card: ");
                    System.out.println(playdeck.getDeck().peekLast());
                    System.out.println();
                    System.out.println(currentPlayer.getPlayerName() + "'s turn to play");
                    int playerChoice;

                    boolean playerMenu = true;
                    while (playerMenu) {
                        System.out.println("- Enter 1 to view your cards");
                        System.out.println("- Enter 2 to go to market");
                        System.out.println("- Enter 3 to play card");

                        playerChoice = keyboard.nextInt();
                        keyboard.nextLine();
                        if (!Set.of(1, 2, 3).contains(playerChoice)) {
                            System.out.println("Please enter a valid choice");
                            continue;
                        }

                        int cardPosition;
                        Card playedCard;
                        if (playerChoice == 1) {
                            System.out.println(currentPlayer.getPlayerCards());
                        } else if (playerChoice == 2) {
                            currentPlayer.goMarket(mainDeck);
                            playerMenu = false;
                        } else {
                            boolean validPosition = false;
                            while (!validPosition) {
                                System.out.println("Enter position of card you will like to play");
                                cardPosition = keyboard.nextInt();
                                keyboard.nextLine();
                                if (!(cardPosition - 1 >= 0 && cardPosition - 1 < currentPlayer.getPlayerCards().size())) {
                                    System.out.println("Please enter a valid card position");
                                    System.out.println();
                                } else {
                                    playedCard = currentPlayer.playCard(cardPosition);
                                    Card matchedCard = playdeck.addCard(currentPlayer, playedCard);
                                    if (matchedCard == null) {
                                        System.out.println("Card does not match play card. Select another matching card or go to market");
                                        break;
                                    }
                                    if (!matchedCard.getRank().isSpecial()) {
                                        currentPlayer.getPlayerCards().remove(cardPosition - 1);
                                        playerMenu = false;
                                        //clearConsole(); //TODO clear console function not working
                                    } else {
                                        currentPlayer.getPlayerCards().remove(cardPosition - 1);
                                        int specialRank = matchedCard.getRank().getNum();
                                        switch(specialRank) {
                                            case 1:
                                                System.out.println(currentPlayer.getPlayerName() + " gets to play another card");
                                                continue;
                                            case 2:
                                                //cardTwoPlayed();
                                                int currentPlayerIndex = players.indexOf(currentPlayer);
                                                int nextPlayerIndex = currentPlayerIndex + 1;
                                                Player nextPlayer = players.get(nextPlayerIndex);

                                                System.out.println(nextPlayer.getPlayerName() + "pick 2 cards from market or defend");
                                                boolean pick2Menu = true;
                                                while (pick2Menu) {
                                                    System.out.println("- Enter 1 to go to market");
                                                    System.out.println("- Enter 2 to defend");

                                                    playerChoice = keyboard.nextInt();
                                                    keyboard.nextLine();
                                                    if (!Set.of(1, 2).contains(playerChoice)) {
                                                        System.out.println("Please enter a valid choice");
                                                        continue;
                                                    }
                                                    pick2Menu = false;
                                                }

                                                if (playerChoice == 1) {
                                                    nextPlayer.goMarketForTwo(mainDeck);
                                                } else if (playerChoice == 2) {
                                                    System.out.println("- Enter 1 to view cards");
                                                    System.out.println("- Enter 2 to play");

                                                    playerChoice = keyboard.nextInt();
                                                    keyboard.nextLine();

                                                    while (!Set.of(1, 2).contains(playerChoice)) {
                                                        System.out.println("Please enter a valid choice");
                                                    }

                                                    if (playerChoice == 1){
                                                        System.out.println(currentPlayer.getPlayerCards());
                                                    } else {
                                                        continue;
                                                    }
                                                }
                                                break;
                                            case 5:
                                                // cardFivePlayed();
                                                break;
                                            case 8:
                                                //cardEightPlayed();
                                                break;
                                            case 14:
                                                //cardFourteenPlayed();
                                                break;
                                            case 20:
                                                //cardTwentyPlayed();
                                                break;
                                            default:
                                                break;
                                        }
                                        playerMenu = false;
                                    }




                                    validPosition = true;
                                }

                            }

                        }
                    }

                    i++;

                }
            }

        }

    }
}
