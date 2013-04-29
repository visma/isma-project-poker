package org.isma.poker.factory;

import org.isma.poker.model.Deck;

public interface IDeckFactory {
    public enum DeckTypeEnum {
        FIFTY_TWO_CARDS_DECK;
    }

    Deck buildPokerDeck(DeckFactory.DeckTypeEnum deckTypeEnum);
}
