package org.isma.poker.mock;

import org.isma.poker.factory.IDeckFactory;
import org.isma.poker.model.FiftyTwoCardsEnum;

public class MockDeckFactory implements IDeckFactory {

    public MockDeck buildPokerDeck(DeckTypeEnum deckTypeEnum) {
        MockDeck deck = new MockDeck();
        if (deckTypeEnum == DeckTypeEnum.FIFTY_TWO_CARDS_DECK) {
            FiftyTwoCardsEnum.values();
            for (FiftyTwoCardsEnum cardEnum : FiftyTwoCardsEnum.values()) {
                deck.add(cardEnum);
            }
        }
        return deck;
    }

}
