package org.isma.poker.game.actions;

import org.isma.poker.game.model.Player;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class PlayerActionTest {
    private PlayerBetListener game;
    private Player player;


    @Before
    public void setUp() throws Exception {
        game = mock(PlayerBetListener.class);
        player = new Player("toto");
    }

    @Test
    public void buy_listener_plugged() throws Exception {
        when(game.buy(player, 80)).thenReturn(true);
        PlayerAction.buyChips(player, game, 80);
        verify(game, times(1)).buy(player, 80);
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
    public void fold_listener_plugged() throws Exception {
        PlayerAction.fold(player, game);
        verify(game, times(1)).fold(player);
    }

}
