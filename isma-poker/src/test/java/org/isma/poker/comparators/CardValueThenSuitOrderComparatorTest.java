package org.isma.poker.comparators;

import org.isma.poker.model.Hand;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static junit.framework.Assert.assertEquals;
import static org.isma.poker.model.FiftyTwoCardsEnum.*;

public class CardValueThenSuitOrderComparatorTest {
    private final CardValueThenSuitOrderComparator comp = new CardValueThenSuitOrderComparator();
    private final Hand hand = new Hand();

    @Before
    public void setUp() throws Exception {
        hand.clear();
    }

    @Test
    public void test_sortValueFirstly() {
        hand.add(NINE_OF_CLUBS);
        hand.add(EIGHT_OF_DIAMONDS);
        hand.add(EIGHT_OF_CLUBS);
        hand.add(EIGHT_OF_HEARTS);
        hand.add(EIGHT_OF_SPADES);

        Collections.sort(hand, comp);
        assertEquals("[9 of Clubs, 8 of Spades, 8 of Hearts, 8 of Diamonds, 8 of Clubs]", hand.toString());
    }

    @Test
    public void test_sortValues() {
        hand.add(EIGHT_OF_DIAMONDS);
        hand.add(SEVEN_OF_DIAMONDS);
        hand.add(SIX_OF_DIAMONDS);
        hand.add(FIVE_OF_DIAMONDS);

        Collections.sort(hand, comp);
        assertEquals("[8 of Diamonds, 7 of Diamonds, 6 of Diamonds, 5 of Diamonds]", hand.toString());
    }
}
