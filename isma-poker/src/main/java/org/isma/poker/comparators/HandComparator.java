package org.isma.poker.comparators;

import org.isma.poker.HandEvaluationEnum;
import org.isma.poker.HandEvaluator;
import org.isma.poker.model.Card;
import org.isma.poker.model.Hand;

import java.util.Comparator;

public class HandComparator implements Comparator<Hand> {
    private final HandEvaluator handEvaluator = new HandEvaluator();
    private final HandEvaluationComparator handEvaluationComparator = new HandEvaluationComparator();
    private final CardValueComparator cardValueComparator = new CardValueComparator();

    @Override
    public int compare(Hand hand1, Hand hand2) {
        HandEvaluationEnum handEvaluation1 = handEvaluator.evaluate(hand1);
        HandEvaluationEnum handEvaluation2 = handEvaluator.evaluate(hand2);

        int compare = handEvaluationComparator.compare(handEvaluation1, handEvaluation2);
        if (compare != 0) {
            return compare;
        }
        for (int i = 0; i < hand1.size(); i++) {
            Card card1 = hand1.get(i);
            Card card2 = hand2.get(i);
            compare = cardValueComparator.compare(card1, card2);
            if (compare != 0) {
                return compare;
            }
        }
        return 0;
    }
}
