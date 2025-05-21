package whot.util;

import whot.card.Card;
import whot.card.CardValidator;

public final class PlayRules {

    private PlayRules() {};

    public static CardValidator validateNormalCard() {
        return (callCard, playedCard) -> {
            boolean rankMatch = callCard.getRank().getNum() == playedCard.getRank().getNum();
            boolean suitMatch = callCard.getSuit().name().equals(playedCard.getSuit().name());
            return rankMatch || suitMatch;
        };
    }

    public static CardValidator validateSpecialCard() {
        return (Card callCard, Card playedCard) -> callCard.getRank().getNum() == playedCard.getRank().getNum();
    }

    public static CardValidator validateWhotCard(String whotRequest) {
        return (Card callCard, Card playedCard) -> playedCard.getSuit().name().equalsIgnoreCase(whotRequest);
    }
}
