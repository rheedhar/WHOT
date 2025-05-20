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
        Card marketCard = market.getTopCard();
        playerCards.add(marketCard);
        System.out.println("Market Card: " + marketCard);
    }

    public void goMarketForTwo(MainDeck market) {
        Card marketCard1 = market.getTopCard();
        Card marketCard2 = market.getTopCard();

        playerCards.add(marketCard1);
        playerCards.add(marketCard2);

        List<Card> marketCards = List.of(marketCard1, marketCard2);
        System.out.println("Market Cards: " + marketCards);
    }

    public void goMarketForThree(MainDeck market) {
        Card marketCard1 = market.getTopCard();
        Card marketCard2 = market.getTopCard();
        Card marketCard3 = market.getTopCard();

        playerCards.add(marketCard1);
        playerCards.add(marketCard2);
        playerCards.add(marketCard3);

        List<Card> marketCards = List.of(marketCard1, marketCard2, marketCard3);
        System.out.println("Market Cards: " + marketCards);
    }

    public String announceLastCard() {
        return "Last Card Remaining";
    }

    public String announceSemiLastCard() {
        return "Semi Last Card Remaining";
    }

}
