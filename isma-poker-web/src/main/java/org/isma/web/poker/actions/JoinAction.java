package org.isma.web.poker.actions;

import org.isma.poker.game.GameSession;
import org.isma.poker.game.Player;
import org.isma.poker.game.PlayerInfos;
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


    protected String execute(GameSession game, Map<String, String> messageMap) {
        String nickname = messageMap.get(JoinMessageRequest.NICKNAME);
        game.addPlayer(new Player(nickname));
        return nickname;
    }


    protected void checkBefore(GameSession game, Map<String, String> messageMap) throws Exception {
        String nickname = messageMap.get(JoinMessageRequest.NICKNAME);
        checkMaxPlayers(game);
        checkUniqueNickName(game, nickname);
    }

    private void checkMaxPlayers(GameSession game) throws Exception {
        if (game.getTableInfos().isTableFull()) {
            throw new Exception("maximum players reached : " + game.getTableInfos().getMaxPlayers());
        }
    }


    private void checkUniqueNickName(GameSession game, String nickname) throws Exception {
        for (PlayerInfos playerInfos : game.getTableInfos().getPlayersInfos()) {
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
        if (game.getTableInfos().isTableFull()) {
            list.add(new GameStartAction());
        }
        return list;
    }
}