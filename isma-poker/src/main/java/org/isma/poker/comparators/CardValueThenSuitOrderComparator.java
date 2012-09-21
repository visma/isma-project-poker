package org.isma.poker.comparators;

import org.isma.poker.model.Card;

import java.util.Comparator;

public class CardValueThenSuitOrderComparator implements Comparator<Card> {

    @Override
    public int compare(Card card1, Card card2) {
        if (card2.getValue().getValue() != card1.getValue().getValue()) {
            return card2.getValue().getValue() - card1.getValue().getValue();
        }
        return card1.getSuit().ordinal() - card2.getSuit().ordinal();
    }
}
