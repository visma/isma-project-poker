package org.isma.poker.comparators;

import org.isma.poker.model.Card;

import java.util.Comparator;

public class CardValueComparator implements Comparator<Card> {

    @Override
    public int compare(Card card1, Card card2) {
        return card1.getValue().getValue() - card2.getValue().getValue();
    }
}
