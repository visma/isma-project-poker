package org.isma.poker.game.model;

import org.isma.poker.game.actions.PlayerBetListener;
import org.isma.poker.game.actions.PlayerAction;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class PlayerTest {
    private PlayerBetListener game;
    private Player player;


    @Before
    public void setUp() throws Exception {
        game = Mockito.mock(PlayerBetListener.class);
        player = new Player("toto");
        when(game.buy(player, 100)).thenReturn(true);
        PlayerAction.buyChips(player, game, 100);
        assertEquals(100, player.getChips());
    }

    @Test
    public void buy_more_chips_ok() throws Exception {
        when(game.buy(player, 80)).thenReturn(true);
        PlayerAction.buyChips(player, game, 80);
        assertEquals(180, player.getChips());
    }

    @Test
    public void buy_more_chips_ko() throws Exception {
        when(game.buy(player, 80)).thenReturn(false);
        PlayerAction.buyChips(player, game, 80);
        assertEquals(100, player.getChips());
    }

    @Test
    public void bet_listener_plugged() throws Exception {
        PlayerAction.bet(player, game, 40);
        verify(game, times(1)).bet(player, 40);
    }

    @Test
    public void raise_listener_plugged() throws Exception {
        PlayerAction.raise(player, game, 50);
        verify(game, times(1)).raise(player, 50);
    }

    @Test
    public void call_listener_plugged() throws Exception {
        PlayerAction.call(player, game);
        verify(game, times(1)).call(player);
    }

    @Test
    public void check_listener_plugged() throws Exception {
        PlayerAction.check(player, game);
        verify(game, times(1)).check(player);
    }

    @Test
    public void fold() throws Exception {
        PlayerAction.fold(player, game);
        verify(game, times(1)).fold(player);
    }

}
