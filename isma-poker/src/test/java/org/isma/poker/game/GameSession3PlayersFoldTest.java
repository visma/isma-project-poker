package org.isma.poker.game;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static org.isma.poker.game.actions.PlayerAction.*;
import static org.isma.poker.game.step.StepEnum.*;

public class GameSession3PlayersFoldTest extends Abstract3PlayersGameSessionTest {
    @Test
    public void a_fold_in_bet1_game_continue() throws Exception {
        //Given
        gotoStep(BETS_1);
        call(player1, game);

        //When
        fold(player2, game);

        //Then
        check(player3, game);
        assertEquals(BETS_2, game.getStep());
    }

    @Test
    public void a_fold_in_bet2_game_continue() throws Exception {
        gotoStep(BETS_2);
        check(player1, game);
        fold(player2, game);
        bet(player3, game, 10);
        call(player1, game);
    }

    @Test
    public void a_fold_in_bet3_game_continue() throws Exception {
        gotoStep(BETS_3);
        check(player1, game);
        fold(player2, game);
        bet(player3, game, 10);
        call(player1, game);
    }

    @Test
    public void a_fold_in_bet4_game_continue() throws Exception {
        gotoStep(BETS_4);
        check(player1, game);
        fold(player2, game);
        bet(player3, game, 10);
        call(player1, game);
    }

    @Test
    public void a_fold_in_bet5_game_continue() throws Exception {
        gotoStep(SHOWDOWN);
        show(player1, game);
        fold(player2, game);
        show(player3, game);
        assertEquals(BLINDS, game.getStep());
    }
}
