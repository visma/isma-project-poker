package org.isma.poker.game.model;

import org.isma.poker.game.PlayerBetListener;
import org.isma.poker.game.actions.PlayerAction;
import org.isma.poker.game.model.Player;
import org.isma.poker.game.model.Table;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static junit.framework.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

public class TableTest {
    private Table table;
    public Player toto = new Player("toto");
    public Player tata = new Player("tata");
    public Player titi = new Player("titi");

    @Before
    public void setUp() throws Exception {
        table = new Table();
        PlayerBetListener game = Mockito.mock(PlayerBetListener.class);
        when(game.buy(any(Player.class), anyInt())).thenReturn(true);

        toto = new Player("toto");
        tata = new Player("tata");
        titi = new Player("titi");
        setUp_player(toto, game);
        setUp_player(tata, game);
        setUp_player(titi, game);
    }

    private void setUp_player(Player player, PlayerBetListener game) {
        PlayerAction.buyChips(player, game, 100);
        table.add(player);
    }

    @Test
    public void test_prepare_new_round_reset_fold_status() throws Exception {
        toto.setFold(true);
        titi.setFold(true);
        tata.setFold(true);

        table.prepareNewRound();

        assertFalse(toto.isFold());
        assertFalse(titi.isFold());
        assertFalse(tata.isFold());
    }
        @Test
    public void test_prepare_first_round_check_ingame_players() throws Exception {
        table.prepareNewRound();

        assertEquals(toto, table.getDealer());
        assertEquals(tata, table.getSmallBlindPlayer());
        assertEquals(titi, table.getBigBlindPlayer());
        assertEquals(toto, table.getCurrentPlayer());
    }

    @Test
    public void test_prepare_second_round_check_ingame_players() throws Exception {
        table.prepareNewRound();
        table.prepareNewRound();
        assertEquals(tata, table.getDealer());
        assertEquals(titi, table.getSmallBlindPlayer());
        assertEquals(toto, table.getBigBlindPlayer());
        assertEquals(tata, table.getCurrentPlayer());
    }

    @Test
    public void test_move_button_check_ingame_players() throws Exception {
        table.prepareNewRound();
        table.moveButton();
        assertEquals(tata, table.getDealer());
        assertEquals(titi, table.getSmallBlindPlayer());
        assertEquals(toto, table.getBigBlindPlayer());
        assertEquals(tata, table.getCurrentPlayer());
    }

    @Test
    public void test_prepare_next_player() throws Exception {
        table.prepareNewRound();
        assertEquals(toto, table.getCurrentPlayer());
        assertTrue(table.prepareNextPlayer());
        assertEquals(tata, table.getCurrentPlayer());
        assertTrue(table.prepareNextPlayer());
        assertEquals(titi, table.getCurrentPlayer());
        assertFalse(table.prepareNextPlayer());
    }

    @Test
    public void test_remove_folding_player() throws Exception {
        table.prepareNewRound();
        assertEquals(toto, table.getCurrentPlayer());
        table.removeFromRound(toto);
        table.prepareNextPlayer();
    }

}
