package whot.player;

import whot.io.IOHandler;
import whot.card.Card;
import whot.card.CardValidator;
import whot.card.SpecialCard;
import whot.deck.MainDeck;
import whot.deck.PlayDeck;
import whot.util.PlayRules;

import java.util.List;
import java.util.ListIterator;

public class ComputerPlayer extends Player {

    private int numberOfMarketCards;
    private SpecialCard specialCard = SpecialCard.NONE;
    private String whotRequest;
    private boolean computerPlayerWins = false;
    private boolean goToMarket = false;
    private final IOHandler io;

    public ComputerPlayer(String name, IOHandler io) {
        super(name);
        this.io = io;
    }

    public SpecialCard getSpecialCard() {
        return specialCard;
    }

    public String getWhotRequest() {
        return whotRequest;
    }

    public boolean isComputerPlayerWinner() {
        return computerPlayerWins;
    }

    public void play(MainDeck mainDeck, PlayDeck playDeck) {
        numberOfMarketCards = 1;
        io.println();
        io.println(getPlayerName() + "'s turn to play");
        while (true) {
            io.println();
            io.println("Call card: " + playDeck.getDeck().peekLast());
            io.println();

            boolean isValidPlay = handleCardPlay(mainDeck, playDeck, PlayRules.validateNormalCard());
            if (goToMarket) {
                io.delay(3500);
                goMarket(mainDeck, numberOfMarketCards);
                specialCard = SpecialCard.NONE;
                goToMarket = false;
            }
            if (isValidPlay) return;

        }

    }

    public void playPickTwoOrThree(MainDeck mainDeck, PlayDeck playDeck, int numCards) {
        numberOfMarketCards = numCards;
        io.println();
        io.println(getPlayerName() + "'s turn to play");
        io.println(getPlayerName() + ", you have to pick " +  numCards + " cards from market or defend");

        while (true) {
            io.println();
            io.println("Call card: " + playDeck.getDeck().peekLast());

            boolean isValidPlay = handleCardPlay(mainDeck, playDeck, PlayRules.validateSpecialCard());
            if (goToMarket) {
                io.delay(3500);
                goMarket(mainDeck, numberOfMarketCards);
                specialCard = SpecialCard.NONE;
                goToMarket = false;
            }
            if (isValidPlay) {
                specialCard = SpecialCard.NONE;
                return;
            }

        }
    }

    public void playGeneralMarket (MainDeck mainDeck) {
        io.println(getPlayerName() + " is going to general market");
        io.delay(2000);
        goMarket(mainDeck, 1);
        specialCard = SpecialCard.NONE;
    }

    public void playWhot(MainDeck mainDeck, PlayDeck playDeck, String whotRequest) {
        this.whotRequest = whotRequest;
        numberOfMarketCards = 1;
        io.println();
        io.println(getPlayerName() + "'s turn to play");
        io.println(getPlayerName() + " can either play a card with a suit of " + whotRequest + ", defend with a suit of whot or go to market");

        while (true) {
            io.println();
            io.println("Call card: " + playDeck.getDeck().peekLast());

            boolean isValidPlay = handleCardPlay(mainDeck, playDeck, PlayRules.validateWhotCard(whotRequest));
            if(goToMarket) {
                io.delay(3500);
                goMarket(mainDeck, numberOfMarketCards);
                specialCard = SpecialCard.WHOT;
                goToMarket = false;
            }
            if (isValidPlay) return;

        }

    }


    private boolean handleCardPlay(MainDeck mainDeck, PlayDeck playDeck, CardValidator validator) {
        Card callCard = playDeck.getDeck().peekLast();
        for (ListIterator<Card> iterator = getPlayerCards().listIterator(); iterator.hasNext();) {
            Card card = iterator.next();
            if (card.getRank().getNum() != 20 && !validator.validateCard(callCard, card)) {
                continue;
            }
            if (card.getRank().getNum() == 20 && (List.of(2, 5).contains(callCard.getRank().getNum()))) {
                continue;
            }

            playDeck.addCard(card);
            io.delay(3500);
            io.println(getPlayerName() + " played " + card);
            iterator.remove();
            if (getPlayerCards().isEmpty()) {
                io.println();
                io.println("Winner: " + getPlayerName());
                computerPlayerWins = true;
                return true;
            }

            if (!card.getRank().isSpecial()) {
                specialCard = SpecialCard.NONE;
                return true;
            }

            return handleSpecialCard(card);

        }
        goToMarket = true;
        return true;

    }

    private boolean handleSpecialCard(Card matchedCard) {
        int specialRank = matchedCard.getRank().getNum();
        switch(specialRank) {
            case 1:
            case 8:
                io.println();
                io.println(getPlayerName() + " gets to play another card");
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

    public void setWhotRequest() {
        int firstCardIndex = 0;
        whotRequest = getPlayerCards().get(firstCardIndex).getSuit().name();
        io.println(getPlayerName() + " requested a " + whotRequest);
    }
}
