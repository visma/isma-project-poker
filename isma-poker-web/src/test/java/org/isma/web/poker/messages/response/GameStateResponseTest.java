package org.isma.web.poker.messages.response;

import org.isma.poker.game.GameSession;
import org.isma.poker.game.PlayerInfos;
import org.isma.poker.game.StepEnum;
import org.isma.poker.game.TableInfos;
import org.isma.web.poker.messages.PlayerInfosFactory;
import org.junit.Test;

import static java.util.Arrays.asList;
import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GameStateResponseTest {

    @Test
    public void test_toMessage() throws Exception {
        PlayerInfos toto = PlayerInfosFactory.buildPlayerInfo(0, "toto", 50);
        PlayerInfos tata = PlayerInfosFactory.buildPlayerInfo(1, "tata", 100);
        PlayerInfos titi = PlayerInfosFactory.buildPlayerInfo(2, "titi", 200);

        TableInfos mockTableInfos = mock(TableInfos.class);
        GameSession mockGame = mock(GameSession.class);
        when(mockTableInfos.getPlayersInfos()).thenReturn(asList(toto, tata, titi));
        when(mockTableInfos.getDealerInfos()).thenReturn(toto);
        when(mockTableInfos.getCurrentPlayerInfos()).thenReturn(titi);
        when(mockTableInfos.getTotalPot()).thenReturn(120);
        when(mockTableInfos.getSmallBlindAmount()).thenReturn(5);
        when(mockTableInfos.getBigBlindAmount()).thenReturn(10);
        when(mockGame.getTableInfos()).thenReturn(mockTableInfos);
        when(mockGame.getStep()).thenReturn(StepEnum.BETS_2);

        GameStateResponse encoder = new GameStateResponse();
        String expected = "GameState" +
                "{" +
                "smallBlind=5" +
                ",bigBlind=10" +
                ",pot=120" +
                ",step=BETS_2" +
                ",button=toto" +
                ",currentPlayer=titi" +
                "}";

        encoder.setObject(mockGame);
        assertEquals(expected, encoder.encode());

    }
}
