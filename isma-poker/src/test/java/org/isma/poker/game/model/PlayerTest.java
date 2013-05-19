package org.isma.poker.game.model;

import org.junit.Test;

import static org.isma.poker.model.FiftyTwoCardsEnum.ACE_OF_CLUBS;
import static org.junit.Assert.*;

public class PlayerTest {

    @Test
    public void clone_content(){
        //Given
        Player p = new Player("toto");
        p.setChips(10);
        p.setFold(true);
        p.getHand().add(ACE_OF_CLUBS);

        //When
        Player clone = p.clone();

        //Then
        assertFalse(p == clone);
        assertEquals(p, clone);

        assertEquals(10, clone.getChips());
        assertEquals(true, clone.isFold());
        assertEquals(1, clone.getHand().size());
        assertEquals(ACE_OF_CLUBS.getCard(), clone.getHand().get(0));
    }

    @Test
    public void equals_by_nickname(){
        //Given
        Player p1 = new Player("toto");
        p1.setChips(10);
        p1.setFold(true);
        Player p2 = new Player("toto");
        p1.setChips(100);
        p1.setFold(false);

        //Then
        assertEquals(p1, p2);
    }

    @Test
    public void not_equals_if_different_nickname(){
        //Given
        Player p1 = new Player("toto");
        p1.setChips(10);
        p1.setFold(true);
        Player p2 = new Player("toto2");
        p1.setChips(10);
        p1.setFold(true);

        //Then
        assertNotSame(p1, p2);
    }
}
