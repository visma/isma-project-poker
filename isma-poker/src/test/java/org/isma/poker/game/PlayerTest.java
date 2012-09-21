package org.isma.poker.game;

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
        player.buyChips(game, 100);
        assertEquals(100, player.getChips());
    }

    @Test
    public void test_buy_more_chips_ok() throws Exception {
        when(game.buy(player, 80)).thenReturn(true);
        player.buyChips(game, 80);
        assertEquals(180, player.getChips());
    }

    @Test
    public void test_buy_more_chips_ko() throws Exception {
        when(game.buy(player, 80)).thenReturn(false);
        player.buyChips(game, 80);
        assertEquals(100, player.getChips());
    }

    @Test
    public void test_bet_listener_plugged() throws Exception {
        player.bet(40, game);
        verify(game, times(1)).bet(player, 40);
    }

    @Test
    public void test_raise_listener_plugged() throws Exception {
        player.raise(50, game);
        verify(game, times(1)).raise(player, 50);
    }

    @Test
    public void test_call_listener_plugged() throws Exception {
        player.call(game);
        verify(game, times(1)).call(player);
    }

    @Test
    public void test_check_listener_plugged() throws Exception {
        player.check(game);
        verify(game, times(1)).check(player);
    }

    @Test
    public void test_fold() throws Exception {
        player.fold(game);
        verify(game, times(1)).fold(player);
    }

}
