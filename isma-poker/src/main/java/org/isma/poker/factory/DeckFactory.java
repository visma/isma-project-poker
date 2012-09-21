package org.isma.poker.factory;

import org.isma.poker.model.Deck;
import org.isma.poker.model.FiftyTwoCardsEnum;

public class DeckFactory implements IDeckFactory {

    public Deck buildPokerDeck(DeckTypeEnum deckTypeEnum) {
        Deck deck = new Deck();
        if (deckTypeEnum == DeckTypeEnum.FIFTY_TWO_CARDS_DECK) {
            FiftyTwoCardsEnum.values();
            for (FiftyTwoCardsEnum cardEnum : FiftyTwoCardsEnum.values()) {
                deck.add(cardEnum);
            }
        }
        return deck;
    }
}
