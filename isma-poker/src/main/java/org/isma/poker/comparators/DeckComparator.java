package org.isma.poker.comparators;

import org.isma.poker.model.Card;

import java.util.Comparator;

public class DeckComparator implements Comparator<Card>  {
    @Override
    public int compare(Card card1, Card card2) {
        if (card1.getSuit().ordinal() != card2.getSuit().ordinal()) {
            return card1.getSuit().ordinal() - card2.getSuit().ordinal();
        }
        return card1.getValue().getValue() - card2.getValue().getValue();
    }
}
