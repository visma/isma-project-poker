package org.isma.poker.rest.socket;

import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocketClient;
import org.eclipse.jetty.websocket.WebSocketClientFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.TimeUnit;

import static java.lang.String.format;

@Component
public class PokerSocketClient {
    private static final String URL = "ws://localhost:8081/";
    private boolean connected = false;
    private WebSocket.Connection connection;

    public PokerSocketClient() throws Exception {
    }

    public void connect() throws Exception {
        if (connected) {
            return;
        }
        WebSocketClientFactory factory = new WebSocketClientFactory();
        factory.start();

        WebSocketClient client = factory.newWebSocketClient();
        connection = client.open(new URI(URL), new WebSocket.OnTextMessage() {
            public void onOpen(Connection connection) {
            }

            public void onClose(int closeCode, String message) {
                System.out.println(format("onClose(%s, %s)", closeCode, message));
            }

            public void onMessage(String data) {
                //No websocket client messages : clients use REST instead
            }
        }).get(5, TimeUnit.SECONDS);
        connected = true;
    }

    public void send(String message) throws IOException {
        connection.sendMessage(message);
    }
}
