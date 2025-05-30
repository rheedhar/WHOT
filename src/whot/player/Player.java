package whot.player;

import whot.card.Card;
import whot.deck.MainDeck;
import whot.io.IOHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Player {
    private final String playerName;
    private final List<Card> playerCards = new ArrayList<>();

    public Player(String playerName) {
        this.playerName = playerName;
    }

    public List<Card> getPlayerCards() {
        return playerCards;
    }

    public String getFormattedPlayerCards() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        for(int i = 0; i < playerCards.size(); i++) {
            stringBuilder
                    .append("(")
                    .append(i + 1)
                    .append(") ")
                    .append(playerCards.get(i));

            if (i != playerCards.size() - 1) {
                stringBuilder.append(", ");
            }
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public String getPlayerName() {
        return playerName;
    }

    public Card playCard(int cardNum) {
        int index = cardNum - 1;
        return playerCards.get(index);
    }

    public void goMarket(MainDeck market, int numCards, IOHandler io) {
        int totalCardsToPick = numCards;
        String cardText = numCards == 1 ? "card" : "cards";
        while (totalCardsToPick > 0) {
            Card marketCard = market.getTopCard();
            playerCards.add(marketCard);
            --totalCardsToPick;
        }
        io.println(playerName + " has picked " + numCards + " " + cardText + " from market");

    }


    public String announceLastCard() {
        return "Last Card Remaining";
    }

    public String announceSemiLastCard() {
        return "Semi Last Card Remaining";
    }

}
