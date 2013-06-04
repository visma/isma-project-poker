package org.isma.poker.game;

import org.isma.poker.factory.DeckFactory;
import org.isma.poker.game.factory.TableFactory;
import org.isma.poker.game.model.GameConfiguration;
import org.isma.poker.game.model.Player;
import org.isma.poker.game.model.PlayerStatus;
import org.isma.poker.game.model.Table;
import org.isma.poker.game.step.InvalidStepActionException;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.isma.poker.game.model.PlayerStatus.*;
import static org.isma.poker.game.step.StepEnum.END;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GameSessionTest {
    private Table table;
    private GameSession game;
    private Player player;
    private GameStepRunner gameStepRunner;

    @Before
    public void setUp() throws Exception {
        //Given
        GameConfiguration conf = mock(GameConfiguration.class);
        TableFactory tableFactory = mock(TableFactory.class);
        IGameStepRunnerFactory runnerFactory = mock(IGameStepRunnerFactory.class);

        table = mock(Table.class);
        gameStepRunner = mock(GameStepRunner.class);

        when(tableFactory.buildTable()).thenReturn(table);
        when(runnerFactory.buildStepRunner(any(GameSession.class))).thenReturn(gameStepRunner);

        game = new GameSession(conf, new DeckFactory(), tableFactory, runnerFactory);
        game.init(2);
        player = new Player("lol");

    }

    @Test
    public void freeze() {
        //Given
        when(gameStepRunner.getStep()).thenReturn(END);

        //When
        boolean isFreeze = game.isFreeze();

        //Then
        assertTrue(isFreeze);
    }


    @Test
    public void player_status_out_of_cash() {
        //Given
        player.setChips(0);
        List<Player> allPlayers = new ArrayList<Player>();
        allPlayers.add(player);
        List<Player> alivePlayers = emptyList();
        when(table.getAllPlayers()).thenReturn(allPlayers);
        when(table.getAlivePlayers()).thenReturn(alivePlayers);

        //When
        PlayerStatus status = game.getStatus(player);

        //Then
        assertEquals(OUT_OF_CASH, status);
    }


    @Test
    public void player_status_waiting_end_of_current_round() {
        //Given
        player.setChips(10);
        List<Player> alivePlayers = emptyList();
        List<Player> allPlayers = new ArrayList<Player>();
        allPlayers.add(player);

        when(table.getAlivePlayers()).thenReturn(alivePlayers);
        when(table.getAllPlayers()).thenReturn(allPlayers);

        //When
        PlayerStatus status = game.getStatus(player);

        //Then
        assertEquals(WAITING, status);
    }

    @Test
    public void player_status_waiting_cause_game_is_frozen() throws InvalidStepActionException {
        //Given - set the game to the frozen state
        when(table.getCurrentPlayer()).thenReturn(null);
        when(gameStepRunner.getStep()).thenReturn(END);
        assertTrue(game.isFreeze());

        //When
        PlayerStatus status = game.getStatus(player);

        //Then
        assertEquals(WAITING, status);
    }

    @Test
    public void player_status_folded() {
        //Given
        player.setChips(10);
        player.setFold(true);
        List<Player> alivePlayers = new ArrayList<Player>();
        List<Player> allPlayers = new ArrayList<Player>();
        alivePlayers.add(player);
        allPlayers.add(player);

        when(table.getAlivePlayers()).thenReturn(alivePlayers);
        when(table.getAllPlayers()).thenReturn(allPlayers);

        //When
        PlayerStatus status = game.getStatus(player);

        //Then
        assertEquals(FOLDED, status);
    }

    @Test
    public void player_status_ingame() {
        //Given
        player.setChips(0);
        player.setFold(false);
        List<Player> alivePlayers = new ArrayList<Player>();
        List<Player> allPlayers = new ArrayList<Player>();
        alivePlayers.add(player);
        allPlayers.add(player);

        when(table.getAlivePlayers()).thenReturn(alivePlayers);
        when(table.getAllPlayers()).thenReturn(allPlayers);

        //When
        PlayerStatus status = game.getStatus(player);

        //Then
        assertEquals(INGAME, status);
    }

    @Test
    public void player_status_playing() {
        //Given
        player.setChips(10);
        player.setFold(false);
        List<Player> alivePlayers = new ArrayList<Player>();
        List<Player> allPlayers = new ArrayList<Player>();
        alivePlayers.add(player);
        allPlayers.add(player);

        when(table.getAlivePlayers()).thenReturn(alivePlayers);
        when(table.getAllPlayers()).thenReturn(allPlayers);
        when(table.getCurrentPlayer()).thenReturn(player);

        //When
        PlayerStatus status = game.getStatus(player);

        //Then
        assertEquals(PLAYING, status);
    }
}
