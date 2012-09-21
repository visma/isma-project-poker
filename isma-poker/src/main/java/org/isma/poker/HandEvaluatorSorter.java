package org.isma.poker;

import org.isma.poker.comparators.CardValueThenSuitOrderComparator;
import org.isma.poker.model.Card;
import org.isma.poker.model.Hand;

import java.util.Collections;
import java.util.List;

import static org.isma.poker.model.ValueEnum.ACE;
import static org.isma.poker.model.ValueEnum.FIVE;

public class HandEvaluatorSorter {
    public static final CardValueThenSuitOrderComparator COMPARATOR = new CardValueThenSuitOrderComparator();
    private HandEvaluator handEvaluator;

    public HandEvaluatorSorter(HandEvaluator handEvaluator) {
        this.handEvaluator = handEvaluator;
    }

    public void sortBestStraightFlush(Hand hand) {
        Hand maxSortedHand = handEvaluator.getStraightFlushes(hand).get(0);
        int index = 0;
        for (Card sortedCard : maxSortedHand) {
            hand.remove(sortedCard);
            hand.add(index++, sortedCard);
        }
    }

    public void sortBestFourOfAKind(Hand hand) {
        Hand fourOfAKind = hand.getFourOfAKind().get(0);
        Hand kickers = new Hand(hand);
        kickers.removeAll(fourOfAKind);
        Collections.sort(fourOfAKind, COMPARATOR);
        Collections.sort(kickers, COMPARATOR);
        hand.clear();
        hand.addAll(fourOfAKind);
        hand.addAll(kickers);
    }

    public void sortBestFullHouse(Hand hand) {
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

    public void sortBestFlush(Hand hand) {
        Hand maxFlush = hand.getFlush();
        Hand kickers = new Hand(hand);
        kickers.removeAll(maxFlush);
        hand.clear();
        Collections.sort(maxFlush, COMPARATOR);
        Collections.sort(kickers, COMPARATOR);
        hand.addAll(maxFlush);
        hand.addAll(kickers);

    }

    public void sortBestStraight(Hand hand) {
        Hand maxStraight = handEvaluator.getMaxStraight(hand);
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

    public void sortBestThreeOfAKind(Hand hand) {
        Hand threeOfAKind = hand.getThreeOfAKinds().get(0);
        Hand kickers = new Hand(hand);
        kickers.removeAll(threeOfAKind);
        Collections.sort(threeOfAKind, COMPARATOR);
        Collections.sort(kickers, COMPARATOR);
        hand.clear();
        hand.addAll(threeOfAKind);
        hand.addAll(kickers);
    }

    public void sortBestTwoPair(Hand hand) {
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

    public void sortBestPair(Hand hand) {
        Hand pair = hand.getPairs().get(0);
        Hand kickers = new Hand(hand);
        kickers.removeAll(pair);
        Collections.sort(pair, COMPARATOR);
        Collections.sort(kickers, COMPARATOR);
        hand.clear();
        hand.addAll(pair);
        hand.addAll(kickers);
    }

    public void sortKicker(Hand hand) {
        Collections.sort(hand, COMPARATOR);
    }


}
