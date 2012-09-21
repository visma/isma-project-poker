package org.isma.poker;

import org.isma.poker.model.Hand;
import org.junit.Test;

import static org.isma.poker.model.FiftyTwoCardsEnum.*;
import static org.junit.Assert.assertEquals;

public class HandEvaluatorSorterTest {
    private final HandEvaluatorSorter sorter = new HandEvaluatorSorter(new HandEvaluator());

    @Test
    public void test_sort_straightFlush() {
        Hand hand = new Hand();
        hand.add(TEN_OF_CLUBS);
        hand.add(KNAVE_OF_CLUBS);
        hand.add(QUEEN_OF_CLUBS);
        hand.add(KING_OF_CLUBS);
        hand.add(ACE_OF_CLUBS);
        sorter.sortBestStraightFlush(hand);
        assertEquals("[Ace of Clubs, King of Clubs, Queen of Clubs, Knave of Clubs, 10 of Clubs]", hand.toString());
    }

    @Test
    public void test_sort_fourOfAKind() {
        Hand hand = new Hand();
        hand.add(ACE_OF_CLUBS);
        hand.add(TEN_OF_CLUBS);
        hand.add(TEN_OF_DIAMONDS);
        hand.add(TEN_OF_HEARTS);
        hand.add(TEN_OF_SPADES);
        sorter.sortBestFourOfAKind(hand);
        assertEquals("[10 of Spades, 10 of Hearts, 10 of Diamonds, 10 of Clubs, Ace of Clubs]", hand.toString());
    }

    @Test
    public void test_sort_fullHouse() {
        Hand hand = new Hand();
        hand.add(ACE_OF_CLUBS);
        hand.add(ACE_OF_DIAMONDS);
        hand.add(TEN_OF_SPADES);
        hand.add(TEN_OF_DIAMONDS);
        hand.add(TEN_OF_HEARTS);
        sorter.sortBestFullHouse(hand);
        assertEquals("[10 of Spades, 10 of Hearts, 10 of Diamonds, Ace of Diamonds, Ace of Clubs]", hand.toString());
    }

    @Test
    public void test_sort_flush() {
        Hand hand = new Hand();
        hand.add(FIVE_OF_CLUBS);
        hand.add(TEN_OF_CLUBS);
        hand.add(KNAVE_OF_CLUBS);
        hand.add(QUEEN_OF_CLUBS);
        hand.add(ACE_OF_CLUBS);
        sorter.sortBestFlush(hand);
        assertEquals("[Ace of Clubs, Queen of Clubs, Knave of Clubs, 10 of Clubs, 5 of Clubs]", hand.toString());
    }

    @Test
    public void test_sort_straight() {
        Hand hand = new Hand();
        hand.add(ACE_OF_HEARTS);
        hand.add(FOUR_OF_DIAMONDS);
        hand.add(FIVE_OF_HEARTS);
        hand.add(TWO_OF_DIAMONDS);
        hand.add(THREE_OF_SPADES);
        sorter.sortBestStraight(hand);
        assertEquals("[5 of Hearts, 4 of Diamonds, 3 of Spades, 2 of Diamonds, Ace of Hearts]", hand.toString());
    }

    @Test
    public void test_sort_threeOfAKind() {
        Hand hand = new Hand();
        hand.add(ACE_OF_HEARTS);
        hand.add(FOUR_OF_DIAMONDS);
        hand.add(ACE_OF_CLUBS);
        hand.add(TWO_OF_DIAMONDS);
        hand.add(ACE_OF_SPADES);
        sorter.sortBestThreeOfAKind(hand);
        assertEquals("[Ace of Spades, Ace of Hearts, Ace of Clubs, 4 of Diamonds, 2 of Diamonds]", hand.toString());
    }

    @Test
    public void test_sort_twoPair() {
        Hand hand = new Hand();
        hand.add(ACE_OF_HEARTS);
        hand.add(ACE_OF_DIAMONDS);
        hand.add(THREE_OF_CLUBS);
        hand.add(TWO_OF_HEARTS);
        hand.add(TWO_OF_SPADES);
        sorter.sortBestTwoPair(hand);
        assertEquals("[Ace of Hearts, Ace of Diamonds, 2 of Spades, 2 of Hearts, 3 of Clubs]", hand.toString());
    }

    @Test
    public void test_sort_pair() {
        Hand hand = new Hand();
        hand.add(ACE_OF_HEARTS);
        hand.add(KING_OF_CLUBS);
        hand.add(THREE_OF_CLUBS);
        hand.add(TWO_OF_HEARTS);
        hand.add(TWO_OF_SPADES);
        sorter.sortBestPair(hand);
        assertEquals("[2 of Spades, 2 of Hearts, Ace of Hearts, King of Clubs, 3 of Clubs]", hand.toString());
    }

    @Test
    public void test_sort_kicker() {
        Hand hand = new Hand();
        hand.add(TWO_OF_CLUBS);
        hand.add(THREE_OF_CLUBS);
        hand.add(ACE_OF_DIAMONDS);
        hand.add(FOUR_OF_CLUBS);
        hand.add(FIVE_OF_CLUBS);
        sorter.sortKicker(hand);
        assertEquals("[Ace of Diamonds, 5 of Clubs, 4 of Clubs, 3 of Clubs, 2 of Clubs]", hand.toString());
    }
}
