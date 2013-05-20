package org.isma.web.poker.actions;

import org.isma.poker.game.GameSession;
import org.isma.poker.game.model.TableFacade;
import org.isma.web.poker.messages.AbstractPokerAction;
import org.isma.web.poker.messages.response.GameStateResponse;

import java.util.Map;

public class GameStartAction extends AbstractPokerAction<TableFacade> {
    protected GameStartAction() {
        super(null, new GameStateResponse());
    }

    @Override
    protected String getErrorType() {
        return "gameStateError";
    }

    @Override
    protected void checkBefore(GameSession game, Map<String, String> messageMap) throws Exception {
    }

    @Override
    protected TableFacade execute(GameSession game, Map<String, String> messageMap) throws Exception {
        game.init(3);
        return game.getTableFacade();
    }
}
