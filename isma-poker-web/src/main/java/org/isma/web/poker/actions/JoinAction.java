package org.isma.web.poker.actions;

import org.isma.poker.game.GameSession;
import org.isma.poker.game.actions.PlayerAction;
import org.isma.poker.game.exceptions.PokerGameException;
import org.isma.poker.game.model.Player;
import org.isma.poker.game.model.PlayerInfos;
import org.isma.web.poker.messages.AbstractPokerAction;
import org.isma.web.poker.messages.request.JoinMessageRequest;
import org.isma.web.poker.messages.response.JoinResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JoinAction extends AbstractPokerAction<String> {
    public JoinAction() {
        super(new JoinMessageRequest(), new JoinResponse());
    }


    protected String execute(GameSession game, Map<String, String> messageMap) throws PokerGameException {
        String nickname = messageMap.get(JoinMessageRequest.NICKNAME);
        PlayerAction.sitIn(new Player(nickname), game);
        return nickname;
    }


    protected void checkBefore(GameSession game, Map<String, String> messageMap) throws Exception {
        String nickname = messageMap.get(JoinMessageRequest.NICKNAME);
        checkMaxPlayers(game);
        checkUniqueNickName(game, nickname);
    }

    private void checkMaxPlayers(GameSession game) throws Exception {
        if (game.getTableFacade().isTableFull()) {
            throw new Exception("maximum players reached : " + game.getTableFacade().getMaxPlayers());
        }
    }


    private void checkUniqueNickName(GameSession game, String nickname) throws Exception {
        for (PlayerInfos playerInfos : game.getTableFacade().getPlayersInfos()) {
            if (playerInfos.getPlayer().getNickname().equals(nickname)) {
                throw new Exception("nickname already exists : " + nickname);
            }
        }
    }

    @Override
    protected String getErrorType() {
        return "loginError";
    }

    @Override
    public List<AbstractPokerAction> nextActions(GameSession game) {
        List<AbstractPokerAction> list = new ArrayList<AbstractPokerAction>();
        if (game.getTableFacade().isTableFull()) {
            list.add(new GameStartAction());
        }
        return list;
    }
}
