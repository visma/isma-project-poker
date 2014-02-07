package org.isma.poker.server;

import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocketHandler;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import static java.lang.String.format;
import static net.sf.json.JSONSerializer.toJSON;

public class PokerWebSocketHandler extends WebSocketHandler {
    private static final Logger logger = Logger.getLogger(PokerWebSocketHandler.class);

    private final Set<PokerWebSocket> webSockets = new CopyOnWriteArraySet<PokerWebSocket>();

    public PokerWebSocketHandler() {
    }

    public WebSocket doWebSocketConnect(HttpServletRequest request, String protocol) {
        String authCode = request.getParameter("authCode");
        logger.info(format("new socket (user : %s)", authCode));
        return new PokerWebSocket(authCode);
    }

    private class PokerWebSocket implements WebSocket.OnTextMessage {
        private final String authCode;
        private Connection connection;

        private PokerWebSocket(String authCode) {
            this.authCode = authCode;
        }

        public void onOpen(Connection connection) {
            this.connection = connection;
            webSockets.add(this);
        }

        public void onClose(int closeCode, String message) {
            this.connection = null;
            webSockets.remove(this);
        }

        public void onMessage(String message) {
            try {
                logger.info(format("onMessage : %s", message));
                JSONObject json = (JSONObject) toJSON(message);
                JSONObject object = json.getJSONObject("object");
                if (object.containsKey("target")) {
                    String authCode = object.getString("target");
                    logger.info(format("targetAuthCode : %s", authCode));
                    sendToTargetUser(authCode, message);
                } else {
                    sendToAllUsers(message);
                }
            } catch (IOException x) {
                x.printStackTrace();
                this.connection.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void sendToTargetUser(String authCode, String message) throws IOException {
            for (PokerWebSocket webSocket : webSockets) {
                if (authCode.equals(webSocket.authCode)) {
                    webSocket.connection.sendMessage(message);
                }
            }
        }

        private void sendToAllUsers(String message) throws IOException {
            for (PokerWebSocket webSocket : webSockets) {
                if (webSocket.authCode != null) {
                    webSocket.connection.sendMessage(message);
                }
            }
        }


    }
}
