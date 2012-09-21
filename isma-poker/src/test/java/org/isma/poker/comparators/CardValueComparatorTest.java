package org.isma.poker.comparators;

import org.junit.Test;

import static junit.framework.Assert.assertTrue;
import static org.isma.poker.model.FiftyTwoCardsEnum.*;

public class CardValueComparatorTest {
    private CardValueComparator comp = new CardValueComparator();

    @Test
    public void test_valueComparaison() {
        assertTrue(comp.compare(EIGHT_OF_DIAMONDS.getCard(), ACE_OF_DIAMONDS.getCard()) < 0);
        assertTrue(comp.compare(ACE_OF_DIAMONDS.getCard(), EIGHT_OF_DIAMONDS.getCard()) > 0);
        assertTrue(comp.compare(ACE_OF_DIAMONDS.getCard(), ACE_OF_CLUBS.getCard()) == 0);
    }
}
