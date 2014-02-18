package org.isma.poker;

import org.isma.poker.model.FiftyTwoCardsEnum;
import org.isma.poker.model.Hand;
import org.isma.poker.model.HandEvaluation;
import org.junit.Before;
import org.junit.Test;

import java.util.Comparator;

import static org.isma.poker.model.FiftyTwoCardsEnum.*;
import static org.isma.poker.model.HandEvaluation.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HandComparatorTest {
    private final HandEvaluator evaluator = new HandEvaluator();
    private final Comparator<Hand> comparator = evaluator.getHandComparator();
    private final Hand lowHand = new Hand();
    private final Hand highHand = new Hand();
    private static final boolean EQUALITY = true;

    @Before
    public void setUp() throws Exception {
        setUpWithoutPair();
    }

    private void setUpWithoutPair() {
        lowHand.clear();
        highHand.clear();
        addCommunityCard(KING_OF_SPADES);
        addCommunityCard(KNAVE_OF_DIAMONDS);
        addCommunityCard(TEN_OF_HEARTS);
        addCommunityCard(FIVE_OF_HEARTS);
        addCommunityCard(TWO_OF_HEARTS);
    }

    private void setUpWithPair() {
        lowHand.clear();
        highHand.clear();
        addCommunityCard(KING_OF_SPADES);
        addCommunityCard(KNAVE_OF_DIAMONDS);
        addCommunityCard(KNAVE_OF_HEARTS);
        addCommunityCard(FIVE_OF_HEARTS);
        addCommunityCard(TWO_OF_HEARTS);
    }

    private void setUpWithTwoPair() {
        lowHand.clear();
        highHand.clear();
        addCommunityCard(KING_OF_HEARTS);
        addCommunityCard(KNAVE_OF_DIAMONDS);
        addCommunityCard(KNAVE_OF_HEARTS);
        addCommunityCard(TWO_OF_DIAMONDS);
        addCommunityCard(TWO_OF_HEARTS);
    }

    private void addCommunityCard(FiftyTwoCardsEnum cardEnum) {
        lowHand.add(cardEnum);
        highHand.add(cardEnum);
    }

    private void addPocketCards(Hand hand, FiftyTwoCardsEnum cardEnum1, FiftyTwoCardsEnum cardEnum2) {
        hand.add(cardEnum1);
        hand.add(cardEnum2);
    }

    private void assertEvaluation(HandEvaluation expectedHighHandEvaluation,
                                  HandEvaluation expectedLowHandEvaluation, boolean equality) {
        HandEvaluation highHandEval = evaluator.evaluate(highHand);
        HandEvaluation lowHandEval = evaluator.evaluate(lowHand);
        assertEquals(expectedHighHandEvaluation, highHandEval);
        assertEquals(expectedLowHandEvaluation, lowHandEval);
        evaluator.sortBest(highHand, highHandEval);
        evaluator.sortBest(lowHand, lowHandEval);
        if (equality) {
            assertTrue(comparator.compare(highHand, lowHand) == 0);
        } else {
            assertTrue(comparator.compare(highHand, lowHand) > 0);
        }
    }

    // KICKERS
    @Test
    public void kickerEqualsVsKicker() {
        addPocketCards(lowHand, QUEEN_OF_CLUBS, SEVEN_OF_DIAMONDS);
        addPocketCards(highHand, QUEEN_OF_DIAMONDS, SEVEN_OF_CLUBS);
        assertEvaluation(KICKER, KICKER, EQUALITY);
    }


    @Test
    public void kickerWinVsKicker() {
        addPocketCards(lowHand, QUEEN_OF_CLUBS, SIX_OF_DIAMONDS);
        addPocketCards(highHand, QUEEN_OF_DIAMONDS, SEVEN_OF_CLUBS);
        assertEvaluation(KICKER, KICKER, !EQUALITY);
    }

    // PAIR
    @Test
    public void pairWinVsKicker() {
        addPocketCards(lowHand, ACE_OF_CLUBS, SIX_OF_DIAMONDS);
        addPocketCards(highHand, TWO_OF_CLUBS, SEVEN_OF_CLUBS);
        assertEvaluation(PAIR, KICKER, !EQUALITY);
    }

    @Test
    public void pairEqualsVsPair() {
        addPocketCards(lowHand, KING_OF_CLUBS, THREE_OF_CLUBS);
        addPocketCards(highHand, KING_OF_DIAMONDS, THREE_OF_SPADES);
        assertEvaluation(PAIR, PAIR, EQUALITY);
    }

    @Test
    public void pairWinVsPair() {
        addPocketCards(lowHand, KNAVE_OF_CLUBS, THREE_OF_CLUBS);
        addPocketCards(highHand, KING_OF_DIAMONDS, THREE_OF_SPADES);
        assertEvaluation(PAIR, PAIR, !EQUALITY);
    }

    // TWO PAIR
    @Test
    public void twoPairWinVsPair() {
        addPocketCards(lowHand, THREE_OF_SPADES, KING_OF_CLUBS);
        addPocketCards(highHand, FIVE_OF_CLUBS, TWO_OF_CLUBS);
        assertEvaluation(TWO_PAIR, PAIR, !EQUALITY);
    }

    @Test
    public void twoPairWinVsTwoPair() {
        addPocketCards(lowHand, TEN_OF_DIAMONDS, KNAVE_OF_SPADES);
        addPocketCards(highHand, TWO_OF_DIAMONDS, KING_OF_DIAMONDS);
        assertEvaluation(TWO_PAIR, TWO_PAIR, !EQUALITY);
    }


    // THREE OF A KIND
    @Test
    public void threeOfAKindWinVsTwoPair() {
        addPocketCards(lowHand, FIVE_OF_CLUBS, TWO_OF_CLUBS);
        addPocketCards(highHand, TWO_OF_DIAMONDS, TWO_OF_SPADES);
        assertEvaluation(THREE_OF_A_KIND, TWO_PAIR, !EQUALITY);
    }

    @Test
    public void threeOfAKindWinVsThreeOfAKind() {
        addPocketCards(lowHand, TWO_OF_DIAMONDS, TWO_OF_CLUBS);
        addPocketCards(highHand, FIVE_OF_DIAMONDS, FIVE_OF_CLUBS);
        assertEvaluation(THREE_OF_A_KIND, THREE_OF_A_KIND, !EQUALITY);
    }

    // STRAIGHT
    @Test
    public void straightWinVsThreeOfAKind() {
        addPocketCards(lowHand, FIVE_OF_DIAMONDS, FIVE_OF_CLUBS);
        addPocketCards(highHand, ACE_OF_CLUBS, QUEEN_OF_CLUBS);
        assertEvaluation(STRAIGHT, THREE_OF_A_KIND, !EQUALITY);
    }

    @Test
    public void straightEqualsVsStraight() {
        addPocketCards(lowHand, ACE_OF_DIAMONDS, QUEEN_OF_DIAMONDS);
        addPocketCards(highHand, ACE_OF_CLUBS, QUEEN_OF_CLUBS);
        assertEvaluation(STRAIGHT, STRAIGHT, EQUALITY);
    }

    @Test
    public void straightWinVsStraight() {
        addPocketCards(lowHand, NINE_OF_DIAMONDS, QUEEN_OF_CLUBS);
        addPocketCards(highHand, ACE_OF_DIAMONDS, QUEEN_OF_DIAMONDS);
        assertEvaluation(STRAIGHT, STRAIGHT, !EQUALITY);
    }

    // FLUSH
    @Test
    public void flushWinVsStraight() {
        addPocketCards(lowHand, ACE_OF_DIAMONDS, QUEEN_OF_DIAMONDS);
        addPocketCards(highHand, FOUR_OF_HEARTS, SEVEN_OF_HEARTS);
        assertEvaluation(FLUSH, STRAIGHT, !EQUALITY);
    }

    @Test
    public void flushWinVsFlush() {
        addPocketCards(lowHand, FOUR_OF_HEARTS, SEVEN_OF_HEARTS);
        addPocketCards(highHand, THREE_OF_HEARTS, EIGHT_OF_HEARTS);
        assertEvaluation(FLUSH, FLUSH, !EQUALITY);
    }

    // FULL HOUSE
    @Test
    public void fullHouseWinVsFlush() {
        setUpWithPair();
        addPocketCards(lowHand, THREE_OF_HEARTS, EIGHT_OF_HEARTS);
        addPocketCards(highHand, KNAVE_OF_SPADES, TWO_OF_SPADES);
        assertEvaluation(FULL_HOUSE, FLUSH, !EQUALITY);
    }


    @Test
    public void fullHouseEqualsVsFullHouse() {
        setUpWithPair();
        addPocketCards(lowHand, KNAVE_OF_SPADES, TWO_OF_SPADES);
        addPocketCards(highHand, KNAVE_OF_CLUBS, TWO_OF_DIAMONDS);
        assertEvaluation(FULL_HOUSE, FULL_HOUSE, EQUALITY);
    }

    @Test
    public void fullHouseWinVsFullHouse() {
        setUpWithPair();
        addPocketCards(lowHand, KNAVE_OF_SPADES, TWO_OF_SPADES);
        addPocketCards(highHand, KNAVE_OF_CLUBS, FIVE_OF_DIAMONDS);
        assertEvaluation(FULL_HOUSE, FULL_HOUSE, !EQUALITY);
    }

    // FOUR OF A KIND
    @Test
    public void fourOfAKindWinVsFullHouse() {
        setUpWithTwoPair();
        addPocketCards(lowHand, KNAVE_OF_CLUBS, KING_OF_SPADES);
        addPocketCards(highHand, TWO_OF_CLUBS, TWO_OF_SPADES);
        assertEvaluation(FOUR_OF_A_KIND, FULL_HOUSE, !EQUALITY);
    }

    @Test
    public void fourOfAKindWinVsFourOfAKind() {
        setUpWithTwoPair();
        addPocketCards(lowHand, TWO_OF_CLUBS, TWO_OF_SPADES);
        addPocketCards(highHand, KNAVE_OF_CLUBS, KNAVE_OF_SPADES);
        assertEvaluation(FOUR_OF_A_KIND, FOUR_OF_A_KIND, !EQUALITY);
    }

    // STRAIGHT FLUSH
    @Test
    public void straightFlushWinVsFourOfAKind() {
        lowHand.clear();
        highHand.clear();
        addCommunityCard(KING_OF_HEARTS);
        addCommunityCard(QUEEN_OF_HEARTS);
        addCommunityCard(KNAVE_OF_HEARTS);
        addCommunityCard(KNAVE_OF_DIAMONDS);
        addCommunityCard(TWO_OF_DIAMONDS);

        addPocketCards(lowHand, KNAVE_OF_CLUBS, KNAVE_OF_SPADES);
        addPocketCards(highHand, ACE_OF_HEARTS, TEN_OF_HEARTS);
        assertEvaluation(STRAIGHT_FLUSH, FOUR_OF_A_KIND, !EQUALITY);
    }

    @Test
    public void straightFlushWinVsStraightFlush() {
        lowHand.clear();
        highHand.clear();
        addCommunityCard(KING_OF_HEARTS);
        addCommunityCard(QUEEN_OF_HEARTS);
        addCommunityCard(KNAVE_OF_HEARTS);
        addCommunityCard(TEN_OF_HEARTS);
        addCommunityCard(TWO_OF_DIAMONDS);

        addPocketCards(lowHand, NINE_OF_HEARTS, FOUR_OF_CLUBS);
        addPocketCards(highHand, ACE_OF_HEARTS, THREE_OF_CLUBS);
        assertEvaluation(STRAIGHT_FLUSH, STRAIGHT_FLUSH, !EQUALITY);
    }
}
