package org.isma.poker.game;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static org.isma.poker.game.actions.PlayerAction.*;
import static org.isma.poker.game.step.StepEnum.*;

public class GameSession3PlayersRaisesTest extends Abstract3PlayersGameSessionTest {
    @Test
    public void a_small_raise_allin_by_last_player_to_speak_in_bet1_game_continue() throws Exception {
        //Given
        assertEquals(100, player1.getChips());
        assertEquals(80, player2.getChips());
        assertEquals(60, player3.getChips());
        gotoStep(BETS_1);
        call(player1, game);
        call(player2, game);

        //When
        raise(player3, game, 50);

        //Then
        call(player1, game);
        call(player2, game);
        assertEquals(BETS_2, game.getStep());
        check(player1, game);
        check(player2, game);
        assertEquals(BETS_3, game.getStep());
        check(player1, game);
        check(player2, game);
        assertEquals(BETS_4, game.getStep());
        check(player1, game);
        check(player2, game);
        assertEquals(SHOWDOWN, game.getStep());
        show(player1, game);
        show(player2, game);
        show(player3, game);
    }

    @Test
    public void a_small_allin_by_last_player_to_speak_in_bet1_game_continue() throws Exception {
        //Given
        assertEquals(100, player1.getChips());
        assertEquals(80, player2.getChips());
        assertEquals(60, player3.getChips());
        gotoStep(BETS_1);
        call(player1, game);
        call(player2, game);

        //When
        allIn(player3, game);

        //Then
        call(player1, game);
        call(player2, game);
        assertEquals(BETS_2, game.getStep());
        check(player1, game);
        check(player2, game);
        assertEquals(BETS_3, game.getStep());
        check(player1, game);
        check(player2, game);
        assertEquals(BETS_4, game.getStep());
        check(player1, game);
        check(player2, game);
        assertEquals(SHOWDOWN, game.getStep());
        show(player1, game);
        show(player2, game);
        show(player3, game);
    }
}


