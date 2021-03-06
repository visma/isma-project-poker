package org.isma.poker;

import org.isma.poker.HandEvaluator;
import org.isma.poker.model.HandEvaluation;
import org.junit.Test;

import java.util.Comparator;

import static junit.framework.Assert.assertTrue;
import static org.isma.poker.model.HandEvaluation.*;

public class HandEvaluationComparatorTest {
    private Comparator<HandEvaluation> comp = HandEvaluator.HAND_EVALUATION_COMPARATOR;

    @Test
    public void greatherThan() {
        assertTrue(comp.compare(STRAIGHT_FLUSH, FOUR_OF_A_KIND) > 0);
        assertTrue(comp.compare(FOUR_OF_A_KIND, FULL_HOUSE) > 0);
        assertTrue(comp.compare(FULL_HOUSE, FLUSH) > 0);
        assertTrue(comp.compare(FLUSH, STRAIGHT) > 0);
        assertTrue(comp.compare(STRAIGHT, THREE_OF_A_KIND) > 0);
        assertTrue(comp.compare(THREE_OF_A_KIND, TWO_PAIR) > 0);
        assertTrue(comp.compare(TWO_PAIR, PAIR) > 0);
        assertTrue(comp.compare(PAIR, KICKER) > 0);
    }

    @Test
    public void lowerThan() {
        assertTrue(comp.compare(FOUR_OF_A_KIND, STRAIGHT_FLUSH) < 0);
        assertTrue(comp.compare(FULL_HOUSE, FOUR_OF_A_KIND) < 0);
        assertTrue(comp.compare(FLUSH, FULL_HOUSE) < 0);
        assertTrue(comp.compare(STRAIGHT, FLUSH) < 0);
        assertTrue(comp.compare(THREE_OF_A_KIND, STRAIGHT) < 0);
        assertTrue(comp.compare(TWO_PAIR, THREE_OF_A_KIND) < 0);
        assertTrue(comp.compare(PAIR, TWO_PAIR) < 0);
        assertTrue(comp.compare(KICKER, PAIR) < 0);
    }

    @Test
    public void equals() {
        assertTrue(comp.compare(STRAIGHT_FLUSH, STRAIGHT_FLUSH) == 0);
        assertTrue(comp.compare(KICKER, KICKER) == 0);
    }

}
