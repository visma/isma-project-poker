package org.isma.web.poker.actions;

import org.isma.poker.game.GameSession;
import org.isma.poker.game.model.TableFacade;
import org.isma.web.poker.messages.AbstractPokerAction;
import org.isma.web.poker.messages.response.PlayerStateResponse;

import java.util.Map;

public class PlayerStateAction extends AbstractPokerAction<TableFacade> {

    protected PlayerStateAction() {
        super(null, new PlayerStateResponse());
    }

    @Override
    protected String getErrorType() {
        return "PlayerStateError";
    }

    @Override
    protected void checkBefore(GameSession game, Map<String, String> messageMap) throws Exception {
    }

    @Override
    protected TableFacade execute(GameSession game, Map<String, String> messageMap) throws Exception {
        return game.getTableFacade();
    }
}
