package org.isma.poker.game;

import org.isma.poker.game.event.RoundEndEvent;
import org.isma.poker.game.results.Results;
import org.isma.poker.game.test.TestResultEventListener;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.isma.poker.game.actions.PlayerAction.fold;
import static org.isma.poker.game.step.StepEnum.*;

public class GameSession2PlayersFoldTest extends Abstract2PlayersGameSessionTest {
    private TestResultEventListener resultEventListener;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        game.removeEventListener(resultEventListener);
        resultEventListener = new TestResultEventListener();
        game.addEventListener(resultEventListener);
    }

    @Test
    public void fold_in_bet1_game_over() throws Exception {
        gotoStep(BETS_1);
        player2FoldPlayer1Win();
    }

    @Test
    public void fold_in_bet2_game_over() throws Exception {
        gotoStep(BETS_2);
        player2FoldPlayer1Win();
    }

    @Test
    public void fold_in_bet3_game_over() throws Exception {
        gotoStep(BETS_3);
        player2FoldPlayer1Win();
    }

    @Test
    public void fold_in_bet4_game_over() throws Exception {
        gotoStep(BETS_4);
        player2FoldPlayer1Win();
    }

    @Test
    public void fold_in_showdown_game_over() throws Exception {
        gotoStep(SHOWDOWN);
        player2FoldPlayer1Win();
    }


    private void player2FoldPlayer1Win() throws Exception {
        assertEquals(player2, tableFacade.getCurrentPlayer());

        //When
        fold(player2, game);

        //Then
        assertEquals(BLINDS, game.getStep());
        assertTrue(resultEventListener.hasEvents());
        RoundEndEvent endEvent = (RoundEndEvent)resultEventListener.poll();
        Results res = endEvent.getResults();
        assertEquals(1, res.getWinners().size());
        assertEquals(player1, res.getWinners().get(0).getPlayer());
    }


}
