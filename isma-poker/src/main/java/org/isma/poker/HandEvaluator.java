package org.isma.poker;

import org.isma.poker.model.Card;
import org.isma.poker.model.Hand;
import org.isma.poker.model.SuitEnum;
import org.isma.poker.model.ValueEnum;

import java.util.ArrayList;
import java.util.List;

import static org.isma.poker.HandEvaluationEnum.*;

public class HandEvaluator {
    private final HandEvaluatorSorter handEvaluatorSorter;
    private final HandCombination handCombination = new HandCombination();

    public HandEvaluator() {
        handEvaluatorSorter = new HandEvaluatorSorter(this);
    }

    public HandEvaluationEnum evaluate(Hand hand) {
        if (isStraightFlush(hand)) {
            handEvaluatorSorter.sortBestStraightFlush(hand);
            return STRAIGHT_FLUSH;
        }
        if (isFourOfAKind(hand)) {
            handEvaluatorSorter.sortBestFourOfAKind(hand);
            return FOUR_OF_A_KIND;
        }
        if (isFullHouse(hand)) {
            handEvaluatorSorter.sortBestFullHouse(hand);
            return FULL_HOUSE;
        }
        if (isFlush(hand)) {
            handEvaluatorSorter.sortBestFlush(hand);
            return FLUSH;
        }
        if (isStraight(hand)) {
            handEvaluatorSorter.sortBestStraight(hand);
            return STRAIGHT;
        }
        if (isThreeOfAKind(hand)) {
            handEvaluatorSorter.sortBestThreeOfAKind(hand);
            return THREE_OF_A_KIND;
        }
        if (isTwoPair(hand)) {
            handEvaluatorSorter.sortBestTwoPair(hand);
            return TWO_PAIR;
        }
        if (isPair(hand)) {
            handEvaluatorSorter.sortBestPair(hand);
            return PAIR;
        }
        handEvaluatorSorter.sortKicker(hand);
        return KICKER;
    }

    public boolean isPair(Hand hand) {
        return countPair(hand) == 1;
    }

    public boolean isTwoPair(Hand hand) {
        return countPair(hand) >= 2;
    }

    private int countPair(Hand hand) {
        int pairCount = 0;
        for (ValueEnum valueEnum : ValueEnum.values()) {
            int count = hand.count(valueEnum);
            if (count == 2) {
                pairCount++;
            }
        }
        return pairCount;
    }

    public boolean isThreeOfAKind(Hand hand) {
        for (ValueEnum valueEnum : ValueEnum.values()) {
            int count = hand.count(valueEnum);
            if (count == 3) {
                return true;
            }
        }
        return false;
    }

    public boolean isStraight(Hand hand) {
        return !handCombination.getAllStraights(hand).isEmpty();
    }

    public boolean isFlush(Hand hand) {
        for (SuitEnum suitEnum : SuitEnum.values()) {
            if (hand.count(suitEnum) >= 5) {
                return true;
            }
        }
        return false;
    }

    public boolean isFullHouse(Hand hand) {
        boolean hasPair = false;
        boolean hasThreeOfAKind = false;
        for (ValueEnum valueEnum : ValueEnum.values()) {
            int count = hand.count(valueEnum);
            if (count == 3) {
                hasThreeOfAKind = true;
            } else if (count == 2) {
                hasPair = true;
            }
            if (hasPair && hasThreeOfAKind) {
                return true;
            }
        }
        return false;
    }

    public boolean isFourOfAKind(Hand hand) {
        for (ValueEnum valueEnum : ValueEnum.values()) {
            if (hand.count(valueEnum) == 4) {
                return true;
            }
        }
        return false;
    }

    public boolean isStraightFlush(Hand hand) {
        return !getStraightFlushes(hand).isEmpty();
    }

    Hand getMaxStraight(Hand hand) {
        return handCombination.getAllStraights(hand).get(0);
    }

    List<Hand> getStraightFlushes(Hand hand) {
        List<Hand> possibleStraights = handCombination.getAllStraights(hand);
        List<Hand> straightFlushes = new ArrayList<Hand>();
        for (Hand possibleStraight : possibleStraights) {
            Card firstCard = possibleStraight.get(0);
            if (possibleStraight.count(firstCard.getSuit()) == 5) {
                straightFlushes.add(possibleStraight);
            }
        }
        return straightFlushes;
    }


}
