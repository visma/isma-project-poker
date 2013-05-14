package org.isma.poker.game;

import org.isma.poker.game.factory.ITableFactory;
import org.isma.poker.game.factory.TableFactory;
import org.isma.poker.game.model.GameConfiguration;
import org.isma.poker.mock.MockDeck;
import org.isma.poker.mock.MockDeckFactory;
import org.isma.poker.model.FiftyTwoCardsEnum;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public abstract class AbstractPokerTest {
    private final ApplicationContext context;
    protected GameSession game;
    protected TableInfos tableInfos;
    protected MockDeck deck;


    protected AbstractPokerTest() {
        context = new ClassPathXmlApplicationContext(new String[]{"applicationContext-tu.xml"});
    }

    protected GameSession buildGame() {
        GameConfiguration gameConfiguration = buildGameConfiguration();
        MockDeckFactory deckFactory = buildDeckFactory();
        ITableFactory tableFactory = buildTableFactory();
        GameSession gameSession = new GameSession(gameConfiguration, deckFactory, tableFactory);
        gameSession.init(getPlayerAmount());
        return gameSession;
    }

    protected abstract int getPlayerAmount();

    protected ITableFactory buildTableFactory() {
        return new TableFactory();
    }

    private MockDeckFactory buildDeckFactory() {
        return (MockDeckFactory) context.getBean("deckFactory");
    }

    private GameConfiguration buildGameConfiguration() {
        return (GameConfiguration) context.getBean("gameConfiguration");
    }

    private Object getBean(String name) {
        return context.getBean(name);
    }

    protected void forceTurnOrRiver(FiftyTwoCardsEnum card) {
        deck.prepareCards(card);
    }

    protected void forceFlop(FiftyTwoCardsEnum card1, FiftyTwoCardsEnum card2, FiftyTwoCardsEnum card3) {
        deck.prepareCards(card1, card2, card3);
    }
}
