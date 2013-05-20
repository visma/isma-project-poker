package org.isma.web.poker.actions;

import org.isma.poker.game.GameSession;
import org.isma.poker.game.step.StepEnum;
import org.isma.web.poker.AbstractPokerTest;
import org.isma.web.poker.messages.AbstractPokerAction;
import org.isma.web.poker.messages.request.JoinMessageRequest;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.Assert.*;

public class JoinActionTest extends AbstractPokerTest {
    private GameSession game;

    @Before
    public void setUp() throws Exception {
        game = buildGame();
        assertEquals(2, game.getTableFacade().getMaxPlayers());
    }

    @Test
    public void testJoinFirstPlayer() throws Exception {
        JoinAction action = new JoinAction();
        Map<String, String> messageMap = new HashMap<String, String>();
        messageMap.put(JoinMessageRequest.NICKNAME, "toto");

        assertEquals(0, game.getTableFacade().getPlayersInfos().size());

        action.execute(game, messageMap);

        assertEquals(1, game.getTableFacade().getPlayersInfos().size());
        assertFalse(game.getTableFacade().isTableFull());
        assertTrue(action.nextActions(game).isEmpty());
        assertEquals(StepEnum.END, game.getStep());
    }

    @Test
    public void testJoinSecondAndLastPlayer() throws Exception {
        JoinAction action = new JoinAction();
        Map<String, String> messageMap = new HashMap<String, String>();

        messageMap.put(JoinMessageRequest.NICKNAME, "toto");
        action.execute(game, messageMap);
        messageMap.put(JoinMessageRequest.NICKNAME, "tata");
        action.execute(game, messageMap);

        assertEquals(2, game.getTableFacade().getPlayersInfos().size());
        assertTrue(game.getTableFacade().isTableFull());
        List<AbstractPokerAction> nextActions = action.nextActions(game);
        assertFalse(nextActions.isEmpty());
        assertEquals(GameStartAction.class, nextActions.get(0).getClass());
    }

}
