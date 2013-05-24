package org.isma.poker.rest.manager;

import org.isma.poker.factory.DeckFactory;
import org.isma.poker.game.GameSession;
import org.isma.poker.game.factory.TableFactory;
import org.isma.poker.game.model.GameConfiguration;
import org.isma.poker.rest.PokerEventDispatcher;
import org.isma.poker.rest.socket.PokerSocketClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoomManager {
    private PokerSocketClient socketClient;
    private GameSession gameSession;

    @Autowired
    public RoomManager(PokerSocketClient socketClient) throws Exception {
        this.socketClient = socketClient;
        createRoom(1);
    }

    public void createRoom(Integer roomId) throws Exception {
        GameConfiguration configuration = new GameConfiguration(5, 10, false, true);
        gameSession = new GameSession(configuration, new DeckFactory(), new TableFactory());
        gameSession.init(2);
        gameSession.addEventListener(new PokerEventDispatcher(socketClient, gameSession));
    }

    public GameSession getGame(Integer roomId) {
        return gameSession;
    }
}
