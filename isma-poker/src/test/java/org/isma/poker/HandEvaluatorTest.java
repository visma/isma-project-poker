package org.isma.poker;

import org.isma.poker.model.Hand;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static org.isma.poker.model.FiftyTwoCardsEnum.*;
import static org.isma.poker.model.HandEvaluation.*;


public class HandEvaluatorTest {
    private final Hand hand = new Hand();
    private final HandEvaluator evaluator = new HandEvaluator();

    @Before
    public void setUp() throws Exception {
        hand.clear();
    }

    @Test
    public void bug_straight() {
        hand.add(ACE_OF_HEARTS);
        hand.add(ACE_OF_CLUBS);
        hand.add(KING_OF_DIAMONDS);
        hand.add(QUEEN_OF_SPADES);
        hand.add(KNAVE_OF_SPADES);
        hand.add(KNAVE_OF_DIAMONDS);
        hand.add(TWO_OF_CLUBS);

        assertEquals(TWO_PAIR, evaluator.evaluate(hand));
    }
    @Test
    public void kicker() {
        hand.add(ACE_OF_CLUBS);
        hand.add(KING_OF_CLUBS);
        hand.add(QUEEN_OF_CLUBS);
        hand.add(KNAVE_OF_CLUBS);
        hand.add(NINE_OF_DIAMONDS);
        hand.add(SIX_OF_HEARTS);
        hand.add(TWO_OF_HEARTS);

        assertEquals(KICKER, evaluator.evaluate(hand));
    }

    @Test
    public void pair() {
        hand.add(ACE_OF_CLUBS);
        hand.add(KING_OF_CLUBS);
        hand.add(QUEEN_OF_CLUBS);
        hand.add(SIX_OF_HEARTS);
        hand.add(FIVE_OF_HEARTS);

        hand.add(NINE_OF_SPADES);
        hand.add(NINE_OF_DIAMONDS);

        assertEquals(PAIR, evaluator.evaluate(hand));
    }



    @Test
    public void twoPair() {
        hand.add(ACE_OF_CLUBS);
        hand.add(SIX_OF_HEARTS);
        hand.add(FIVE_OF_HEARTS);

        hand.add(KING_OF_CLUBS);
        hand.add(KING_OF_DIAMONDS);
        hand.add(NINE_OF_SPADES);
        hand.add(NINE_OF_DIAMONDS);
        assertEquals(TWO_PAIR, evaluator.evaluate(hand));
    }

    @Test
    public void threeOfAKind() {
        hand.add(ACE_OF_CLUBS);
        hand.add(KING_OF_CLUBS);
        hand.add(SIX_OF_HEARTS);
        hand.add(FIVE_OF_HEARTS);

        hand.add(NINE_OF_HEARTS);
        hand.add(NINE_OF_SPADES);
        hand.add(NINE_OF_DIAMONDS);

        assertEquals(THREE_OF_A_KIND, evaluator.evaluate(hand));
    }

    @Test
    public void lowStraight() {
        hand.add(ACE_OF_DIAMONDS);
        hand.add(ACE_OF_SPADES);

        hand.add(ACE_OF_CLUBS);
        hand.add(TWO_OF_CLUBS);
        hand.add(THREE_OF_CLUBS);
        hand.add(FOUR_OF_DIAMONDS);
        hand.add(FIVE_OF_DIAMONDS);

        assertEquals(STRAIGHT, evaluator.evaluate(hand));
    }

    @Test
    public void highStraight() {
        hand.add(ACE_OF_DIAMONDS);
        hand.add(ACE_OF_SPADES);

        hand.add(ACE_OF_CLUBS);
        hand.add(KING_OF_CLUBS);
        hand.add(QUEEN_OF_DIAMONDS);
        hand.add(KNAVE_OF_DIAMONDS);
        hand.add(TEN_OF_CLUBS);
        assertEquals(STRAIGHT, evaluator.evaluate(hand));
    }

    @Test
    public void notAStraight() {
        hand.add(EIGHT_OF_HEARTS);
        hand.add(NINE_OF_HEARTS);

        hand.add(KING_OF_DIAMONDS);
        hand.add(ACE_OF_CLUBS);
        hand.add(TWO_OF_CLUBS);
        hand.add(THREE_OF_CLUBS);
        hand.add(FOUR_OF_DIAMONDS);
        
        assertEquals(KICKER, evaluator.evaluate(hand));
    }

    @Test
    public void flush() {
        hand.add(EIGHT_OF_HEARTS);
        hand.add(EIGHT_OF_DIAMONDS);

        hand.add(TWO_OF_CLUBS);
        hand.add(THREE_OF_CLUBS);
        hand.add(SIX_OF_CLUBS);
        hand.add(EIGHT_OF_CLUBS);
        hand.add(KNAVE_OF_CLUBS);
        
        assertEquals(FLUSH, evaluator.evaluate(hand));
    }

    @Test
    public void fullHouse() {
        hand.add(EIGHT_OF_DIAMONDS);
        hand.add(NINE_OF_DIAMONDS);

        hand.add(TWO_OF_CLUBS);
        hand.add(TWO_OF_DIAMONDS);
        hand.add(TWO_OF_SPADES);
        hand.add(SIX_OF_CLUBS);
        hand.add(SIX_OF_DIAMONDS);
        
        assertEquals(FULL_HOUSE, evaluator.evaluate(hand));
    }

    @Test
    public void fourOfAKind() {
        hand.add(EIGHT_OF_DIAMONDS);
        hand.add(NINE_OF_DIAMONDS);

        hand.add(TWO_OF_DIAMONDS);

        hand.add(SIX_OF_HEARTS);
        hand.add(SIX_OF_SPADES);
        hand.add(SIX_OF_CLUBS);
        hand.add(SIX_OF_DIAMONDS);

        assertEquals(FOUR_OF_A_KIND, evaluator.evaluate(hand));
    }

    @Test
    public void straightFlush() {
        hand.add(EIGHT_OF_DIAMONDS);
        hand.add(NINE_OF_DIAMONDS);

        hand.add(ACE_OF_CLUBS);
        hand.add(KING_OF_CLUBS);
        hand.add(QUEEN_OF_CLUBS);
        hand.add(KNAVE_OF_CLUBS);
        hand.add(TEN_OF_CLUBS);

        assertEquals(STRAIGHT_FLUSH, evaluator.evaluate(hand));
    }

}
