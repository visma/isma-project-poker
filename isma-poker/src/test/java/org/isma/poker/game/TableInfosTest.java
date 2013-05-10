package org.isma.poker.game;

import org.isma.poker.game.actions.PlayerAction;
import org.isma.poker.game.step.StepEnum;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class TableInfosTest extends Abstract2PlayerGameSessionTest {

    @Test
    public void remaining_to_pay() throws Exception {
        gotoStep(StepEnum.BETS_2);
        PlayerAction.bet(player2, game, 60);
        PlayerAction.raise(player1, game, 25);
        assertEquals(25, tableInfos.getRemainingChipsToPay(player2));
        assertEquals(0, tableInfos.getRemainingChipsToPay(player1));
    }
}
