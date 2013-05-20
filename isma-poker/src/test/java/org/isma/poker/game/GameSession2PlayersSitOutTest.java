package org.isma.poker.game;

import org.isma.poker.game.model.Player;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static org.isma.poker.game.actions.PlayerAction.*;
import static org.isma.poker.game.step.StepEnum.BLINDS;
import static org.isma.poker.game.step.StepEnum.END;

public class GameSession2PlayersSitOutTest extends Abstract2PlayersGameSessionTest {
    @Test
    public void small_blind_sitout_and_new_challenger() throws Exception {
        //Given
        gotoStep(BLINDS);
        assertEquals(player2, tableInfos.getSmallBlindPlayer());
        assertEquals(player1, tableInfos.getBigBlindPlayer());

        //When
        sitOut(player2, game);

        //Then
        assertEquals(END, game.getStep());
        assertEquals(null, game.getTableInfos().getCurrentPlayer());

        Player nouveau = new Player("nouveau");
        nouveau.setChips(200);
        sitIn(nouveau, game);

        assertEquals(player1, game.getTableInfos().getDealer());
        assertEquals(nouveau, game.getTableInfos().getSmallBlindPlayer());
        assertEquals(player1, game.getTableInfos().getBigBlindPlayer());
        assertEquals(nouveau, game.getTableInfos().getCurrentPlayer());
        assertEquals(BLINDS, game.getStep());

        paySmallBlind(nouveau, game);
        payBigBlind(player1, game);
    }


    @Test
    public void big_blind_sitout_and_new_challenger() throws Exception {
        //Given
        gotoStep(BLINDS);
        assertEquals(player2, tableInfos.getSmallBlindPlayer());
        assertEquals(player1, tableInfos.getBigBlindPlayer());


        //When
        paySmallBlind(player2, game);
        sitOut(player1, game);

        //Then
        assertEquals(END, game.getStep());
        assertEquals(null, game.getTableInfos().getCurrentPlayer());

        Player nouveau = new Player("nouveau");
        nouveau.setChips(200);
        sitIn(nouveau, game);

        assertEquals(player2, game.getTableInfos().getDealer());
        assertEquals(nouveau, game.getTableInfos().getSmallBlindPlayer());
        assertEquals(player2, game.getTableInfos().getBigBlindPlayer());
        assertEquals(nouveau, game.getTableInfos().getCurrentPlayer());
        assertEquals(BLINDS, game.getStep());

        paySmallBlind(nouveau, game);
        payBigBlind(player2, game);
    }
}
