package org.isma.poker.game;

import org.isma.poker.game.actions.PlayerAction;
import org.isma.poker.game.exceptions.InvalidPlayerBetException;
import org.isma.poker.game.model.InvalidPlayerTurnException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static junit.framework.Assert.assertEquals;
import static org.isma.poker.game.step.StepEnum.BETS_1;

//TODO compléter ce test avec d'autres cas
public class GameSessionInvalidMovesTest extends Abstract2PlayersGameSessionTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void two_Players_too_cheap_bet() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("invalid action :BET");

        gotoStep(BETS_1);
        assertEquals(player2, tableInfos.getCurrentPlayer());
        PlayerAction.bet(player2, game, 4);
    }

    @Test(expected = InvalidPlayerTurnException.class)
    public void two_Players_player_bet_at_wrong_position() throws Exception {
        gotoStep(BETS_1);
        assertEquals(player2, tableInfos.getCurrentPlayer());
        PlayerAction.call(player1, game);
    }


    @Test
    public void two_Players_check_after_a_bet() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("invalid action :BET");

        gotoStep(BETS_1);
        assertEquals(player2, tableInfos.getCurrentPlayer());
        PlayerAction.bet(player2, game, 50);
        PlayerAction.check(player2, game);
    }

    @Test
    public void two_Players_raises_do_not_exceed_limit() throws Exception {
        gotoStep(BETS_1);
        PlayerAction.raise(player2, game, 10);
        PlayerAction.raise(player1, game, 10);
        PlayerAction.raise(player2, game, 10);
    }

    @Test(expected = InvalidPlayerBetException.class)
    public void two_Players_raises_exceed_limit() throws Exception {
        gotoStep(BETS_1);
        PlayerAction.raise(player2, game, 10);
        PlayerAction.raise(player1, game, 10);
        PlayerAction.raise(player2, game, 10);
        PlayerAction.raise(player1, game, 10);
    }

}
