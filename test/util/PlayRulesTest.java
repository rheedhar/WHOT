package util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import whot.card.Card;
import whot.card.CardValidator;
import whot.card.Rank;
import whot.card.Suit;
import whot.util.PlayRules;

import static org.junit.jupiter.api.Assertions.*;

public class PlayRulesTest {

    @Test
    void testValidateNormalCardWhenCallCardAndPlayedCardHaveSameNum() {
        // given
        Card card1 = new Card(Suit.CIRCLE, Rank.SEVEN);
        Card card2 = new Card(Suit.TRIANGLE, Rank.SEVEN);

        // when & then
        CardValidator validator = PlayRules.validateNormalCard();
        assertTrue(validator.validateCard(card1, card2));
    }

    @Test
    void testValidateNormalCardWhenCallCardAndPlayedCardHaveSameRank() {
        // given
        Card card1 = new Card(Suit.CIRCLE, Rank.EIGHT);
        Card card2 = new Card(Suit.CIRCLE, Rank.SEVEN);

        // when & then
        CardValidator validator = PlayRules.validateNormalCard();
        assertTrue(validator.validateCard(card1, card2));
    }

    @Test
    void testValidateNormalCardWhenCallCardAndPlayedCardDontMatch() {
        // given
        Card card1 = new Card(Suit.CIRCLE, Rank.EIGHT);
        Card card2 = new Card(Suit.TRIANGLE, Rank.SEVEN);

        // when & then
        CardValidator validator = PlayRules.validateNormalCard();
        assertFalse(validator.validateCard(card1, card2));
    }

    @Test
    void testValidateSpecialCardWhenNumMatchesOnBothCallCardAndPlayedCard() {
        // given
        Card card1 = new Card(Suit.CIRCLE, Rank.EIGHT);
        Card card2 = new Card(Suit.TRIANGLE, Rank.EIGHT);

        // when & then
        CardValidator validator = PlayRules.validateSpecialCard();
        assertTrue(validator.validateCard(card1, card2));
    }

    @Test
    void testValidateSpecialCardWhenNumDontMatch() {
        Card card1 = new Card(Suit.CIRCLE, Rank.EIGHT);
        Card card2 = new Card(Suit.CIRCLE, Rank.SEVEN);

        // when & then
        CardValidator validator = PlayRules.validateSpecialCard();
        assertFalse(validator.validateCard(card1, card2));
    }

    @Test
    void testValidateWhotCardWhenRequestMatchesCardName() {
        Card card1 = new Card(Suit.WHOT, Rank.TWENTY);
        Card card2 = new Card(Suit.TRIANGLE, Rank.SEVEN);
        String whotRequest = "triangle";

        CardValidator validator = PlayRules.validateWhotCard(whotRequest);
        assertTrue(validator.validateCard(card1, card2));
    }

    @Test
    void testValidateWhotCardWhenRequestDontMatchCardName(){
        Card card1 = new Card(Suit.WHOT, Rank.TWENTY);
        Card card2 = new Card(Suit.TRIANGLE, Rank.SEVEN);
        String whotRequest = "circle";

        CardValidator validator = PlayRules.validateWhotCard(whotRequest);
        assertFalse(validator.validateCard(card1, card2));
    }
}
