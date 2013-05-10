package org.isma.poker.game;

import org.isma.poker.game.actions.PlayerAction;
import org.isma.poker.game.results.Results;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.isma.poker.game.step.StepEnum.*;

public class GameSession2PlayersFoldTest extends Abstract2PlayerGameSessionTest {
    @Test
    public void test_fold_in_bet1_game_over() throws Exception {
        gotoStep(BETS_1);
        player2FoldPlayer1Win();
    }

    @Test
    public void test_fold_in_bet2_game_over() throws Exception {
        gotoStep(BETS_2);
        player2FoldPlayer1Win();
    }

    @Test
    public void test_fold_in_bet3_game_over() throws Exception {
        gotoStep(BETS_3);
        player2FoldPlayer1Win();
    }

    @Test
    public void test_fold_in_bet4_game_over() throws Exception {
        gotoStep(BETS_4);
        player2FoldPlayer1Win();
    }

    @Test
    public void test_fold_in_showdown_game_over() throws Exception {
        gotoStep(SHOWDOWN);
        player2FoldPlayer1Win();
    }


    private void player2FoldPlayer1Win() throws Exception {
        assertEquals(player2, tableInfos.getCurrentPlayer());
        PlayerAction.fold(player2, game);
        assertTrue(game.isStepOver());
        game.nextStep();
        assertEquals(END, game.getStep());
        Results res = game.buildResults();
        assertEquals(1, res.getWinners().size());
        assertEquals(player1, res.getWinners().get(0).getPlayer());
    }


}
