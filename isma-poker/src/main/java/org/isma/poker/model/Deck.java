package org.isma.poker.model;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Deck implements IHand {
    private static final Logger LOG = Logger.getLogger(Deck.class);
    //Inner composition : not exposing Collection method
    protected final AbstractHand innerHand = new AbstractHand() {
    };


    public void shuffle() {
        Collections.shuffle(innerHand);
    }

    public void sort() {
        Collections.sort(innerHand, new DeckComparator());
    }

    protected void burn() {
        innerHand.remove(0);
    }

    public List<Card> deal(int amount) {
        burn();
        List<Card> cards = new ArrayList<Card>();
        for (int i = 0; i < amount; i++) {
            cards.add(innerHand.remove(0));
        }
        return cards;
    }

    @Override
    public boolean add(Card card) {
        return innerHand.add(card);
    }

    @Override
    public boolean add(FiftyTwoCardsEnum card) {
        return innerHand.add(card);
    }

    @Override
    public boolean addAll(List<FiftyTwoCardsEnum> cards) {
        boolean ret = false;
        for (FiftyTwoCardsEnum card : cards) {
            ret |= add(card);
        }
        return ret;
    }

    public int size() {
        return innerHand.size();
    }

    public Object getInnerData() {
        LOG.warn("exposing inner data must be avoid if not in a test context !!!");
        return innerHand;
    }

    private class DeckComparator implements Comparator<Card> {
        @Override
        public int compare(Card card1, Card card2) {
            if (card1.getSuit().ordinal() != card2.getSuit().ordinal()) {
                return card1.getSuit().ordinal() - card2.getSuit().ordinal();
            }
            return card1.getValue().getValue() - card2.getValue().getValue();
        }
    }
}
