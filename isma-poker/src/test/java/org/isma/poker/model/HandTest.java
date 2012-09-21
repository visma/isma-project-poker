package org.isma.poker.model;

import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.isma.poker.model.FiftyTwoCardsEnum.*;
import static org.isma.poker.model.SuitEnum.*;
import static org.isma.poker.model.ValueEnum.*;


public class HandTest {
    private final Hand hand = new Hand();

    protected void setUp() {
        hand.clear();
    }

    @Test
    public void test_countSuit() {
        hand.add(EIGHT_OF_CLUBS);
        hand.add(ACE_OF_CLUBS);

        hand.add(NINE_OF_DIAMONDS);

        hand.add(KING_OF_HEARTS);
        hand.add(QUEEN_OF_HEARTS);
        hand.add(KNAVE_OF_HEARTS);
        hand.add(TEN_OF_HEARTS);

        assertEquals(0, hand.count(SPADES));
        assertEquals(2, hand.count(CLUBS));
        assertEquals(1, hand.count(DIAMONDS));
        assertEquals(4, hand.count(HEARTS));
    }

    @Test
    public void test_countValue() {
        hand.add(EIGHT_OF_CLUBS);
        hand.add(EIGHT_OF_DIAMONDS);

        hand.add(NINE_OF_DIAMONDS);
        hand.add(NINE_OF_CLUBS);
        hand.add(NINE_OF_SPADES);

        hand.add(TEN_OF_CLUBS);

        assertEquals(2, hand.count(EIGHT));
        assertEquals(3, hand.count(NINE));
        assertEquals(1, hand.count(TEN));
        assertEquals(0, hand.count(ACE));
    }

    @Test
    public void test_getHandBySuit() {
        hand.add(EIGHT_OF_CLUBS);
        hand.add(EIGHT_OF_DIAMONDS);

        hand.add(NINE_OF_DIAMONDS);
        hand.add(NINE_OF_CLUBS);
        hand.add(NINE_OF_SPADES);

        hand.add(TEN_OF_CLUBS);

        Hand clubs = hand.getHand(SuitEnum.CLUBS);
        assertEquals("[8 of Clubs, 9 of Clubs, 10 of Clubs]", clubs.toString());

    }

    @Test
    public void test_getHandByValue() {
        hand.add(EIGHT_OF_CLUBS);
        hand.add(EIGHT_OF_DIAMONDS);

        hand.add(NINE_OF_DIAMONDS);
        hand.add(NINE_OF_CLUBS);
        hand.add(NINE_OF_SPADES);

        hand.add(TEN_OF_CLUBS);

        Hand eights = hand.getHand(EIGHT);
        assertEquals("[8 of Clubs, 8 of Diamonds]", eights.toString());

        Hand nines = hand.getHand(NINE);
        assertEquals("[9 of Diamonds, 9 of Clubs, 9 of Spades]", nines.toString());

        Hand tens = hand.getHand(TEN);
        assertEquals("[10 of Clubs]", tens.toString());
    }

    @Test
    public void test_getPairs() {
        hand.add(EIGHT_OF_CLUBS);
        hand.add(EIGHT_OF_DIAMONDS);

        hand.add(NINE_OF_DIAMONDS);
        hand.add(NINE_OF_CLUBS);

        hand.add(TEN_OF_CLUBS);

        List<Hand> pairs = hand.getPairs();
        assertEquals("[9 of Diamonds, 9 of Clubs]", pairs.get(0).toString());
        assertEquals("[8 of Clubs, 8 of Diamonds]", pairs.get(1).toString());
    }

    @Test
    public void test_getThreeOfAKinds() {
        hand.add(EIGHT_OF_CLUBS);
        hand.add(EIGHT_OF_DIAMONDS);
        hand.add(EIGHT_OF_SPADES);

        hand.add(NINE_OF_SPADES);
        hand.add(NINE_OF_DIAMONDS);
        hand.add(NINE_OF_CLUBS);

        hand.add(TEN_OF_CLUBS);

        List<Hand> threeOfAKinds = hand.getThreeOfAKinds();
        assertEquals("[9 of Spades, 9 of Diamonds, 9 of Clubs]", threeOfAKinds.get(0).toString());
        assertEquals("[8 of Clubs, 8 of Diamonds, 8 of Spades]", threeOfAKinds.get(1).toString());
    }

    @Test
    public void test_getFourOfAKind() {
        hand.add(EIGHT_OF_CLUBS);
        hand.add(EIGHT_OF_DIAMONDS);
        hand.add(EIGHT_OF_SPADES);
        hand.add(EIGHT_OF_HEARTS);

        hand.add(NINE_OF_DIAMONDS);
        hand.add(NINE_OF_CLUBS);

        hand.add(TEN_OF_CLUBS);

        List<Hand> fourOfAKind = hand.getFourOfAKind();
        assertEquals("[8 of Clubs, 8 of Diamonds, 8 of Spades, 8 of Hearts]", fourOfAKind.get(0).toString());
    }


}
