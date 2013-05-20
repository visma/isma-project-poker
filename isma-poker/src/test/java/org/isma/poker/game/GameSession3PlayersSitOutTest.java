package org.isma.poker.game;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static org.isma.poker.game.actions.PlayerAction.*;
import static org.isma.poker.game.step.StepEnum.*;

public class GameSession3PlayersSitOutTest extends Abstract3PlayersGameSessionTest {
    @Test
    public void small_blind_sitout() throws Exception {
        //Given
        gotoStep(BLINDS);
        assertEquals(player2, game.getTableInfos().getSmallBlindPlayer());
        assertEquals(player2, game.getTableInfos().getCurrentPlayer());
        assertEquals(player3, game.getTableInfos().getBigBlindPlayer());

        //When
        sitOut(player2, game);

        //Then
        assertEquals(player3, game.getTableInfos().getCurrentPlayer());
        assertEquals(player3, game.getTableInfos().getSmallBlindPlayer());
        assertEquals(player1, game.getTableInfos().getBigBlindPlayer());

        paySmallBlind(player3, game);
        payBigBlind(player1, game);

        assertEquals(BETS_1, game.getStep());
        call(player3, game);
        check(player1, game);
        assertEquals(BETS_2, game.getStep());
        assertEquals(20, game.getPot().getTotal());
    }


    @Test
    public void big_blind_sitout() throws Exception {
        //Given
        gotoStep(BLINDS);
        assertEquals(player2, game.getTableInfos().getSmallBlindPlayer());

        //When
        paySmallBlind(player2, game);
        sitOut(player3, game);

        //Then
        assertEquals(player1, game.getTableInfos().getCurrentPlayer());
        assertEquals(player1, game.getTableInfos().getBigBlindPlayer());

        payBigBlind(player1, game);

        assertEquals(BETS_1, game.getStep());
        call(player2, game);
        check(player1, game);
        assertEquals(BETS_2, game.getStep());
        assertEquals(20, game.getPot().getTotal());
    }
}
