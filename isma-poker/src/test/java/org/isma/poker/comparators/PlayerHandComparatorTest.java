package org.isma.poker.comparators;

import junit.framework.Assert;
import org.isma.poker.game.Player;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static org.isma.poker.model.FiftyTwoCardsEnum.*;

public class PlayerHandComparatorTest {

    public static final PlayerHandComparator COMPARATOR = new PlayerHandComparator();

    @Test
    public void test_sort() throws Exception {

        Player toto = new Player("toto");
        Player tata = new Player("tata");
        Player titi = new Player("titi");
        //Straight flush
        titi.getHand().addAll(asList(TWO_OF_HEARTS, THREE_OF_HEARTS, FOUR_OF_HEARTS, FIVE_OF_HEARTS, EIGHT_OF_DIAMONDS, SIX_OF_HEARTS, SEVEN_OF_HEARTS));
        //Pair
        tata.getHand().addAll(asList(TWO_OF_HEARTS, THREE_OF_HEARTS, FOUR_OF_HEARTS, FIVE_OF_HEARTS, EIGHT_OF_DIAMONDS, KING_OF_CLUBS, KING_OF_DIAMONDS));
        //Kicker
        toto.getHand().addAll(asList(TWO_OF_HEARTS, THREE_OF_HEARTS, FOUR_OF_HEARTS, FIVE_OF_HEARTS, EIGHT_OF_DIAMONDS, NINE_OF_DIAMONDS, KNAVE_OF_DIAMONDS));
        List<Player> list = asList(toto, tata, titi);
        Collections.sort(list, COMPARATOR);
        Assert.assertEquals(toto, list.get(0));
        Assert.assertEquals(tata, list.get(1));
        Assert.assertEquals(titi, list.get(2));
    }
}
