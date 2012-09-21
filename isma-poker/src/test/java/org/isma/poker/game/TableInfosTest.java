package org.isma.poker.game;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class TableInfosTest extends Abstract2PlayerGameSessionTest {

    @Test
    public void test_remaining_to_pay() throws Exception {
        gotoStep(StepEnum.BETS_2);
        player2.bet(60, game);
        player1.raise(25, game);
        assertEquals(25, tableInfos.getRemainingChipsToPay(player2));
        assertEquals(0, tableInfos.getRemainingChipsToPay(player1));
    }
}
