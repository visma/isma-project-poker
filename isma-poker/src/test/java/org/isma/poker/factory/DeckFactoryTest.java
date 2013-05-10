package org.isma.poker.factory;

import org.isma.poker.model.AbstractHand;
import org.isma.poker.model.Deck;
import org.junit.Test;

import static org.isma.poker.factory.IDeckFactory.DeckTypeEnum.FIFTY_TWO_CARDS_DECK;
import static org.junit.Assert.assertEquals;

public class DeckFactoryTest {

    @Test
    public void texasHoldemDeck() {
        Deck deck = new DeckFactory().buildPokerDeck(FIFTY_TWO_CARDS_DECK);
        assertEquals(52, deck.size());

        int index = 0;
        index = assertSuitedCards(deck, index, "Spades");
        index = assertSuitedCards(deck, index, "Hearts");
        index = assertSuitedCards(deck, index, "Diamonds");
        index = assertSuitedCards(deck, index, "Clubs");
    }

    private int assertSuitedCards(Deck deck, int index, String suit) {
        AbstractHand innerData = (AbstractHand)deck.getInnerData();
        
        assertEquals("2 of " + suit, innerData.get(index++).toString());
        assertEquals("3 of " + suit, innerData.get(index++).toString());
        assertEquals("4 of " + suit, innerData.get(index++).toString());
        assertEquals("5 of " + suit, innerData.get(index++).toString());
        assertEquals("6 of " + suit, innerData.get(index++).toString());
        assertEquals("7 of " + suit, innerData.get(index++).toString());
        assertEquals("8 of " + suit, innerData.get(index++).toString());
        assertEquals("9 of " + suit, innerData.get(index++).toString());
        assertEquals("10 of " + suit, innerData.get(index++).toString());
        assertEquals("Knave of " + suit, innerData.get(index++).toString());
        assertEquals("Queen of " + suit, innerData.get(index++).toString());
        assertEquals("King of " + suit, innerData.get(index++).toString());
        assertEquals("Ace of " + suit, innerData.get(index++).toString());
        return index;
    }
}
