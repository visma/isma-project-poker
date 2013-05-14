package org.isma.poker.mock;

import org.isma.poker.model.Card;
import org.isma.poker.model.Deck;
import org.isma.poker.model.FiftyTwoCardsEnum;

import java.util.List;

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

    public void prepareCards(List<Card> cards) {
        for (int i = 0; i < cards.size(); i++) {
            innerHand.remove(cards.get(i));
            innerHand.add(i, cards.get(i));
        }
    }
}
