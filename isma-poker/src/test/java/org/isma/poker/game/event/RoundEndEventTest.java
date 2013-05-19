package org.isma.poker.game.event;

import org.isma.poker.game.model.Player;
import org.isma.poker.game.model.Pot;
import org.isma.poker.game.results.Results;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.isma.poker.model.FiftyTwoCardsEnum.*;

public class RoundEndEventTest {

    @Test
    public void clone_content() {
        //Given
        Pot pot = new Pot();

        Player winnner = new Player("toto");
        winnner.getHand().add(ACE_OF_DIAMONDS);
        winnner.getHand().add(ACE_OF_CLUBS);
        winnner.getHand().add(ACE_OF_SPADES);
        winnner.getHand().add(ACE_OF_HEARTS);
        winnner.getHand().add(TWO_OF_CLUBS);

        Player loser = new Player("tata");
        loser.getHand().add(THREE_OF_DIAMONDS);
        loser.getHand().add(THREE_OF_CLUBS);
        loser.getHand().add(THREE_OF_SPADES);
        loser.getHand().add(THREE_OF_HEARTS);
        loser.getHand().add(TWO_OF_CLUBS);

        List<Player> players = new ArrayList<Player>();
        players.add(winnner);
        players.add(loser);

        pot.add(winnner, 50);
        pot.add(loser, 50);

        Results results = new Results(pot, players);

        //When
        RoundEndEvent event = new RoundEndEvent(results);
        winnner.getHand().clear();

        //Then
        assertEquals(0, winnner.getHand().size());
        assertEquals("toto", event.getResults().getWinners().get(0).getPlayer().getNickname());
        assertEquals(5, event.getResults().getWinners().get(0).getPlayer().getHand().size());
    }
}
