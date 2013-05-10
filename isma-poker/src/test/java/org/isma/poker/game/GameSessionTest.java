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
    private Player player1 = new Player("toto");
    private Player player2 = new Player("titi");
    private boolean isRoundOverValue;

    @Before
    public void setUp() throws Exception {
        player1.setChips(0);
        player2.setChips(0);
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
    public void round_over() throws Exception {
        setUpWithRoundOverValue(true);
        assertFalse(game.isRoundOver());
        assertEquals(game.getStep(), StepEnum.END);
    }

    @Test
    public void round_not_over() throws Exception {
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

}
