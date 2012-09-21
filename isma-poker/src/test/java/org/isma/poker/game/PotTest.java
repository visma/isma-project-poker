package org.isma.poker.game;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.*;

public class PotTest {
    private Player toto;
    private Player tata;
    private Player titi;

    @Before
    public void setUp() throws Exception {
        toto = new Player("toto");
        tata = new Player("tata");
        titi = new Player("titi");
    }

    @Test
    public void test_split_pots() throws Exception {
        Pot pot = new Pot();
        assertEquals(0, pot.getTotal());
        pot.add(toto, 50);
        pot.add(tata, 20);
        pot.add(titi, 50);
        assertEquals(120, pot.getTotal());

        List<SplitPot> splitPots = pot.buildSplitPots();
        assertEquals(2, splitPots.size());

        SplitPot firstPot = splitPots.get(0);
        SplitPot secondPot = splitPots.get(1);
        assertEquals(3, firstPot.getPlayers().size());
        assertTrue(firstPot.getPlayers().contains(toto));
        assertTrue(firstPot.getPlayers().contains(tata));
        assertTrue(firstPot.getPlayers().contains(titi));

        assertEquals(2, secondPot.getPlayers().size());
        assertTrue(secondPot.getPlayers().contains(toto));
        assertFalse(secondPot.getPlayers().contains(tata));
        assertTrue(secondPot.getPlayers().contains(titi));
    }
}
