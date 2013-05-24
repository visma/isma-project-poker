package org.isma.poker.server;

import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocketHandler;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class PokerWebSocketHandler extends WebSocketHandler {
    private final Set<PokerWebSocket> webSockets = new CopyOnWriteArraySet<PokerWebSocket>();

    public PokerWebSocketHandler() {
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

        public void onMessage(String message) {
            try {
                System.out.println("onMessage:" + message);
                for (PokerWebSocket webSocket : webSockets) {
                    webSocket.connection.sendMessage(message);
                }
            } catch (IOException x) {
                x.printStackTrace();
                this.connection.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void onClose(int closeCode, String message) {
            webSockets.remove(this);
        }
    }
}
