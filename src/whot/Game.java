package whot;

import whot.card.Card;
import whot.card.CardValidator;
import whot.card.SpecialCard;
import whot.card.Suit;
import whot.deck.MainDeck;
import whot.deck.PlayDeck;
import whot.player.ComputerPlayer;
import whot.player.Player;
import whot.util.InputMenu;
import whot.util.PlayRules;

import java.util.*;

public class Game {
    private boolean isGameActive;
    private int gameRound;
    private String whotRequest;
    private SpecialCard specialCard = SpecialCard.NONE;
    private boolean isFirstPlayCardWhot = false;
    private MainDeck mainDeck;
    private PlayDeck playDeck;



    public void setGameRound(int gameRound) {
        this.gameRound = gameRound;
    }

    public void welcomePlayers() {
        System.out.println("---------Welcome to Whot Game--------");
    }

    public int setGameOption(Scanner keyboard) {
        System.out.println("- Enter 1 for single player");
        System.out.println("- Enter 2 for multiple players (not supported at this time)");
        while (true) {
            try {
                int gameType = Integer.parseInt(keyboard.nextLine());

                if (gameType == 1) {
                    return gameType;
                } else {
                    System.out.println("- Please enter a value of 1");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer");
            }
        }

    }

    public List<Player> createPlayerObjects(Scanner keyboard, int gameOption) {
        List<Player> playersTemp = new ArrayList<>(2);
        if (gameOption == 1) {
           while (true) {
               System.out.println("Please enter your name");
               String playerName = keyboard.nextLine();

               if (playerName.isBlank()) {
                   System.out.println("You need to have a name");
               } else {
                   playersTemp.add(new Player(playerName));
                   break;
               }
           }

           playersTemp.add(new ComputerPlayer("Mia"));
           System.out.println("You are playing against " + playersTemp.get(1).getPlayerName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.err.println("Thread was interrupted " + e.getMessage());
            }

       }
        return playersTemp;
    }

    private void setSpecialEffect(PlayDeck playDeck) {
        switch (playDeck.getDeck().peekLast().getRank().getNum()) {
            case 1:
            case 8:
                specialCard = SpecialCard.HOLD_ON;
                break;
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

    private void gameInit(List<Player> players) {
        for (Player player: players) {
            player.getPlayerCards().clear();
        }
        isGameActive = true;
        // create deck and deal cards to each player
        mainDeck = new MainDeck();
        playDeck = mainDeck.dealCards(players, 5);
        setSpecialEffect(playDeck);
    }

    public void startGame(List<Player> players, Scanner keyboard) {
        gameInit(players);
        printScoreBoard();
        System.out.println("--------ROUND " + gameRound + "----------");
        while (isGameActive) {
            for(Player currentPlayer: players) {
                if (!isGameActive) break;

                if(currentPlayer instanceof  ComputerPlayer){
                    computerPlays((ComputerPlayer) currentPlayer);
                    continue;
                }
                switch (specialCard) {
                    case HOLD_ON:
                        specialCard = SpecialCard.NONE;
                        continue;
                    case PICK_TWO:
                        playPickTwoOrThree(currentPlayer, mainDeck, playDeck, keyboard, 2);
                        break;
                    case PICK_THREE:
                        playPickTwoOrThree(currentPlayer, mainDeck, playDeck, keyboard, 3);
                        break;
                    case GENERAL_MARKET:
                        playGeneralMarket(currentPlayer, mainDeck, keyboard);  // implementing here because we have just two players. ideally to implement in switch case.
                        break;
                    case WHOT:
                        if (isFirstPlayCardWhot) {
                            System.out.println("Call card: " + playDeck.getDeck().peekLast());
                            System.out.println(currentPlayer.getPlayerName() + "'s turn to play");
                            System.out.println("Your cards: " + currentPlayer.getFormattedPlayerCards());
                            setWhotRequest(keyboard);
                            isFirstPlayCardWhot = false;
                            continue;
                        }
                        playWhot(currentPlayer, mainDeck, playDeck, keyboard);
                        break;
                    default:
                        play(currentPlayer, mainDeck, playDeck, keyboard);
                        break;
                }
            }
        }

        System.out.println("Current Scores: ");
        printScoreBoard();
        boolean isGameReset = gameReset(keyboard);
        if (isGameReset) {
            ++gameRound;
            startGame(players, keyboard);
        }

    }

    public void computerPlays(ComputerPlayer computerPlayer) {
        switch (specialCard) {
            case PICK_TWO:
                computerPlayer.playPickTwoOrThree(mainDeck, playDeck, 2);
                break;
            case PICK_THREE:
                computerPlayer.playPickTwoOrThree(mainDeck, playDeck, 3);
                break;
            case GENERAL_MARKET:
                computerPlayer.playGeneralMarket(mainDeck);
                break;
            case WHOT:
                if (isFirstPlayCardWhot) {
                    System.out.println();
                    System.out.print("Call card: " + playDeck.getDeck().peekLast());
                    System.out.println(computerPlayer.getPlayerName() + "'s turn to play");
                    computerPlayer.setWhotRequest();
                    isFirstPlayCardWhot = false;
                }
                computerPlayer.playWhot(mainDeck, playDeck, whotRequest);
                break;
            default:
                computerPlayer.play(mainDeck, playDeck);
                break;
        }
        specialCard = computerPlayer.getSpecialCard();
        whotRequest = computerPlayer.getWhotRequest();
        isGameActive = !computerPlayer.isComputerPlayerWinner();

    }


    private void play(Player currentPlayer, MainDeck mainDeck, PlayDeck playDeck, Scanner keyboard) {
        try {
            Thread.sleep(1500);
        } catch(InterruptedException e) {
            System.err.println("Thread was interrupted" + e.getMessage());
        }
        System.out.println();
        System.out.println(currentPlayer.getPlayerName() + "'s turn to play");

        while (true) {
            System.out.println();
            System.out.println("Call card: " + playDeck.getDeck().peekLast());
            System.out.println("Your cards: " + currentPlayer.getFormattedPlayerCards());
            System.out.println();
            int currentPlayerChoice = InputMenu.getPlayerMenuChoice(keyboard);

            if (currentPlayerChoice == 1) {
                currentPlayer.goMarket(mainDeck, 1);
                specialCard = SpecialCard.NONE;
                return;
            } else {
                boolean isValidPlay = handleCardPlay(currentPlayer, playDeck, keyboard, PlayRules.validateNormalCard());
                if (isValidPlay) return;
            }
        }

    }

    private void playPickTwoOrThree(Player currentPlayer, MainDeck mainDeck, PlayDeck playDeck, Scanner keyboard, int numCards) {
        try {
            Thread.sleep(1500);
        } catch(InterruptedException e) {
            System.err.println("Thread was interrupted" + e.getMessage());
        }
        System.out.println();
        System.out.println(currentPlayer.getPlayerName() + "'s turn to play");
        System.out.println(currentPlayer.getPlayerName() + ", you have to pick " +  numCards + " cards from market or defend");

        while (true) {
            System.out.println();
            System.out.println("Call card: " + playDeck.getDeck().peekLast());
            System.out.println("Your cards: " + currentPlayer.getFormattedPlayerCards());
            System.out.println();

            int playerChoice = InputMenu.getPlayerMenuChoice(keyboard);
            if (playerChoice == 1) {
                currentPlayer.goMarket(mainDeck, numCards);
                specialCard = SpecialCard.NONE;
                return;
            }  else {
                boolean isValidPlay = handleCardPlay(currentPlayer, playDeck, keyboard, PlayRules.validateSpecialCard());
                if (isValidPlay) {
                    specialCard = SpecialCard.NONE;
                    return;
                }
            }
        }
    }



    private void playGeneralMarket (Player currentPlayer, MainDeck mainDeck, Scanner keyboard) {
        try {
            Thread.sleep(1500);
        } catch(InterruptedException e) {
            System.err.println("Thread was interrupted" + e.getMessage());
        }
        System.out.println(currentPlayer.getPlayerName() + "'s turn to play");
        System.out.println();
        System.out.println("Call card: " + playDeck.getDeck().peekLast());
        System.out.println(currentPlayer.getPlayerName() + " you have to go to general market");
        System.out.println();
        int playerChoice = InputMenu.generalMarketMenu(keyboard);
        if (playerChoice == 1) {
            currentPlayer.goMarket(mainDeck, 1);
            specialCard = SpecialCard.NONE;
        }

    }

    private void playWhot(Player currentPlayer, MainDeck mainDeck, PlayDeck playDeck, Scanner keyboard) {
        try {
            Thread.sleep(1500);
        } catch(InterruptedException e) {
            System.err.println("Thread was interrupted" + e.getMessage());
        }
        System.out.println();
        System.out.println(currentPlayer.getPlayerName() + "'s turn to play");
        System.out.println("You can either play a card with a suit of " + whotRequest + ", defend with a suit of whot or go to market");

        while (true) {
            System.out.println();
            System.out.println("Call card: " + playDeck.getDeck().peekLast());
            System.out.println("Your cards: " + currentPlayer.getFormattedPlayerCards());
            System.out.println();

            int playerChoice = InputMenu.getPlayerMenuChoice(keyboard);
            if (playerChoice == 1) {
                currentPlayer.goMarket(mainDeck, 1);
                specialCard = SpecialCard.WHOT;
                return;
            } else {
                boolean isValidPlay = handleCardPlay(currentPlayer, playDeck, keyboard, PlayRules.validateWhotCard(whotRequest));
                if (isValidPlay) return;
            }

        }

    }

    private boolean handleCardPlay(Player currentPlayer, PlayDeck playDeck, Scanner keyboard, CardValidator validator) {
        int cardPosition = InputMenu.getCardPosition(currentPlayer, keyboard);
        Card playedCard = currentPlayer.playCard(cardPosition);
        Card callCard = playDeck.getDeck().peekLast();

        if (playedCard.getRank().getNum() != 20 && !validator.validateCard(callCard, playedCard)) {
            System.out.println("Card does not match call card. Select another matching card or go to market");
            return false;
        }

        if (playedCard.getRank().getNum() == 20 && (List.of(2, 5).contains(callCard.getRank().getNum())) && !validator.validateCard(callCard, playedCard)) {
            System.out.println("Card does not match play card. Select another matching card or go to market");
            return false;
        }

        playDeck.addCard(playedCard);
        System.out.println(currentPlayer.getPlayerName() + " played " + playedCard);
        currentPlayer.getPlayerCards().remove(cardPosition - 1);
        if (currentPlayer.getPlayerCards().isEmpty()) {
            System.out.println();
            System.out.println("Winner: " + currentPlayer.getPlayerName());
            isGameActive = false;
            Player.updatePlayersScores(currentPlayer);
            return true;
        }

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
                System.out.println();
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

    private boolean gameReset(Scanner keyboard) {
        System.out.println();
        int playerChoice = InputMenu.continuePlaying(keyboard);
        return playerChoice != 2;

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

    private void printScoreBoard() {
        System.out.println("==========Score Board===========");
        for(Map.Entry<String, Integer> entry: Player.getPlayersScores().entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " points(s)");
        }
        System.out.println("================================");
    }

    public static boolean contains(String userRequest){
        for (Suit suit: Suit.values()) {
            if(suit.name().equalsIgnoreCase(userRequest)) {
                return true;
            }
        }
        return false;
    }

}
