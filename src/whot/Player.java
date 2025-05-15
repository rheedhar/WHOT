package whot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Player {
    private static HashMap<String, List<Card>> currentPlayers = new HashMap<>();
    private String playerName;
    private List<Card> playerCards = new ArrayList<>();

    public Player(String playerName) {
        this.playerName = playerName;
        currentPlayers.put(this.playerName, playerCards);
    }

    public static HashMap<String, List<Card>> getCurrentPlayers() {
        return currentPlayers;
    }

    public List<Card> getPlayerCards() {
        return playerCards;
    }

    public String getPlayerName() {
        return playerName;
    }

    public Card playCard(int cardNum) {
        int index = cardNum - 1;
        return playerCards.get(index);
    }

    public void goMarket(MainDeck market) {
        playerCards.add(market.getTopCard());
    }

    public void goMarketForTwo(MainDeck market) {
        playerCards.add(market.getTopCard());
        playerCards.add(market.getTopCard());
    }

    public String announceLastCard() {
        return "Last Card Remaining";
    }

    public String announceSemiLastCard() {
        return "Semi Last Card Remaining";
    }

}
