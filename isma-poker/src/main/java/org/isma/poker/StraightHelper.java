package org.isma.poker;

import org.isma.poker.comparators.CardValueComparator;
import org.isma.poker.model.Card;
import org.isma.poker.model.Hand;
import org.isma.poker.model.ValueEnum;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.isma.poker.model.ValueEnum.*;

public class StraightHelper {
    private static final int STRAIGHT_CARDS_REQUIRED = 5;
    private static final CardValueComparator COMPARATOR = new CardValueComparator();

    private StraightHelper() {
    }


    public static List<Hand> getAllStraights(Hand hand) {
        Collections.sort(hand, COMPARATOR);
        List<ValueEnum> straightsLowerCardList = getStraightsLowerCards(hand);
        List<Hand> allStraights = new ArrayList<Hand>();
        for (ValueEnum valueEnum : straightsLowerCardList) {
            ValueEnum currentValue = valueEnum;
            List<Hand> straights = new ArrayList<Hand>();
            straights.add(new Hand());
            for (int i = 0; i < STRAIGHT_CARDS_REQUIRED; i++) {
                feedStraights(hand, straights, currentValue);
                currentValue = currentValue.nextValue();
            }
            allStraights.addAll(straights);
        }
        for (Hand finalStraight : allStraights) {
            Collections.reverse(finalStraight);
        }
        Collections.reverse(allStraights);
        return allStraights;
    }


    public static boolean isLowerStraight(Hand subHand) {
        Card card = subHand.get(0);
        if (card.getValue() != ValueEnum.TWO) {
            return false;
        }
        card = subHand.get(1);
        if (card.getValue() != ValueEnum.THREE) {
            return false;
        }
        card = subHand.get(2);
        if (card.getValue() != ValueEnum.FOUR) {
            return false;
        }
        card = subHand.get(3);
        if (card.getValue() != ValueEnum.FIVE) {
            return false;
        }
        card = subHand.get(4);
        return card.getValue() == ACE;
    }


    private static List<ValueEnum> getStraightsLowerCards(Hand hand) {
        List<ValueEnum> straightsLowerCards = new ArrayList<ValueEnum>();
        for (int startIndex = 0; startIndex <= hand.size() - STRAIGHT_CARDS_REQUIRED; startIndex++) {
            fillStraightsLowerCards(hand, straightsLowerCards, startIndex);
        }
        //Check Lowest Straight (Ace possible in last index)
        fillStraightsLowerCards(hand, straightsLowerCards, hand.size() - 1);
        return straightsLowerCards;
    }


    private static void fillStraightsLowerCards(Hand hand, List<ValueEnum> straightsLowerCards, int handIndex) {
        ValueEnum currentValue = hand.get(handIndex).getValue();
        if (straightsLowerCards.contains(currentValue)) {
            return;
        }
        ValueEnum secondValue;
        ValueEnum thirdValue;
        ValueEnum fourthValue;
        ValueEnum fifthValue;
        if (currentValue == ACE) {
            secondValue = TWO;
            thirdValue = THREE;
            fourthValue = FOUR;
            fifthValue = FIVE;
        } else {
            secondValue = currentValue.nextValue();
            thirdValue = secondValue.nextValue();
            fourthValue = thirdValue.nextValue();
            fifthValue = fourthValue.nextValue();
        }
        if (hand.count(secondValue) > 0 && hand.count(thirdValue) > 0 && hand.count(fourthValue) > 0 && hand.count(fifthValue) > 0) {
            straightsLowerCards.add(currentValue);
        }
    }

    /**
     * clone current straights to manage multiples values
     */
    private static void feedStraights(Hand hand, List<Hand> straights, ValueEnum valueEnum) {
        int originalStraightsSize = straights.size();
        int count = hand.count(valueEnum);
        Hand valueHand = hand.getHand(valueEnum);
        List<Hand> newStraights = new ArrayList<Hand>(originalStraightsSize * count);
        for (int i = 0; i < originalStraightsSize; i++) {
            Hand currentStraight = straights.get(i);
            for (int j = 0; j < count; j++) {
                Hand clone = new Hand(currentStraight);
                clone.add(valueHand.get(j));
                newStraights.add(clone);
            }
        }
        straights.clear();
        straights.addAll(newStraights);
    }

    /**
     * Prerequisite : subHand sorted
     */
    static boolean isStraight(Hand subHand) {
        if (isLowerStraight(subHand)) {
            return true;
        }
        int currentValue = subHand.get(0).getValue().getValue();
        for (int i = 1; i < subHand.size(); i++) {
            if (subHand.get(i).getValue().getValue() != currentValue + 1) {
                return false;
            }
            currentValue++;
        }
        return true;
    }

}
