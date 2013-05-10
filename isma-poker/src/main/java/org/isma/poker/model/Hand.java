package org.isma.poker.model;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

import java.util.ArrayList;
import java.util.List;

public final class Hand extends AbstractHand {

    public Hand() {
    }

    public Hand(Hand hand) {
        super(hand);
    }


    public int count(final SuitEnum suit) {
        return CollectionUtils.countMatches(this, new Predicate() {
            @Override
            public boolean evaluate(Object o) {
                return ((Card) o).getSuit() == suit;
            }
        });
    }

    public int count(final ValueEnum valueEnum) {
        return CollectionUtils.countMatches(this, new Predicate() {
            @Override
            public boolean evaluate(Object o) {
                return ((Card) o).getValue() == valueEnum;
            }
        });

    }

    public List<Hand> getPairs() {
        List<Hand> pairs = new ArrayList<Hand>();
        for (ValueEnum valueEnum : ValueEnum.values()) {
            if (count(valueEnum) == 2) {
                pairs.add(getHand(valueEnum));
            }
        }
        return pairs;
    }

    public List<Hand> getThreeOfAKinds() {
        List<Hand> threeOfAKinds = new ArrayList<Hand>();
        for (ValueEnum valueEnum : ValueEnum.values()) {
            if (count(valueEnum) == 3) {
                threeOfAKinds.add(getHand(valueEnum));
            }
        }
        return threeOfAKinds;
    }

    public List<Hand> getFourOfAKind() {
        List<Hand> fourOfAKind = new ArrayList<Hand>();
        for (ValueEnum valueEnum : ValueEnum.values()) {
            if (count(valueEnum) == 4) {
                fourOfAKind.add(getHand(valueEnum));
            }
        }
        return fourOfAKind;
    }

    public Hand getFlush() {
        Hand maxFlush = new Hand();
        for (SuitEnum suitEnum : SuitEnum.values()) {
            if (count(suitEnum) >= 5) {
                maxFlush.addAll(getHand(suitEnum));
            }
        }
        return maxFlush;
    }


    public Hand getHand(ValueEnum valueEnum) {
        Hand hand = new Hand();
        for (Card card : this) {
            if (card.getValue() == valueEnum) {
                hand.add(card);
            }
        }
        return hand;
    }

    public Hand getHand(SuitEnum suitEnum) {
        Hand hand = new Hand();
        for (Card card : this) {
            if (card.getSuit() == suitEnum) {
                hand.add(card);
            }
        }
        return hand;
    }

}
