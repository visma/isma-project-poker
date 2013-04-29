package org.isma.poker.model;

import java.util.List;

public interface IHand {
    boolean add(Card card);
    boolean add(FiftyTwoCardsEnum card);
    boolean addAll(List<FiftyTwoCardsEnum> cards);
}
