package org.isma.poker.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class AbstractHand extends ArrayList<Card> implements IHand {
    AbstractHand() {
    }

    AbstractHand(Collection<Card> collection) {
        super(collection);
    }

    @Override
    public boolean add(Card card) {
        if (contains(card)) {
            throw new RuntimeException(String.format("cannot add two same cards : %s", card));
        }
        return super.add(card);
    }

    public boolean add(FiftyTwoCardsEnum card) {
        return super.add(card.getCard());
    }

    public boolean addAll(List<FiftyTwoCardsEnum> cards) {
        boolean ret = false;
        for (FiftyTwoCardsEnum card : cards) {
            ret |= add(card);
        }
        return ret;
    }
}
