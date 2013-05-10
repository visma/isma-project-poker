package org.isma.web.poker;

import org.isma.poker.factory.DeckFactory;
import org.isma.poker.game.factory.ITableFactory;
import org.isma.poker.game.factory.TableFactory;
import org.isma.poker.game.model.GameConfiguration;
import org.isma.poker.game.GameSession;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public abstract class AbstractPokerTest {
    private final ApplicationContext context;
    protected GameSession game;


    protected AbstractPokerTest() {
        context = new ClassPathXmlApplicationContext(new String[]{"applicationContext-tu.xml"});
    }

    protected GameSession buildGame() {
        GameConfiguration gameConfiguration = buildGameConfiguration();
        DeckFactory deckFactory = buildDeckFactory();
        ITableFactory tableFactory = buildTableFactory();
        return new GameSession(gameConfiguration, deckFactory, tableFactory);
    }

    protected ITableFactory buildTableFactory() {
        return new TableFactory();
    }

    private DeckFactory buildDeckFactory() {
        return (DeckFactory) context.getBean("deckFactory");
    }

    private GameConfiguration buildGameConfiguration() {
        return (GameConfiguration) context.getBean("gameConfiguration");
    }
}