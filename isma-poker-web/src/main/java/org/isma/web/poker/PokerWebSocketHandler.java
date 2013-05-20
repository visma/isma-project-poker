package org.isma.web.poker;

import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocketHandler;
import org.isma.poker.game.GameSession;
import org.isma.web.poker.messages.AbstractObjectMessageResponse;
import org.isma.web.poker.messages.AbstractPokerAction;
import org.isma.web.poker.messages.response.GameStateResponse;
import org.isma.web.poker.messages.response.PlayerStateResponse;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class PokerWebSocketHandler extends WebSocketHandler {
    private static final PlayerStateResponse PLAYER_STATE_MESSAGE_RESPONSE = new PlayerStateResponse();
    private static final GameStateResponse GAME_STATE_MESSAGE_RESPONSE = new GameStateResponse();

    private final Set<PokerWebSocket> webSockets = new CopyOnWriteArraySet<PokerWebSocket>();
    private final PokerGameSessionHandler gameSessionHandler;
    private final GameSession game;

    public PokerWebSocketHandler(GameSession game) {
        this.game = game;
        GAME_STATE_MESSAGE_RESPONSE.setObject(game);
        PLAYER_STATE_MESSAGE_RESPONSE.setObject(game.getTableFacade());
        gameSessionHandler = new PokerGameSessionHandler();
    }

    public WebSocket doWebSocketConnect(HttpServletRequest request, String protocol) {
        return new PokerWebSocket();
    }

    private class PokerWebSocket implements WebSocket.OnTextMessage {
        private Connection connection;

        public void onOpen(Connection connection) {
            this.connection = connection;
            webSockets.add(this);
        }

        //TODO recursif
        public void onMessage(String message) {
            try {
                AbstractPokerAction action = gameSessionHandler.getAction(message);
                AbstractObjectMessageResponse response = action.update(game, message);
                for (PokerWebSocket webSocket : webSockets) {
                    webSocket.connection.sendMessage(response.encode());
                    List<AbstractPokerAction> nextActions = action.nextActions(game);
                    for (AbstractPokerAction nextAction : nextActions) {
                        response = nextAction.update(game, message);
                        webSocket.connection.sendMessage(response.encode());
                    }
                    sendCurrentState(webSocket);
                }
            } catch (IOException x) {
                x.printStackTrace();
                this.connection.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void sendCurrentState(PokerWebSocket webSocket) throws IOException {
            webSocket.connection.sendMessage(GAME_STATE_MESSAGE_RESPONSE.encode());
            webSocket.connection.sendMessage(PLAYER_STATE_MESSAGE_RESPONSE.encode());
        }

        public void onClose(int closeCode, String message) {
            webSockets.remove(this);
        }
    }
}
