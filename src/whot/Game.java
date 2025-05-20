package whot;

import java.util.*;

public class Game {
    private SpecialCard specialCard = SpecialCard.NONE;
    private String whotRequest;
    private boolean isFirstPlayCardWhot = false;

    public void welcomePlayers() {
        System.out.println("---------Welcome to Whot Game--------");
    }

    public int getNumberOfPlayers(Scanner keyboard) {
        System.out.println("How many players are playing?");
        while (true) {
            System.out.println("At least 2 users must play. \nPlease enter a number greater than or equal to 2: "); // todo: need to update for only two players.
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

    public List<Player> createPlayerObjects(Scanner keyboard, int numPlayers) {
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

    private void setSpecialEffect(PlayDeck playDeck) {
        switch (playDeck.getDeck().peekLast().getRank().getNum()) {
            case 2:
                specialCard = SpecialCard.PICK_TWO;
                break;
            case 5:
                specialCard = SpecialCard.PICK_THREE;
                break;
            case 14:
                specialCard = SpecialCard.GENERAL_MARKET;
                break;
            case 20:
                specialCard = SpecialCard.WHOT;
                isFirstPlayCardWhot = true;
            default:
                break;
        }
    }


    public void startGame(List<Player> players, MainDeck mainDeck, PlayDeck playDeck, Scanner keyboard) {
        setSpecialEffect(playDeck);
        while (true) {
            for(Player currentPlayer: players) {
                switch (specialCard) {
                    case PICK_TWO:
                        playPickTwo(currentPlayer, mainDeck, playDeck, keyboard);
                        break;
                    case PICK_THREE:
                        playPickThree(currentPlayer, mainDeck, playDeck, keyboard);
                        break;
                    case GENERAL_MARKET:
                        playGeneralMarket(currentPlayer, mainDeck, keyboard);  // implementing here because we have just two players. ideally to implement in switch case.
                        break;
                    case WHOT:
                        if (isFirstPlayCardWhot) setWhotRequest(keyboard);
                        playWhot(currentPlayer, players, mainDeck, playDeck, keyboard);
                        break;
                    default:
                        play(currentPlayer, mainDeck, playDeck, keyboard);
                        break;
                }
            }
        }
    }

    private void play(Player currentPlayer, MainDeck mainDeck, PlayDeck playDeck, Scanner keyboard) {
        System.out.println(currentPlayer.getPlayerName() + "'s turn to play");

        while (true) {
            System.out.print("Call card: " + playDeck.getDeck().peekLast());
            System.out.println();
            int currentPlayerChoice = getPlayerMenuChoice(keyboard);

            if (currentPlayerChoice == 1) {
                System.out.println(currentPlayer.getPlayerCards());
            } else if (currentPlayerChoice == 2) {
                currentPlayer.goMarket(mainDeck);
                specialCard = SpecialCard.NONE;
                return;
            } else {
                boolean isValidPlay = handleCardPlay(currentPlayer, playDeck, keyboard, validateNormalCard);
                if (isValidPlay) return;
            }
        }

    }

    private void playPickTwo(Player currentPlayer, MainDeck mainDeck, PlayDeck playDeck, Scanner keyboard) {
        System.out.println(currentPlayer.getPlayerName() + "'s turn to play");

        while (true) {
            System.out.print("Call card: " + playDeck.getDeck().peekLast());
            System.out.println();
            System.out.println(currentPlayer.getPlayerName() + ", you have to pick 2 cards from market or defend");

            int playerChoice = getPlayerMenuChoice(keyboard);
            if (playerChoice == 1) {
                System.out.println(currentPlayer.getPlayerCards());
            } else if (playerChoice == 2) {
                currentPlayer.goMarketForTwo(mainDeck);
                specialCard = SpecialCard.NONE;
                return;
            } else {
                boolean isValidPlay = handleCardPlay(currentPlayer, playDeck, keyboard, validateSpecialCard);
                if (isValidPlay) return;
            }
        }
    }


    private void playPickThree(Player currentPlayer, MainDeck mainDeck, PlayDeck playDeck, Scanner keyboard) {
        System.out.println(currentPlayer.getPlayerName() + "'s turn to play");
        System.out.println(currentPlayer.getPlayerName() + ", you have to pick 3 cards from market or defend");

        while (true) {
            System.out.print("Call card: " + playDeck.getDeck().peekLast());
            System.out.println();

            int playerChoice = getPlayerMenuChoice(keyboard);
            if (playerChoice == 1) {
                System.out.println(currentPlayer.getPlayerCards());
            } else if (playerChoice == 2) {
                currentPlayer.goMarketForThree(mainDeck);
                specialCard = SpecialCard.NONE;
                return;
            } else {
                boolean isValidPlay = handleCardPlay(currentPlayer, playDeck, keyboard, validateSpecialCard);
                if (isValidPlay) return;
            }
        }
    }

    private void playGeneralMarket (Player currentPlayer, MainDeck mainDeck, Scanner keyboard) {
        System.out.println(currentPlayer.getPlayerName() + " you have to go to general market");

        int playerChoice = generalMarketMenu(keyboard);
        if (playerChoice == 1) {
            currentPlayer.goMarket(mainDeck);
            specialCard = SpecialCard.NONE;
        }

    }

    private void playWhot(Player currentPlayer, List<Player> players, MainDeck mainDeck, PlayDeck playDeck, Scanner keyboard) {
        System.out.println(currentPlayer.getPlayerName() + "'s turn to play");
        System.out.println("You can either play a card with a suit of " + whotRequest + " or go to market");

        while (true) {
            System.out.println("Call card: " + playDeck.getDeck().peekLast());

            int playerChoice = getPlayerMenuChoice(keyboard);
            if (playerChoice == 1) {
                System.out.println(currentPlayer.getPlayerCards());
            } else if (playerChoice == 2) {
                currentPlayer.goMarket(mainDeck);
                specialCard = SpecialCard.WHOT;
                return;
            } else {
                boolean isValidPlay = handleCardPlay(currentPlayer, playDeck, keyboard, validateWhotCard);
                if (isValidPlay) return;
            }

        }

    }

    private boolean handleCardPlay(Player currentPlayer, PlayDeck playDeck, Scanner keyboard, CardValidator validator) {
        int cardPosition = getCardPosition(currentPlayer, keyboard);
        Card playedCard = currentPlayer.playCard(cardPosition);
        Card callCard = playDeck.getDeck().peekLast();

        if (playedCard.getRank().getNum() != 20 && !validator.validateCard(callCard, playedCard)) {
            System.out.println("Card does not match play card. Select another matching card or go to market");
            return false;
        }

        if (playedCard.getRank().getNum() == 20 && (List.of(2, 3).contains(callCard.getRank().getNum())) && !validator.validateCard(callCard, playedCard)) {
            System.out.println("Card does not match play card. Select another matching card or go to market");
            return false;
        }

        playDeck.addCard(playedCard);
        currentPlayer.getPlayerCards().remove(cardPosition - 1);

        if (!playedCard.getRank().isSpecial()) {
            specialCard = SpecialCard.NONE;
            return true;
        }

        return handleSpecialCard(playedCard, currentPlayer, keyboard);
    }

    private boolean handleSpecialCard(Card matchedCard, Player currentPlayer, Scanner keyboard) {
        int specialRank = matchedCard.getRank().getNum();
        switch(specialRank) {
            case 1:
            case 8:
                System.out.println(currentPlayer.getPlayerName() + " gets to play another card");
                return false;
            case 2:
                specialCard = SpecialCard.PICK_TWO;
                return true;
            case 5:
                specialCard = SpecialCard.PICK_THREE;
                return true;
            case 14:
                specialCard = SpecialCard.GENERAL_MARKET;
                return true;
            case 20:
                specialCard = SpecialCard.WHOT;
                setWhotRequest(keyboard);
                return true;
            default:
                break;
        }
        throw new IllegalStateException("Unexpected Special card: " + matchedCard.getRank() + "of" + matchedCard.getSuit() );
    }

    private int getPlayerMenuChoice(Scanner keyboard) {
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

    private int generalMarketMenu(Scanner keyboard) {
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


    private int getCardPosition(Player currentPlayer, Scanner keyboard) {
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

    private void setWhotRequest(Scanner keyboard) {
        while (true) {
            System.out.println("Request Suit to Play. \nPlease request only a valid suit (CIRCLE, SQUARE, TRIANGLE, CROSS, STAR)");
            whotRequest = keyboard.nextLine();
            if (contains(whotRequest)) {
                return;
            }
        }
    }

    public static boolean contains(String userRequest){
        for (Suit suit: Suit.values()) {
            if(suit.name().equalsIgnoreCase(userRequest)) {
                return true;
            }
        }
        return false;
    }
    

    CardValidator validateNormalCard = (callCard, playedCard) -> {
        boolean rankMatch = callCard.getRank().getNum() == playedCard.getRank().getNum();
        boolean suitMatch = callCard.getSuit().name().equals(playedCard.getSuit().name());
        return rankMatch || suitMatch;
    };

    CardValidator validateSpecialCard = (Card callCard, Card playedCard) -> callCard.getRank().getNum() == playedCard.getRank().getNum();

    CardValidator validateWhotCard = (Card callCard, Card playedCard) -> playedCard.getSuit().name().equalsIgnoreCase(whotRequest);

}
