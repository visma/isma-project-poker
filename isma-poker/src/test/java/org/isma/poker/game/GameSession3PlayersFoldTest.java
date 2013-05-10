package org.isma.poker.game;

import org.isma.poker.game.actions.PlayerAction;
import org.isma.poker.game.step.StepEnum;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;

public class GameSession3PlayersFoldTest extends Abstract3PlayersGameSessionTest {
    @Test
    public void a_fold_in_bet1_game_continue() throws Exception {
        gotoStep(StepEnum.BETS_1);
        PlayerAction.call(player1, game);
        PlayerAction.fold(player2, game);
        PlayerAction.check(player3, game);
        assertTrue(game.isStepOver());
    }

    @Test
    public void a_fold_in_bet2_game_continue() throws Exception {
        gotoStep(StepEnum.BETS_2);
        PlayerAction.check(player1, game);
        PlayerAction.fold(player2, game);
        PlayerAction.bet(player3, game, 10);
        PlayerAction.call(player1, game);
    }

    @Test
    public void a_fold_in_bet3_game_continue() throws Exception {
        gotoStep(StepEnum.BETS_3);
        PlayerAction.check(player1, game);
        PlayerAction.fold(player2, game);
        PlayerAction.bet(player3, game, 10);
        PlayerAction.call(player1, game);
    }

    @Test
    public void a_fold_in_bet4_game_continue() throws Exception {
        gotoStep(StepEnum.BETS_4);
        PlayerAction.check(player1, game);
        PlayerAction.fold(player2, game);
        PlayerAction.bet(player3, game, 10);
        PlayerAction.call(player1, game);
    }

    @Test
    public void a_fold_in_bet5_game_continue() throws Exception {
        gotoStep(StepEnum.SHOWDOWN);
        PlayerAction.show(player1, game);
        PlayerAction.fold(player2, game);
        PlayerAction.show(player3, game);
        assertTrue(game.isStepOver());
    }
}
