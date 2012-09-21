package org.isma.poker;

import org.isma.poker.model.Hand;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.*;
import static org.isma.poker.model.FiftyTwoCardsEnum.*;

public class HandCombinationTest {
    private final HandCombination handCombination = new HandCombination();
    private final Hand hand = new Hand();

    @Before
    public void setUp() throws Exception {
        hand.clear();
    }

    @Test
    public void test_isTheLowerStraight(){
        hand.add(TWO_OF_CLUBS);
        hand.add(THREE_OF_CLUBS);
        hand.add(FOUR_OF_DIAMONDS);
        hand.add(FIVE_OF_HEARTS);
        hand.add(ACE_OF_CLUBS);
        assertTrue(handCombination.isStraight(hand));
    }

    @Test
    public void test_isNotTheLowerStraight(){
        hand.add(TWO_OF_CLUBS);
        hand.add(THREE_OF_CLUBS);
        hand.add(FOUR_OF_DIAMONDS);
        hand.add(SIX_OF_HEARTS);
        hand.add(ACE_OF_CLUBS);
        assertFalse(handCombination.isStraight(hand));
    }

    @Test
    public void test_isStraight_HighCard_Ace() {
        hand.add(TEN_OF_CLUBS);
        hand.add(KNAVE_OF_CLUBS);
        hand.add(QUEEN_OF_CLUBS);
        hand.add(KING_OF_CLUBS);
        hand.add(ACE_OF_CLUBS);
        assertTrue(handCombination.isStraight(hand));
    }
    @Test
    public void test_isStraight_HighCard_King() {
        hand.add(NINE_OF_CLUBS);
        hand.add(TEN_OF_CLUBS);
        hand.add(KNAVE_OF_CLUBS);
        hand.add(QUEEN_OF_CLUBS);
        hand.add(KING_OF_CLUBS);
        assertTrue(handCombination.isStraight(hand));
    }
    @Test
    public void test_isStraight_HighCard_Five() {
        hand.add(TWO_OF_CLUBS);
        hand.add(THREE_OF_CLUBS);
        hand.add(FOUR_OF_CLUBS);
        hand.add(FIVE_OF_CLUBS);
        hand.add(ACE_OF_CLUBS);
        assertTrue(handCombination.isStraight(hand));
    }
    @Test
    public void test_isNotAStraight_MissingOneCard() {
        hand.add(KING_OF_CLUBS);
        hand.add(QUEEN_OF_CLUBS);
        hand.add(KNAVE_OF_CLUBS);
        hand.add(TEN_OF_CLUBS);
        hand.add(EIGHT_OF_CLUBS);
        assertFalse(handCombination.isStraight(hand));
    }
    @Test
    public void test_isNotAStraight_AceNotACenterCard() {
        hand.add(KING_OF_CLUBS);
        hand.add(ACE_OF_CLUBS);
        hand.add(TWO_OF_CLUBS);
        hand.add(THREE_OF_CLUBS);
        hand.add(FOUR_OF_CLUBS);
        assertFalse(handCombination.isStraight(hand));
    }


    @Test
    public void test_possibleStraights_normalCase() {
        hand.add(ACE_OF_CLUBS);
        hand.add(KING_OF_CLUBS);
        hand.add(QUEEN_OF_CLUBS);
        hand.add(KNAVE_OF_CLUBS);
        hand.add(TEN_OF_CLUBS);
        hand.add(NINE_OF_CLUBS);
        hand.add(EIGHT_OF_CLUBS);

        List<Hand> straightHands = handCombination.getAllStraights(hand);
        assertEquals(3, straightHands.size());
        assertEquals("[Ace of Clubs, King of Clubs, Queen of Clubs, Knave of Clubs, 10 of Clubs]", straightHands.get(0).toString());
        assertEquals("[King of Clubs, Queen of Clubs, Knave of Clubs, 10 of Clubs, 9 of Clubs]", straightHands.get(1).toString());
        assertEquals("[Queen of Clubs, Knave of Clubs, 10 of Clubs, 9 of Clubs, 8 of Clubs]", straightHands.get(2).toString());
    }

    @Test
    public void test_possibleStraights_pairInsideStraight() {
        hand.add(ACE_OF_CLUBS);
        hand.add(KING_OF_CLUBS);
        hand.add(QUEEN_OF_CLUBS);
        hand.add(KNAVE_OF_CLUBS);
        hand.add(TEN_OF_CLUBS);
        hand.add(TEN_OF_DIAMONDS);
        hand.add(NINE_OF_CLUBS);

        List<Hand> straightHands = handCombination.getAllStraights(hand);
        assertEquals(4, straightHands.size());
        assertEquals("[Ace of Clubs, King of Clubs, Queen of Clubs, Knave of Clubs, 10 of Diamonds]", straightHands.get(0).toString());
        assertEquals("[Ace of Clubs, King of Clubs, Queen of Clubs, Knave of Clubs, 10 of Clubs]", straightHands.get(1).toString());

        assertEquals("[King of Clubs, Queen of Clubs, Knave of Clubs, 10 of Diamonds, 9 of Clubs]", straightHands.get(2).toString());
        assertEquals("[King of Clubs, Queen of Clubs, Knave of Clubs, 10 of Clubs, 9 of Clubs]", straightHands.get(3).toString());

    }
}
