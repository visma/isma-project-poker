package org.isma.poker.comparators;

import org.isma.poker.HandEvaluationEnum;

import java.util.Comparator;

public class HandEvaluationComparator implements Comparator<HandEvaluationEnum> {
    @Override
    public int compare(HandEvaluationEnum e1, HandEvaluationEnum e2) {
        return e1.getRank() - e2.getRank();
    }
}
