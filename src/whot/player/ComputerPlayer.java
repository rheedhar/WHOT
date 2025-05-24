package whot.player;

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

    public ComputerPlayer(String name) {
        super(name);
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
        System.out.println();
        System.out.println(getPlayerName() + "'s turn to play");
        while (true) {
            System.out.println();
            System.out.println("Call card: " + playDeck.getDeck().peekLast());
            System.out.println();

            boolean isValidPlay = handleCardPlay(mainDeck, playDeck, PlayRules.validateNormalCard());
            if (goToMarket) {
                try {
                    Thread.sleep(3500);
                } catch (InterruptedException e) {
                    System.err.println("Thread was interrupted " + e.getMessage());
                }
                goMarket(mainDeck, numberOfMarketCards);
                specialCard = SpecialCard.NONE;
                goToMarket = false;
            }
            if (isValidPlay) return;

        }

    }

    public void playPickTwoOrThree(MainDeck mainDeck, PlayDeck playDeck, int numCards) {
        numberOfMarketCards = numCards;
        System.out.println();
        System.out.println(getPlayerName() + "'s turn to play");
        System.out.println(getPlayerName() + ", you have to pick " +  numCards + " cards from market or defend");

        while (true) {
            System.out.println();
            System.out.println("Call card: " + playDeck.getDeck().peekLast());

            boolean isValidPlay = handleCardPlay(mainDeck, playDeck, PlayRules.validateSpecialCard());
            if (goToMarket) {
                try {
                    Thread.sleep(3500);
                } catch (InterruptedException e) {
                    System.err.println("Thread was interrupted " + e.getMessage());
                }
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
        System.out.println(getPlayerName() + " is going to general market");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            System.err.println("Thread was interrupted" + e.getMessage());
        }
        goMarket(mainDeck, 1);
        specialCard = SpecialCard.NONE;
    }

    public void playWhot(MainDeck mainDeck, PlayDeck playDeck, String whotRequest) {
        this.whotRequest = whotRequest;
        numberOfMarketCards = 1;
        System.out.println();
        System.out.println(getPlayerName() + "'s turn to play");
        System.out.println(getPlayerName() + " can either play a card with a suit of " + whotRequest + ", defend with a suit of whot or go to market");

        while (true) {
            System.out.println();
            System.out.println("Call card: " + playDeck.getDeck().peekLast());

            boolean isValidPlay = handleCardPlay(mainDeck, playDeck, PlayRules.validateWhotCard(whotRequest));
            if(goToMarket) {
                try {
                    Thread.sleep(3500);
                } catch (InterruptedException e) {
                    System.err.println("Thread was interrupted " + e.getMessage());
                }
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
            try {
                Thread.sleep(3500);
            } catch (InterruptedException e) {
                System.err.println("Thread was interrupted " + e.getMessage());
            }
            System.out.println(getPlayerName() + " played " + card);
            iterator.remove();
            if (getPlayerCards().isEmpty()) {
                System.out.println();
                System.out.println("Winner: " + getPlayerName());
                computerPlayerWins = true;
                Player.updatePlayersScores(this);
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
                System.out.println();
                System.out.println(getPlayerName() + " gets to play another card");
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
        System.out.println(getPlayerName() + " requested a " + whotRequest);
    }
}
