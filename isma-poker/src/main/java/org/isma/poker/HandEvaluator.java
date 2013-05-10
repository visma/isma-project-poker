package org.isma.poker;

import org.isma.poker.model.*;

import java.util.ArrayList;
import java.util.List;

import static org.isma.poker.model.HandEvaluation.*;

public class HandEvaluator {
    private final HandEvaluatorSorter handEvaluatorSorter;

    public HandEvaluator() {
        handEvaluatorSorter = new HandEvaluatorSorter(this);
    }

    public HandEvaluation evaluate(Hand hand) {
        HandEvaluation handEvaluation;
        if (isStraightFlush(hand)) {
            handEvaluation = STRAIGHT_FLUSH;
        } else if (isFourOfAKind(hand)) {
            handEvaluation = FOUR_OF_A_KIND;
        } else if (isFullHouse(hand)) {
            handEvaluation = FULL_HOUSE;
        } else if (isFlush(hand)) {
            handEvaluation = FLUSH;
        } else if (isStraight(hand)) {
            handEvaluation = STRAIGHT;
        } else if (isThreeOfAKind(hand)) {
            handEvaluation = THREE_OF_A_KIND;
        } else if (isTwoPair(hand)) {
            handEvaluation = TWO_PAIR;
        } else if (isPair(hand)) {
            handEvaluation = PAIR;
        } else {
            handEvaluation = KICKER;
        }
        handEvaluatorSorter.sortBest(hand, handEvaluation);
        return handEvaluation;
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
        return !StraightHelper.getAllStraights(hand).isEmpty();
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
        return StraightHelper.getAllStraights(hand).get(0);
    }

    List<Hand> getStraightFlushes(Hand hand) {
        List<Hand> possibleStraights = StraightHelper.getAllStraights(hand);
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
