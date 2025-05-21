package whot.card;

@FunctionalInterface
public interface CardValidator {
    boolean validateCard(Card callCard, Card playedCard);
}
