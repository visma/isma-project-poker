package org.isma.poker.model;

import java.util.List;

public interface IHand {
    public boolean add(Card card);
    public boolean add(FiftyTwoCardsEnum card);
    public boolean addAll(List<FiftyTwoCardsEnum> cards);
}
