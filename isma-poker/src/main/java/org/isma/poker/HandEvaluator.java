package org.isma.poker;

import org.isma.poker.model.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.isma.poker.model.HandEvaluation.*;
import static org.isma.poker.model.ValueEnum.ACE;
import static org.isma.poker.model.ValueEnum.FIVE;

public class HandEvaluator {
    private final CardValueThenSuitOrderComparator COMPARATOR = new CardValueThenSuitOrderComparator();

    public HandEvaluator() {
        //handEvaluatorSorter = new HandEvaluatorSorter(this);
    }

    /**
     * *********************************************************************
     * EVALUATION METHODS
     * **********************************************************************
     */
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
        sortBest(hand, handEvaluation);
        return handEvaluation;
    }

    private boolean isPair(Hand hand) {
        return countPair(hand) == 1;
    }

    private boolean isTwoPair(Hand hand) {
        return countPair(hand) >= 2;
    }

    private boolean isThreeOfAKind(Hand hand) {
        for (ValueEnum valueEnum : ValueEnum.values()) {
            int count = hand.count(valueEnum);
            if (count == 3) {
                return true;
            }
        }
        return false;
    }

    private boolean isStraight(Hand hand) {
        return !StraightHelper.getAllStraights(hand).isEmpty();
    }

    private boolean isFlush(Hand hand) {
        for (SuitEnum suitEnum : SuitEnum.values()) {
            if (hand.count(suitEnum) >= 5) {
                return true;
            }
        }
        return false;
    }

    private boolean isFullHouse(Hand hand) {
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

    private boolean isFourOfAKind(Hand hand) {
        for (ValueEnum valueEnum : ValueEnum.values()) {
            if (hand.count(valueEnum) == 4) {
                return true;
            }
        }
        return false;
    }

    private boolean isStraightFlush(Hand hand) {
        return !getStraightFlushes(hand).isEmpty();
    }

    private Hand getMaxStraight(Hand hand) {
        return StraightHelper.getAllStraights(hand).get(0);
    }

    private List<Hand> getStraightFlushes(Hand hand) {
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

    /**
     * *********************************************************************
     * SORT METHODS
     * **********************************************************************
     */
    public void sortBest(Hand hand, HandEvaluation handEvaluation) {
        switch (handEvaluation) {
            case STRAIGHT_FLUSH:
                sortBestStraightFlush(hand);
                break;
            case FOUR_OF_A_KIND:
                sortBestFourOfAKind(hand);
                break;
            case FULL_HOUSE:
                sortBestFullHouse(hand);
                break;
            case FLUSH:
                sortBestFlush(hand);
                break;
            case STRAIGHT:
                sortBestStraight(hand);
                break;
            case THREE_OF_A_KIND:
                sortBestThreeOfAKind(hand);
                break;
            case TWO_PAIR:
                sortBestTwoPair(hand);
                break;
            case PAIR:
                sortBestPair(hand);
                break;
            case KICKER:
                sortKicker(hand);
                break;
        }
    }

    private void sortBestStraightFlush(Hand hand) {
        Hand maxSortedHand = getStraightFlushes(hand).get(0);
        int index = 0;
        for (Card sortedCard : maxSortedHand) {
            hand.remove(sortedCard);
            hand.add(index++, sortedCard);
        }
    }

    private void sortBestFourOfAKind(Hand hand) {
        Hand fourOfAKind = hand.getFourOfAKind().get(0);
        Hand kickers = new Hand(hand);
        kickers.removeAll(fourOfAKind);
        Collections.sort(fourOfAKind, COMPARATOR);
        Collections.sort(kickers, COMPARATOR);
        hand.clear();
        hand.addAll(fourOfAKind);
        hand.addAll(kickers);
    }

    private void sortBestFullHouse(Hand hand) {
        List<Hand> threeOfAKinds = hand.getThreeOfAKinds();
        if (threeOfAKinds.size() > 1) {
            Collections.sort(threeOfAKinds.get(0), COMPARATOR);
            Collections.sort(threeOfAKinds.get(1), COMPARATOR);
            Hand kickers = new Hand(hand);
            kickers.removeAll(threeOfAKinds.get(0));
            kickers.removeAll(threeOfAKinds.get(1));
            hand.clear();
            hand.addAll(threeOfAKinds.get(0));
            hand.addAll(threeOfAKinds.get(1));
            hand.addAll(kickers);
        } else {
            Hand threeOfAKind = threeOfAKinds.get(0);
            Hand pair = hand.getPairs().get(0);
            Hand kickers = new Hand(hand);
            kickers.removeAll(threeOfAKind);
            kickers.removeAll(pair);
            hand.clear();
            Collections.sort(threeOfAKind, COMPARATOR);
            Collections.sort(pair, COMPARATOR);
            Collections.sort(kickers, COMPARATOR);
            hand.addAll(threeOfAKind);
            hand.addAll(pair);
            hand.addAll(kickers);
        }
    }

    private void sortBestFlush(Hand hand) {
        Hand maxFlush = hand.getFlush();
        Hand kickers = new Hand(hand);
        kickers.removeAll(maxFlush);
        hand.clear();
        Collections.sort(maxFlush, COMPARATOR);
        Collections.sort(kickers, COMPARATOR);
        hand.addAll(maxFlush);
        hand.addAll(kickers);

    }

    private void sortBestStraight(Hand hand) {
        Hand maxStraight = getMaxStraight(hand);
        Hand kickers = new Hand(hand);
        kickers.removeAll(maxStraight);
        hand.clear();
        sortSpecialCaseLowestStraight(maxStraight);
        Collections.sort(kickers, COMPARATOR);
        hand.addAll(maxStraight);
        hand.addAll(kickers);
    }

    private void sortSpecialCaseLowestStraight(Hand maxStraight) {
        Collections.sort(maxStraight, COMPARATOR);
        if (maxStraight.get(0).getValue() == ACE && maxStraight.get(1).getValue() == FIVE) {
            Card ace = maxStraight.remove(0);
            maxStraight.add(ace);
        }
    }

    private void sortBestThreeOfAKind(Hand hand) {
        Hand threeOfAKind = hand.getThreeOfAKinds().get(0);
        Hand kickers = new Hand(hand);
        kickers.removeAll(threeOfAKind);
        Collections.sort(threeOfAKind, COMPARATOR);
        Collections.sort(kickers, COMPARATOR);
        hand.clear();
        hand.addAll(threeOfAKind);
        hand.addAll(kickers);
    }

    private void sortBestTwoPair(Hand hand) {
        Hand maxPair = hand.getPairs().get(0);
        Hand minPair = hand.getPairs().get(1);
        Hand kickers = new Hand(hand);
        kickers.removeAll(minPair);
        kickers.removeAll(maxPair);
        Collections.sort(maxPair, COMPARATOR);
        Collections.sort(minPair, COMPARATOR);
        Collections.sort(kickers, COMPARATOR);
        hand.clear();
        hand.addAll(maxPair);
        hand.addAll(minPair);
        hand.addAll(kickers);
    }

    private void sortBestPair(Hand hand) {
        Hand pair = hand.getPairs().get(0);
        Hand kickers = new Hand(hand);
        kickers.removeAll(pair);
        Collections.sort(pair, COMPARATOR);
        Collections.sort(kickers, COMPARATOR);
        hand.clear();
        hand.addAll(pair);
        hand.addAll(kickers);
    }

    private void sortKicker(Hand hand) {
        Collections.sort(hand, COMPARATOR);
    }

    private class CardValueThenSuitOrderComparator implements Comparator<Card> {
        @Override
        public int compare(Card card1, Card card2) {
            if (card2.getValue().getValue() != card1.getValue().getValue()) {
                return card2.getValue().getValue() - card1.getValue().getValue();
            }
            return card1.getSuit().ordinal() - card2.getSuit().ordinal();
        }
    }
}
