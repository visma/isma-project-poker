package org.isma.poker.helper;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static org.isma.poker.model.FiftyTwoCardsEnum.*;

public class CardHelperTest {

    @Test
    public void buildCard() {
        assertEquals(ACE_OF_DIAMONDS.getCard(), CardHelper.parse("A-D"));
        assertEquals(NINE_OF_CLUBS.getCard(), CardHelper.parse("9-C"));
        assertEquals(TEN_OF_SPADES.getCard(), CardHelper.parse("10-S"));
        assertEquals(KNAVE_OF_HEARTS.getCard(), CardHelper.parse("J-H"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void buildCardFailed_invalidValue() {
        CardHelper.parse("1-D");
    }

    @Test(expected = IllegalArgumentException.class)
    public void buildCardFailed_invalidSuit() {
        CardHelper.parse("10-X");
    }
}
