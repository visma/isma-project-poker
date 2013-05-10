package org.isma.poker.model;

import org.isma.poker.factory.DeckFactory;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.*;
import static org.isma.poker.factory.IDeckFactory.DeckTypeEnum.FIFTY_TWO_CARDS_DECK;
import static org.isma.poker.model.FiftyTwoCardsEnum.*;

public class DeckTest {
    public Deck deck;

    @Before
    public void setUp() throws Exception {
        buildNewDeck();
    }

    private void buildNewDeck() {
        deck = new DeckFactory().buildPokerDeck(FIFTY_TWO_CARDS_DECK);
    }

    @Test
    public void size() {
        assertEquals(52, deck.size());
    }


    @Test
    public void natural_order() {
        List<Card> tenCards = deck.deal(10);
        assertEquals(10, tenCards.size());

        assertNotSame("burn method not called", TWO_OF_SPADES.getCard(), tenCards.get(0));
        assertEquals(THREE_OF_SPADES.getCard(), tenCards.get(0));
        assertEquals(FOUR_OF_SPADES.getCard(), tenCards.get(1));
        assertEquals(FIVE_OF_SPADES.getCard(), tenCards.get(2));
        assertEquals(SIX_OF_SPADES.getCard(), tenCards.get(3));
        assertEquals(SEVEN_OF_SPADES.getCard(), tenCards.get(4));
        assertEquals(EIGHT_OF_SPADES.getCard(), tenCards.get(5));
        assertEquals(NINE_OF_SPADES.getCard(), tenCards.get(6));
        assertEquals(TEN_OF_SPADES.getCard(), tenCards.get(7));
        assertEquals(KNAVE_OF_SPADES.getCard(), tenCards.get(8));
        assertEquals(QUEEN_OF_SPADES.getCard(), tenCards.get(9));
    }

    @Test
    public void burn() {
        assertEquals(52, deck.size());
        assertEquals(THREE_OF_SPADES.getCard(), deck.deal(1).get(0));
        assertEquals(50, deck.size());
        assertEquals(FIVE_OF_SPADES.getCard(), deck.deal(1).get(0));
        assertEquals(48, deck.size());
        assertEquals(NINE_OF_SPADES.getCard(), deck.deal(3).get(2));
        assertEquals(44, deck.size());
    }

    @Test
    public void shuffle() {
        deck.shuffle();
        List<Card> cards = deck.deal(8);
        assertEquals(8, cards.size());

        boolean firstCardIsSorted = THREE_OF_SPADES.getCard() == cards.get(0);
        boolean secondCardIsSorted = FOUR_OF_SPADES.getCard() == cards.get(1);
        boolean thirthCardIsSorted = FIVE_OF_SPADES.getCard() == cards.get(2);
        boolean fourthCardIsSorted = SIX_OF_SPADES.getCard() == cards.get(3);
        boolean fifthCardIsSorted = SEVEN_OF_SPADES.getCard() == cards.get(4);
        boolean sixthCardIsSorted = EIGHT_OF_SPADES.getCard() == cards.get(5);
        boolean seventhCardIsSorted = NINE_OF_SPADES.getCard() == cards.get(6);
        boolean eighthCardIsSorted = TEN_OF_SPADES.getCard() == cards.get(7);
        assertFalse(firstCardIsSorted && secondCardIsSorted && thirthCardIsSorted && fourthCardIsSorted && fifthCardIsSorted && sixthCardIsSorted && seventhCardIsSorted && eighthCardIsSorted);
    }

    @Test
    public void sort() {
        deck.shuffle();
        deck.sort();

        List<Card> cards = deck.deal(51);
        assertEquals(51, cards.size());
        assertNotSame("burn method not called", TWO_OF_SPADES.getCard(), cards.get(0));
        assertEquals(THREE_OF_SPADES.getCard(), cards.get(0));
        assertEquals(ACE_OF_SPADES.getCard(), cards.get(11));
        assertEquals(TWO_OF_HEARTS.getCard(), cards.get(12));
        assertEquals(ACE_OF_HEARTS.getCard(), cards.get(24));
        assertEquals(ACE_OF_DIAMONDS.getCard(), cards.get(37));
        assertEquals(ACE_OF_CLUBS.getCard(), cards.get(50));
    }

}
