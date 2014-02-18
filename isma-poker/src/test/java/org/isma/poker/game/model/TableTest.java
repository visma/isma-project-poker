package org.isma.poker.game.model;

import junit.framework.Assert;
import org.isma.poker.mock.MockDeck;
import org.isma.poker.model.Deck;
import org.isma.poker.model.FiftyTwoCardsEnum;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class TableTest {

    @Test
    public void dontDealHoldeCardsForFoldedPlayers() {
        //Given
        Deck deck = new MockDeck();
        deck.add(FiftyTwoCardsEnum.FIVE_OF_CLUBS);
        deck.add(FiftyTwoCardsEnum.SEVEN_OF_CLUBS);
        deck.add(FiftyTwoCardsEnum.KING_OF_DIAMONDS);
        deck.add(FiftyTwoCardsEnum.QUEEN_OF_HEARTS);


        Table table = new Table();
        Player toto = new Player("toto");
        Player tata = new Player("tata");
        toto.setChips(100);
        tata.setChips(100);
        table.add(toto);
        table.add(tata);

        //When
        table.prepareBlindsStep();
        tata.setFold(true);
        table.prepareDealHoleCardsStep(deck);

        //Then
        assertEquals(2, toto.getHand().size());
        assertTrue(tata.getHand().isEmpty());
    }

    @Test
    public void dontDealCommunityCardsForFoldedPlayers() {
        //Given
        Deck deck = new MockDeck();
        deck.add(FiftyTwoCardsEnum.FIVE_OF_CLUBS);
        deck.add(FiftyTwoCardsEnum.SEVEN_OF_CLUBS);
        deck.add(FiftyTwoCardsEnum.KING_OF_DIAMONDS);


        Table table = new Table();
        Player toto = new Player("toto");
        Player tata = new Player("tata");
        toto.setChips(100);
        tata.setChips(100);
        table.add(toto);
        table.add(tata);

        //When
        table.prepareBlindsStep();
        tata.setFold(true);

        table.prepapreDealCommunityCardStep(deck, 3);

        //Then
        assertEquals(3, toto.getHand().size());
        assertTrue(tata.getHand().isEmpty());
    }
}
