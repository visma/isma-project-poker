package org.isma.poker.game;

import org.isma.poker.game.model.Player;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static org.isma.poker.game.actions.PlayerAction.*;
import static org.isma.poker.game.step.StepEnum.BLINDS;
import static org.isma.poker.game.step.StepEnum.END;

public class  GameSession2PlayersSitOutTest extends Abstract2PlayersGameSessionTest {
    @Test
    public void small_blind_sitout_and_new_challenger() throws Exception {
        //Given
        gotoStep(BLINDS);
        assertEquals(player2, tableFacade.getSmallBlindPlayer());
        assertEquals(player1, tableFacade.getBigBlindPlayer());

        //When
        sitOut(player2, game);

        //Then
        assertEquals(END, game.getStep());
        assertEquals(null, game.getTableFacade().getCurrentPlayer());

        Player nouveau = new Player("nouveau");
        nouveau.setChips(200);
        sitIn(nouveau, game);

        assertEquals(player1, game.getTableFacade().getDealer());
        assertEquals(nouveau, game.getTableFacade().getSmallBlindPlayer());
        assertEquals(player1, game.getTableFacade().getBigBlindPlayer());
        assertEquals(nouveau, game.getTableFacade().getCurrentPlayer());
        assertEquals(BLINDS, game.getStep());

        paySmallBlind(nouveau, game);
        payBigBlind(player1, game);
    }


    @Test
    public void big_blind_sitout_and_new_challenger() throws Exception {
        //Given
        gotoStep(BLINDS);
        assertEquals(player2, tableFacade.getSmallBlindPlayer());
        assertEquals(player1, tableFacade.getBigBlindPlayer());


        //When
        paySmallBlind(player2, game);
        sitOut(player1, game);

        //Then
        assertEquals(END, game.getStep());
        assertEquals(null, game.getTableFacade().getCurrentPlayer());
        assertEquals(100, player2.getChips());

        Player nouveau = new Player("nouveau");
        nouveau.setChips(200);
        sitIn(nouveau, game);

        assertEquals(player2, game.getTableFacade().getDealer());
        assertEquals(nouveau, game.getTableFacade().getSmallBlindPlayer());
        assertEquals(player2, game.getTableFacade().getBigBlindPlayer());
        assertEquals(nouveau, game.getTableFacade().getCurrentPlayer());
        assertEquals(BLINDS, game.getStep());

        paySmallBlind(nouveau, game);
        payBigBlind(player2, game);
    }
}
