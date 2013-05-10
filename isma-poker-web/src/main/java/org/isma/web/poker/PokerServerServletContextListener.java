package org.isma.web.poker;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.isma.poker.factory.DeckFactory;
import org.isma.poker.game.factory.TableFactory;
import org.isma.poker.game.model.GameConfiguration;
import org.isma.poker.game.GameSession;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class PokerServerServletContextListener implements ServletContextListener {
    private GameSession gameSession;
    private final PokerWebSocketHandler pokerWebSocketHandler;
    private Server server = null;

    public PokerServerServletContextListener() {
        doIoc();
        pokerWebSocketHandler = new PokerWebSocketHandler(gameSession);
    }

    private void doIoc() {
        ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"applicationContext.xml"});
        GameConfiguration gameConfiguration = (GameConfiguration)context.getBean("gameConfiguration");
        DeckFactory deckFactory = (DeckFactory)context.getBean("deckFactory");
        TableFactory tableFactory = (TableFactory)context.getBean("tableFactory");

        gameSession = new GameSession(gameConfiguration, deckFactory, tableFactory);
    }


    public void contextInitialized(ServletContextEvent event) {
        try {
            server = new Server(8081);
            pokerWebSocketHandler.setHandler(new DefaultHandler());
            server.setHandler(pokerWebSocketHandler);
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
