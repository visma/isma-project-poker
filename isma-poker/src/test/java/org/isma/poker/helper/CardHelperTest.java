package org.isma.poker.helper;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static org.isma.poker.model.FiftyTwoCardsEnum.*;

public class CardHelperTest {

    @Test
    public void buildCard() {
        assertEquals(ACE_OF_DIAMONDS.getCard(), CardHelper.toCard("A-D"));
        assertEquals(NINE_OF_CLUBS.getCard(), CardHelper.toCard("9-C"));
        assertEquals(TEN_OF_SPADES.getCard(), CardHelper.toCard("10-S"));
        assertEquals(KNAVE_OF_HEARTS.getCard(), CardHelper.toCard("J-H"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void buildCardFailed_invalidValue() {
        CardHelper.toCard("1-D");
    }

    @Test(expected = IllegalArgumentException.class)
    public void buildCardFailed_invalidSuit() {
        CardHelper.toCard("10-X");
    }
}
