package org.isma.poker.mock;

import org.isma.poker.model.Deck;
import org.isma.poker.model.FiftyTwoCardsEnum;

public class MockDeck extends Deck {

    public MockDeck() {
        super();
    }

    @Override
    public void shuffle() {
    }

    protected void burn() {
    }

    public void prepareCards(FiftyTwoCardsEnum... cards) {
        for (int i = 0; i < cards.length; i++) {
            innerHand.remove(cards[i].getCard());
            innerHand.add(i, cards[i].getCard());
        }
    }
}
