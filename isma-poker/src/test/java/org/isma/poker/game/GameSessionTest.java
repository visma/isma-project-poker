package org.isma.poker.game;

import org.isma.poker.game.factory.ITableFactory;
import org.isma.poker.game.actions.PlayerAction;
import org.isma.poker.game.model.Player;
import org.isma.poker.game.model.Table;
import org.isma.poker.game.step.StepEnum;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static org.isma.poker.game.step.StepEnum.BLINDS;

public class GameSessionTest extends AbstractPokerTest {
    private TestPlayer player1 = new TestPlayer("toto");
    private TestPlayer player2 = new TestPlayer("titi");
    private boolean isRoundOverValue;

    @Before
    public void setUp() throws Exception {
        player1.clearChips();
        player2.clearChips();
    }

    @Override
    protected ITableFactory buildTableFactory() {
        ITableFactory tableFactory = Mockito.mock(ITableFactory.class);
        Table table = Mockito.mock(Table.class);
        Mockito.when(table.isRoundOver()).thenReturn(isRoundOverValue);
        Mockito.when(table.getSmallBlindPlayer()).thenReturn(player1);
        Mockito.when(table.getBigBlindPlayer()).thenReturn(player2);
        Mockito.when(tableFactory.buildTable()).thenReturn(table);
        return tableFactory;
    }

    @Test
    public void test_round_over() throws Exception {
        setUpWithRoundOverValue(true);
        assertFalse(game.isRoundOver());
        assertEquals(game.getStep(), StepEnum.END);
    }

    @Test
    public void test_round_not_over() throws Exception {
        setUpWithRoundOverValue(false);
        assertFalse(game.isRoundOver());
        assertEquals(BLINDS, game.getStep());
    }

    private void setUpWithRoundOverValue(boolean isRoundOverValue) throws Exception {
        this.isRoundOverValue = isRoundOverValue;
        game = buildGame();
        game.addPlayer(player1);
        game.addPlayer(player2);
        PlayerAction.buyChips(player1, game, 100);
        PlayerAction.buyChips(player2, game, 100);
        game.start();
        game.nextStep();

    }

    private class TestPlayer extends Player {
        public TestPlayer(String nickName) {
            super(nickName);
        }

        public void clearChips() {
            chips = 0;
        }
    }
}
