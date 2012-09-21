package org.isma.poker.game;

import org.isma.poker.exceptions.InvalidPlayerBetException;
import org.isma.poker.exceptions.InvalidPlayerTurnException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static junit.framework.Assert.assertEquals;
import static org.isma.poker.game.StepEnum.BETS_1;

public class GameSessionInvalidMovesTest extends Abstract2PlayerGameSessionTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void test_2Players_too_cheap_bet() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("invalid action :BET");

        gotoStep(BETS_1);
        assertEquals(player2, tableInfos.getCurrentPlayer());
        player2.bet(4, game);
    }

    @Test(expected = InvalidPlayerTurnException.class)
    public void test_2Players_player_bet_at_wrong_position() throws Exception {
        gotoStep(BETS_1);
        assertEquals(player2, tableInfos.getCurrentPlayer());
        player1.call(game);
    }


    @Test
    public void test_2Players_check_after_a_bet() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("invalid action :BET");

        gotoStep(BETS_1);
        assertEquals(player2, tableInfos.getCurrentPlayer());
        player2.bet(50, game);
        player2.check(game);
    }

    @Test
    public void test_2Players_raises_do_not_exceed_limit() throws Exception, InvalidPlayerBetException {
        gotoStep(BETS_1);
        player2.raise(10, game);
        player1.raise(10, game);
        player2.raise(10, game);
    }

    @Test(expected = InvalidPlayerBetException.class)
    public void test_2Players_raises_exceed_limit() throws Exception, InvalidPlayerBetException {
        gotoStep(BETS_1);
        player2.raise(10, game);
        player1.raise(10, game);
        player2.raise(10, game);
        player1.raise(10, game);
    }

}
