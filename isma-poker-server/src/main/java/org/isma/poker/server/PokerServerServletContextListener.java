package org.isma.poker.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class PokerServerServletContextListener implements ServletContextListener {
    private static final int PORT = 8081;

    private final PokerWebSocketHandler pokerWebSocketHandler;
    private Server server = null;

    public PokerServerServletContextListener() {
        pokerWebSocketHandler = new PokerWebSocketHandler();
    }


    public void contextInitialized(ServletContextEvent event) {
        try {
            server = new Server(PORT);
            pokerWebSocketHandler.setHandler(new DefaultHandler());
            server.setHandler(pokerWebSocketHandler);
            server.getConnectors()[0].setMaxIdleTime(1000 * 60 * 30);
            server.start();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public void contextDestroyed(ServletContextEvent event) {
        if (server != null) {
            try {
                server.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
