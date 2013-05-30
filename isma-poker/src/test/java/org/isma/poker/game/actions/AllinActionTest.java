package org.isma.poker.game.actions;

import org.isma.poker.game.GameSession;
import org.isma.poker.game.exceptions.InvalidPlayerBetException;
import org.isma.poker.game.exceptions.PokerGameException;
import org.isma.poker.game.model.GameConfiguration;
import org.isma.poker.game.model.Player;
import org.isma.poker.game.model.Table;
import org.isma.poker.game.model.TableFacade;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

import static junit.framework.Assert.assertFalse;
import static org.mockito.Mockito.*;

public class AllinActionTest {
    private GameSession game;
    private Table table;
    private GameConfiguration conf;
    private Player player;

    @Before
    public void setUp() throws Exception {
        game = mock(GameSession.class);
        table = mock(Table.class);
        conf = mock(GameConfiguration.class);
        player = new Player("toto");

        when(game.getTableFacade()).thenReturn(new TableFacade(table, conf));

    }

    @Test
    public void no_bet_before() throws PokerGameException {
        //Given
        player.setChips(80);

        when(table.getRaisesRemaining()).thenReturn(1);
        when(table.getCurrentBet()).thenReturn(0);
        when(table.getCurrentStepBet(player)).thenReturn(0);
        when(table.getCurrentPlayer()).thenReturn(player);

        //Then
        new AllinAction(game, table).execute(player);

        //When
        assertFalse(player.hasChips());
        verify(table, times(1)).decreaseRaiseRemainings();
        verify(table, times(1)).addToPot(player, 80);
        verify(table, times(1)).addBet(80);
    }

    @Test
    public void another_player_bet_before() throws PokerGameException {
        //Given
        player.setChips(80);
        when(table.getRaisesRemaining()).thenReturn(1);
        when(table.getCurrentBet()).thenReturn(10);
        when(table.getCurrentStepBet(player)).thenReturn(0);
        when(table.getCurrentPlayer()).thenReturn(player);

        //Then
        new AllinAction(game, table).execute(player);

        //When
        assertFalse(player.hasChips());
        verify(table, times(1)).decreaseRaiseRemainings();
        verify(table, times(1)).addToPot(player, 80);
        verify(table, times(1)).addBet(70);
    }


    @Test
    public void small_blind_do_allin() throws PokerGameException {
        //Given
        player.setChips(95);
        when(table.getRaisesRemaining()).thenReturn(1);
        when(table.getCurrentBet()).thenReturn(10);
        when(table.getCurrentStepBet(player)).thenReturn(5);
        when(table.getCurrentPlayer()).thenReturn(player);

        //Then
        new AllinAction(game, table).execute(player);

        //When
        assertFalse(player.hasChips());
        verify(table, times(1)).decreaseRaiseRemainings();
        verify(table, times(1)).addToPot(player, 95);
        verify(table, times(1)).addBet(90);
    }


    @Test
    public void allin_cheaper_than_currentTableBet() throws PokerGameException {
        //Given
        player.setChips(80);

        when(table.getRaisesRemaining()).thenReturn(1);
        when(table.getCurrentBet()).thenReturn(90);
        when(table.getCurrentStepBet(player)).thenReturn(0);
        when(table.getCurrentPlayer()).thenReturn(player);

        //Then
        new AllinAction(game, table).execute(player);

        //When
        assertFalse(player.hasChips());
        verify(table, times(0)).decreaseRaiseRemainings();
        verify(table, times(1)).addToPot(player, 80);
        verify(table, times(0)).addBet(Mockito.anyInt());
    }

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Test
    public void allin_forbidden_max_raises_reached() throws PokerGameException {
        //Given
        player.setChips(80);

        when(table.getRaisesRemaining()).thenReturn(0);
        when(table.getCurrentBet()).thenReturn(30);
        when(table.getCurrentStepBet(player)).thenReturn(0);
        when(table.getCurrentPlayer()).thenReturn(player);

        expectedEx.expect(InvalidPlayerBetException.class);
        expectedEx.expectMessage("RAISE_FORBIDDEN");

        //Then
        new AllinAction(game, table).execute(player);
    }
}
