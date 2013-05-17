package org.isma.poker.model;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class AbstractHand extends ArrayList<Card> implements IHand {
    private static final Logger LOG = Logger.getLogger(AbstractHand.class);

    AbstractHand() {
    }

    AbstractHand(Collection<Card> collection) {
        super(collection);
    }

    @Override
    public boolean add(Card card) {
        if (contains(card)) {
            //throw new RuntimeException(String.format("cannot add two same cards : %s", card));
            LOG.debug(String.format("card already in deck. %s skipped", card));
            return false;
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
