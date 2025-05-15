package whot;

import java.util.*;

public class Game {

    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);

        // Welcome players to the game
        welcomePlayers();

        //get number of players
        int numPlayers = getNumberOfPlayers(keyboard);

        // create players and store in list
        List<Player> players = createPlayerObjects(keyboard, numPlayers);

        // create deck and deal cards to each player
        MainDeck mainDeck = new MainDeck();
        PlayDeck playdeck = mainDeck.dealCards(5);

        // start game play
        startGame(players, mainDeck, playdeck, keyboard);

    }

    private static void welcomePlayers() {
        System.out.println("---------Welcome to Whot Game--------");
    }

    private static int getNumberOfPlayers(Scanner keyboard) {
        System.out.println("How many players are playing?");
        while (true) {
            System.out.println("At least 2 users must play. \nPlease enter a number greater than or equal to 2: ");
            try {
                int numPlayers = Integer.parseInt(keyboard.nextLine());

                if (numPlayers >= 2) {
                    return numPlayers;
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer");
            }
        }

    }

    private static List<Player> createPlayerObjects(Scanner keyboard, int numPlayers) {
        List<Player> playersTemp = new ArrayList<>(numPlayers);
        System.out.println("Please enter the name for each player");
        int playerNum = 1;
        while(playerNum <= numPlayers) {
            System.out.println("Enter name for Player " + playerNum + ":");
            String playerName = keyboard.nextLine();

            if (playerName.isBlank()) {
                System.out.println("Player needs to have a name");
                continue;
            }

            playersTemp.add(new Player(playerName));
            playerNum++;
        }

        return playersTemp;
    }


    private static void startGame(List<Player> players, MainDeck mainDeck, PlayDeck playDeck, Scanner keyboard) {
        while (true) {
            for(Player currentPlayer: players) {
                play(currentPlayer, players, mainDeck, playDeck, keyboard);
            }
        }
    }

    private static void play(Player currentPlayer, List<Player> players, MainDeck mainDeck, PlayDeck playDeck, Scanner keyboard) {
        System.out.println(currentPlayer.getPlayerName() + "'s turn to play");
        System.out.print("Call card: " + playDeck.getDeck().peekLast());
        System.out.println();

        while (true) {
            int currentPlayerChoice = getPlayerMenuChoice(keyboard);

            if (currentPlayerChoice == 1) {
                System.out.println(currentPlayer.getPlayerCards());
            } else if (currentPlayerChoice == 2) {
                currentPlayer.goMarket(mainDeck);
                return;
            } else {
                boolean isValidPlay = handleCardPlay(players, currentPlayer, mainDeck, playDeck, keyboard);
                if(isValidPlay) return;
            }
        }

    }

    private static int getPlayerMenuChoice(Scanner keyboard) {
        while (true) {
            System.out.println("- Enter 1 to view your cards");
            System.out.println("- Enter 2 to go to market");
            System.out.println("- Enter 3 to play card");
            try {
                int playerChoice = Integer.parseInt(keyboard.nextLine());

                if (Set.of(1, 2, 3).contains(playerChoice)) {
                    return playerChoice;
                } else {
                    System.out.println("Please enter a valid menu choice");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer");
            }

        }
    }

    private static boolean handleCardPlay(List<Player> players, Player currentPlayer, MainDeck mainDeck, PlayDeck playDeck, Scanner keyboard) {
        int cardPosition = getCardPosition(currentPlayer, keyboard);
        Card playedCard = currentPlayer.playCard(cardPosition);
        Card matchedCard = playDeck.addCard(currentPlayer, playedCard);

        if (matchedCard == null) {
            System.out.println("Card does not match play card. Select another matching card or go to market");
            return false;
        }

        currentPlayer.getPlayerCards().remove(cardPosition - 1);

        if (!matchedCard.getRank().isSpecial()) return true;

        return handleSpecialCard(matchedCard, currentPlayer, players, mainDeck, playDeck, keyboard);
    }

    private static int getCardPosition(Player currentPlayer, Scanner keyboard) {
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

    private static boolean handleSpecialCard(Card matchedCard, Player currentPlayer, List<Player> players, MainDeck mainDeck, PlayDeck playDeck, Scanner keyboard) {
        int specialRank = matchedCard.getRank().getNum();
        switch(specialRank) {
            case 1:
                System.out.println(currentPlayer.getPlayerName() + " gets to play another card");
                return false;
            case 2:
                //return handlePickTwo(players, currentPlayer, mainDeck, playDeck, keyboard);
            case 5:
                //return handlePickThree();
                break;
            case 8:
                //return handleSkipNextPlayer();
                break;
            case 14:
                //return handleAllPlayersGoToMarket();
                break;
            case 20:
                //return handleRequestSymbol();
                break;
            default:
                break;
        }
        throw new IllegalStateException("Unexpected Special card: " + matchedCard.getRank() + "of" + matchedCard.getSuit() );
    }

//    private static boolean handlePickTwo(List<Player> players, Player currentPlayer, MainDeck mainDeck, PlayDeck playDeck, Scanner keyboard) {
//        int currentPlayerIndex = players.indexOf(currentPlayer);
//        int nextPlayerIndex = currentPlayerIndex + 1;
//        Player nextPlayer = players.get(nextPlayerIndex);
//
//        System.out.println(nextPlayer.getPlayerName() + "pick 2 cards from market or defend");
//        int playerChoice = SpecialCard.getPickTwoMenu(keyboard);
//
//        if (playerChoice == 1) {
//            nextPlayer.goMarketForTwo(mainDeck);
//        } else if (playerChoice == 2) {
//            SpecialCard.defendPickTwo();
//        }
//
//    }

}
