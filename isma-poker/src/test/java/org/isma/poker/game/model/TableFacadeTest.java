package org.isma.poker.game.model;

import org.isma.poker.game.Abstract2PlayersGameSessionTest;
import org.isma.poker.game.actions.PlayerAction;
import org.isma.poker.game.step.StepEnum;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class TableFacadeTest extends Abstract2PlayersGameSessionTest {

    @Test
    public void remaining_to_pay() throws Exception {
        gotoStep(StepEnum.BETS_2);
        PlayerAction.bet(player2, game, 60);
        PlayerAction.raise(player1, game, 25);
        assertEquals(25, tableFacade.getRemainingChipsToPay(player2));
        assertEquals(0, tableFacade.getRemainingChipsToPay(player1));
    }
}
