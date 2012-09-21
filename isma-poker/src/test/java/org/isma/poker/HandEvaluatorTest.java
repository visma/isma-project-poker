package org.isma.poker;

import org.isma.poker.model.Hand;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static org.isma.poker.HandEvaluationEnum.*;
import static org.isma.poker.model.FiftyTwoCardsEnum.*;


public class HandEvaluatorTest {
    private final Hand hand = new Hand();
    private final HandEvaluator evaluator = new HandEvaluator();

    @Before
    public void setUp() throws Exception {
        hand.clear();
    }

    @Test
    public void test_kicker() {
        hand.add(ACE_OF_CLUBS);
        hand.add(KING_OF_CLUBS);
        hand.add(QUEEN_OF_CLUBS);
        hand.add(KNAVE_OF_CLUBS);
        hand.add(NINE_OF_DIAMONDS);
        hand.add(SIX_OF_HEARTS);
        hand.add(TWO_OF_HEARTS);

        assertEquals(KICKER, evaluator.evaluate(hand));
        assertEquals(false, evaluator.isStraightFlush(hand));
        assertEquals(false, evaluator.isFourOfAKind(hand));
        assertEquals(false, evaluator.isFullHouse(hand));
        assertEquals(false, evaluator.isFlush(hand));
        assertEquals(false, evaluator.isStraight(hand));
        assertEquals(false, evaluator.isThreeOfAKind(hand));
        assertEquals(false, evaluator.isTwoPair(hand));
        assertEquals(false, evaluator.isPair(hand));
    }

    @Test
    public void test_pair() {
        hand.add(ACE_OF_CLUBS);
        hand.add(KING_OF_CLUBS);
        hand.add(QUEEN_OF_CLUBS);
        hand.add(SIX_OF_HEARTS);
        hand.add(FIVE_OF_HEARTS);

        hand.add(NINE_OF_SPADES);
        hand.add(NINE_OF_DIAMONDS);
        assertEquals(PAIR, evaluator.evaluate(hand));
        assertEquals(true, evaluator.isPair(hand));
    }

    @Test
    public void test_twoPair() {
        hand.add(ACE_OF_CLUBS);
        hand.add(SIX_OF_HEARTS);
        hand.add(FIVE_OF_HEARTS);

        hand.add(KING_OF_CLUBS);
        hand.add(KING_OF_DIAMONDS);
        hand.add(NINE_OF_SPADES);
        hand.add(NINE_OF_DIAMONDS);
        assertEquals(TWO_PAIR, evaluator.evaluate(hand));
        assertEquals(true, evaluator.isTwoPair(hand));
    }

    @Test
    public void test_threeOfAKind() {
        hand.add(ACE_OF_CLUBS);
        hand.add(KING_OF_CLUBS);
        hand.add(SIX_OF_HEARTS);
        hand.add(FIVE_OF_HEARTS);

        hand.add(NINE_OF_HEARTS);
        hand.add(NINE_OF_SPADES);
        hand.add(NINE_OF_DIAMONDS);
        assertEquals(THREE_OF_A_KIND, evaluator.evaluate(hand));
        assertEquals(true, evaluator.isThreeOfAKind(hand));

    }

    @Test
    public void test_lowStraight() {
        hand.add(ACE_OF_DIAMONDS);
        hand.add(ACE_OF_SPADES);

        hand.add(ACE_OF_CLUBS);
        hand.add(TWO_OF_CLUBS);
        hand.add(THREE_OF_CLUBS);
        hand.add(FOUR_OF_DIAMONDS);
        hand.add(FIVE_OF_DIAMONDS);
        assertEquals(STRAIGHT, evaluator.evaluate(hand));
        assertEquals(true, evaluator.isStraight(hand));
    }

    @Test
    public void test_highStraight() {
        hand.add(ACE_OF_DIAMONDS);
        hand.add(ACE_OF_SPADES);

        hand.add(ACE_OF_CLUBS);
        hand.add(KING_OF_CLUBS);
        hand.add(QUEEN_OF_DIAMONDS);
        hand.add(KNAVE_OF_DIAMONDS);
        hand.add(TEN_OF_CLUBS);
        assertEquals(STRAIGHT, evaluator.evaluate(hand));
        assertEquals(true, evaluator.isStraight(hand));
    }

    @Test
    public void test_notAStraight() {
        hand.add(EIGHT_OF_HEARTS);
        hand.add(NINE_OF_HEARTS);

        hand.add(KING_OF_DIAMONDS);
        hand.add(ACE_OF_CLUBS);
        hand.add(TWO_OF_CLUBS);
        hand.add(THREE_OF_CLUBS);
        hand.add(FOUR_OF_DIAMONDS);
        assertEquals(KICKER, evaluator.evaluate(hand));
        assertEquals(false, evaluator.isStraightFlush(hand));
        assertEquals(false, evaluator.isFourOfAKind(hand));
        assertEquals(false, evaluator.isFullHouse(hand));
        assertEquals(false, evaluator.isFlush(hand));
        assertEquals(false, evaluator.isStraight(hand));
        assertEquals(false, evaluator.isThreeOfAKind(hand));
        assertEquals(false, evaluator.isTwoPair(hand));
        assertEquals(false, evaluator.isPair(hand));
    }

    @Test
    public void test_flush() {
        hand.add(EIGHT_OF_HEARTS);
        hand.add(EIGHT_OF_DIAMONDS);

        hand.add(TWO_OF_CLUBS);
        hand.add(THREE_OF_CLUBS);
        hand.add(SIX_OF_CLUBS);
        hand.add(EIGHT_OF_CLUBS);
        hand.add(KNAVE_OF_CLUBS);
        assertEquals(FLUSH, evaluator.evaluate(hand));
        assertEquals(true, evaluator.isFlush(hand));
    }

    @Test
    public void test_fullHouse() {
        hand.add(EIGHT_OF_DIAMONDS);
        hand.add(NINE_OF_DIAMONDS);

        hand.add(TWO_OF_CLUBS);
        hand.add(TWO_OF_DIAMONDS);
        hand.add(TWO_OF_SPADES);
        hand.add(SIX_OF_CLUBS);
        hand.add(SIX_OF_DIAMONDS);
        assertEquals(FULL_HOUSE, evaluator.evaluate(hand));
        assertEquals(true, evaluator.isFullHouse(hand));
    }

    @Test
    public void test_fourOfAKind() {
        hand.add(EIGHT_OF_DIAMONDS);
        hand.add(NINE_OF_DIAMONDS);

        hand.add(TWO_OF_DIAMONDS);

        hand.add(SIX_OF_HEARTS);
        hand.add(SIX_OF_SPADES);
        hand.add(SIX_OF_CLUBS);
        hand.add(SIX_OF_DIAMONDS);
        assertEquals(FOUR_OF_A_KIND, evaluator.evaluate(hand));
        assertEquals(true, evaluator.isFourOfAKind(hand));
    }

    @Test
    public void test_straightFlush() {
        hand.add(EIGHT_OF_DIAMONDS);
        hand.add(NINE_OF_DIAMONDS);

        hand.add(ACE_OF_CLUBS);
        hand.add(KING_OF_CLUBS);
        hand.add(QUEEN_OF_CLUBS);
        hand.add(KNAVE_OF_CLUBS);
        hand.add(TEN_OF_CLUBS);
        assertEquals(STRAIGHT_FLUSH, evaluator.evaluate(hand));
        assertEquals(true, evaluator.isStraightFlush(hand));
    }

}
