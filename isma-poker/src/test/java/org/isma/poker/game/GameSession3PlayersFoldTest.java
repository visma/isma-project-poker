package org.isma.poker.game;

import org.junit.Test;

import static junit.framework.Assert.assertTrue;

public class GameSession3PlayersFoldTest extends Abstract3PlayersGameSessionTest {
    @Test
    public void test_a_fold_in_bet1_game_continue() throws Exception {
        gotoStep(StepEnum.BETS_1);
        player1.call(game);
        player2.fold(game);
        player3.check(game);
        assertTrue(game.isStepOver());
    }

    @Test
    public void test_a_fold_in_bet2_game_continue() throws Exception {
        gotoStep(StepEnum.BETS_2);
        player1.check(game);
        player2.fold(game);
        player3.bet(10, game);
        player1.call(game);
    }

    @Test
    public void test_a_fold_in_bet3_game_continue() throws Exception {
        gotoStep(StepEnum.BETS_3);
        player1.check(game);
        player2.fold(game);
        player3.bet(10, game);
        player1.call(game);
    }

    @Test
    public void test_a_fold_in_bet4_game_continue() throws Exception {
        gotoStep(StepEnum.BETS_4);
        player1.check(game);
        player2.fold(game);
        player3.bet(10, game);
        player1.call(game);
    }

    @Test
    public void test_a_fold_in_bet5_game_continue() throws Exception {
        gotoStep(StepEnum.SHOWDOWN);
        player1.show(game);
        player2.fold(game);
        player3.show(game);
        assertTrue(game.isStepOver());
    }
}
