package org.isma.poker.comparators;

import org.isma.poker.HandEvaluator;
import org.isma.poker.model.Card;
import org.isma.poker.model.Hand;
import org.isma.poker.model.HandEvaluation;

import java.util.Comparator;

public class HandComparator implements Comparator<Hand> {
    public final static Comparator<HandEvaluation> HAND_EVALUATION_COMPARATOR = new HandEvaluationComparator();
    private final HandEvaluator handEvaluator = new HandEvaluator();
    private final CardValueComparator cardValueComparator = new CardValueComparator();

    @Override
    public int compare(Hand hand1, Hand hand2) {
        HandEvaluation handEvaluation1 = handEvaluator.evaluate(hand1);
        HandEvaluation handEvaluation2 = handEvaluator.evaluate(hand2);

        int compare = HAND_EVALUATION_COMPARATOR.compare(handEvaluation1, handEvaluation2);
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

    private static class HandEvaluationComparator implements Comparator<HandEvaluation> {
        @Override
        public int compare(HandEvaluation e1, HandEvaluation e2) {
            return e1.getRank() - e2.getRank();
        }
    }
}
