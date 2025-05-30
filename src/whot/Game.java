package whot;

import whot.card.Card;
import whot.card.CardValidator;
import whot.card.SpecialCard;
import whot.card.Suit;
import whot.deck.MainDeck;
import whot.deck.PlayDeck;
import whot.io.IOHandler;
import whot.player.ComputerPlayer;
import whot.player.Player;
import whot.util.InputMenu;
import whot.util.PlayRules;
import whot.util.ScoreBoard;

import java.util.*;

public class Game {
    private boolean isGameActive;
    private int gameRound;
    private String whotRequest;
    private SpecialCard specialCard = SpecialCard.NONE;
    private boolean isFirstPlayCardWhot = false;
    private MainDeck mainDeck;
    private PlayDeck playDeck;
    private final ScoreBoard scoreBoard;
    public final IOHandler io;

    public Game(IOHandler io) {
        this.io = io;
        scoreBoard = new ScoreBoard(this.io);
    }

    public void start() {
        // Welcome players to the game
        io.println("---------Welcome to Whot Game--------");

        //get number of players
        int gameOption = setGameOption();

        // create players and store in list
        List<Player> players = createPlayerObjects(gameOption);
        gameRound = 1;
        startGame(players);
    }

    private int setGameOption() {
        io.println("- Enter 1 for single player");
        io.println("- Enter 2 for multiple players (not supported at this time)");
        while (true) {
            try {
                int gameType = Integer.parseInt(io.readLine());

                if (gameType == 1) {
                    return gameType;
                } else {
                    io.println("- Please enter a value of 1");
                }
            } catch (NumberFormatException e) {
                io.println("Please enter a valid integer");
            }
        }

    }

    private List<Player> createPlayerObjects(int gameOption) {
        List<Player> playersTemp = new ArrayList<>(2);
        if (gameOption == 1) {
           while (true) {
               io.println("Please enter your name");
               String playerName = io.readLine();

               if (playerName.isBlank()) {
                   io.println("You need to have a name");
               } else {
                   playersTemp.add(new Player(playerName));
                   scoreBoard.registerPlayer(playerName);

                   ComputerPlayer computerPlayer = new ComputerPlayer("Mia", io);
                   playersTemp.add(computerPlayer);
                   scoreBoard.registerPlayer(computerPlayer.getPlayerName());
                   break;
               }
           }

           io.println("You are playing against " + playersTemp.get(1).getPlayerName());
           io.delay(1000);

       }
        return playersTemp;
    }

    private void setSpecialEffect() {
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


    public void startGame(List<Player> players) {
        isGameActive = true;
        // create deck and deal cards to each player
        mainDeck = new MainDeck();
        mainDeck.dealCards(players, 5);
        playDeck = mainDeck.getPlayDeck();
        setSpecialEffect();

        io.println("--------ROUND " + gameRound + "----------");
        while (isGameActive) {
            for(Player currentPlayer: players) {
                if (!isGameActive) break;

                if(currentPlayer instanceof  ComputerPlayer){
                    computerPlays((ComputerPlayer) currentPlayer);
                    continue;
                }
                playerPlays(currentPlayer);
            }
        }

        resetGame(players);
    }


    private void playerPlays(Player currentPlayer) {
        switch (specialCard) {
            case HOLD_ON:
                specialCard = SpecialCard.NONE;
                break;
            case PICK_TWO:
                playPickTwoOrThree(currentPlayer, mainDeck, playDeck, 2);
                break;
            case PICK_THREE:
                playPickTwoOrThree(currentPlayer, mainDeck, playDeck,3);
                break;
            case GENERAL_MARKET:
                playGeneralMarket(currentPlayer, mainDeck);  // implementing here because we have just two players. ideally to implement in switch case.
                break;
            case WHOT:
                if (isFirstPlayCardWhot) {
                    io.println("Call card: " + playDeck.getDeck().peekLast());
                    io.println(currentPlayer.getPlayerName() + "'s turn to play");
                    io.println("Your cards: " + currentPlayer.getFormattedPlayerCards());
                    setWhotRequest();
                    isFirstPlayCardWhot = false;
                    break;
                }
                playWhot(currentPlayer, mainDeck, playDeck);
                break;
            default:
                play(currentPlayer, mainDeck, playDeck);
                break;
        }
    }

    private void play(Player currentPlayer, MainDeck mainDeck, PlayDeck playDeck) {
        try {
            Thread.sleep(1500);
        } catch(InterruptedException e) {
            System.err.println("Thread was interrupted" + e.getMessage());
        }
        io.println();
        io.println(currentPlayer.getPlayerName() + "'s turn to play");

        while (true) {
            io.println();
            io.println("Call card: " + playDeck.getDeck().peekLast());
            io.println("Your cards: " + currentPlayer.getFormattedPlayerCards());
            io.println();
            int currentPlayerChoice = InputMenu.getPlayerMenuChoice(io);

            if (currentPlayerChoice == 1) {
                currentPlayer.goMarket(mainDeck, 1, io);
                specialCard = SpecialCard.NONE;
                return;
            } else {
                boolean isValidPlay = handleCardPlay(currentPlayer, playDeck, PlayRules.validateNormalCard());
                if (isValidPlay) return;
            }
        }

    }

    private void playPickTwoOrThree(Player currentPlayer, MainDeck mainDeck, PlayDeck playDeck, int numCards) {
        io.delay(1500);
        io.println();
        io.println(currentPlayer.getPlayerName() + "'s turn to play");
        io.println(currentPlayer.getPlayerName() + ", you have to pick " +  numCards + " cards from market or defend");

        while (true) {
            io.println();
            io.println("Call card: " + playDeck.getDeck().peekLast());
            io.println("Your cards: " + currentPlayer.getFormattedPlayerCards());
            io.println();

            int playerChoice = InputMenu.getPlayerMenuChoice(io);
            if (playerChoice == 1) {
                currentPlayer.goMarket(mainDeck, numCards, io);
                specialCard = SpecialCard.NONE;
                return;
            }  else {
                boolean isValidPlay = handleCardPlay(currentPlayer, playDeck, PlayRules.validateSpecialCard());
                if (isValidPlay) {
                    specialCard = SpecialCard.NONE;
                    return;
                }
            }
        }
    }



    private void playGeneralMarket (Player currentPlayer, MainDeck mainDeck) {
        io.delay(1500);
        io.println(currentPlayer.getPlayerName() + "'s turn to play");
        io.println();
        io.println("Call card: " + playDeck.getDeck().peekLast());
        io.println(currentPlayer.getPlayerName() + " you have to go to general market");
        io.println();
        int playerChoice = InputMenu.generalMarketMenu(io);
        if (playerChoice == 1) {
            currentPlayer.goMarket(mainDeck, 1, io);
            specialCard = SpecialCard.NONE;
        }

    }

    private void playWhot(Player currentPlayer, MainDeck mainDeck, PlayDeck playDeck) {
        io.delay(1500);
        io.println();
        io.println(currentPlayer.getPlayerName() + "'s turn to play");
        io.println("You can either play a card with a suit of " + whotRequest + ", defend with a suit of whot or go to market");

        while (true) {
            io.println();
            io.println("Call card: " + playDeck.getDeck().peekLast());
            io.println("Your cards: " + currentPlayer.getFormattedPlayerCards());
            io.println();

            int playerChoice = InputMenu.getPlayerMenuChoice(io);
            if (playerChoice == 1) {
                currentPlayer.goMarket(mainDeck, 1, io);
                specialCard = SpecialCard.WHOT;
                return;
            } else {
                boolean isValidPlay = handleCardPlay(currentPlayer, playDeck, PlayRules.validateWhotCard(whotRequest));
                if (isValidPlay) return;
            }

        }

    }

    private boolean handleCardPlay(Player currentPlayer, PlayDeck playDeck, CardValidator validator) {
        int cardPosition = InputMenu.getCardPosition(currentPlayer, io);
        Card playedCard = currentPlayer.playCard(cardPosition);
        Card callCard = playDeck.getDeck().peekLast();

        if (playedCard.getRank().getNum() != 20 && !validator.validateCard(callCard, playedCard)) {
            io.println("Card does not match call card. Select another matching card or go to market");
            return false;
        }

        if (playedCard.getRank().getNum() == 20 && (List.of(2, 5).contains(callCard.getRank().getNum())) && !validator.validateCard(callCard, playedCard)) {
            io.println("Card does not match play card. Select another matching card or go to market");
            return false;
        }

        playDeck.addCard(playedCard);
        io.println(currentPlayer.getPlayerName() + " played " + playedCard);
        currentPlayer.getPlayerCards().remove(cardPosition - 1);
        if (currentPlayer.getPlayerCards().isEmpty()) {
            io.println();
            io.println("Winner: " + currentPlayer.getPlayerName());
            isGameActive = false;
            scoreBoard.incrementPlayerScore(currentPlayer.getPlayerName());
            return true;
        }

        if (!playedCard.getRank().isSpecial()) {
            specialCard = SpecialCard.NONE;
            return true;
        }

        return handleSpecialCard(playedCard, currentPlayer);
    }

    private boolean handleSpecialCard(Card matchedCard, Player currentPlayer) {
        int specialRank = matchedCard.getRank().getNum();
        switch(specialRank) {
            case 1:
            case 8:
                io.println();
                io.println(currentPlayer.getPlayerName() + " gets to play another card");
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
                setWhotRequest();
                return true;
            default:
                break;
        }
        throw new IllegalStateException("Unexpected Special card: " + matchedCard.getRank() + "of" + matchedCard.getSuit() );
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
                    io.println();
                    io.print("Call card: " + playDeck.getDeck().peekLast());
                    io.println(computerPlayer.getPlayerName() + "'s turn to play");
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
        if(!isGameActive) {
            scoreBoard.incrementPlayerScore(computerPlayer.getPlayerName());
        }

    }

    private void resetGame(List<Player> players) {
        io.println("Current Scores: ");
        scoreBoard.printScoreBoard();
        boolean isGameReset = gameReset();
        if (isGameReset) {
            ++gameRound;
            startGame(players);
        }
    }

    private boolean gameReset() {
        io.println();
        int playerChoice = InputMenu.continuePlaying(io);
        return playerChoice != 2;

    }

    private void setWhotRequest() {
        while (true) {
            io.println("Request Suit to Play. \nPlease request only a valid suit (CIRCLE, SQUARE, TRIANGLE, CROSS, STAR)");
            whotRequest = io.readLine();
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

}
