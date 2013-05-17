package org.isma.poker.game;

import org.isma.poker.game.actions.PlayerAction;
import org.isma.poker.game.event.GameEventListener;
import org.isma.poker.game.model.Player;
import org.isma.poker.game.step.StepEnum;
import org.isma.poker.mock.MockDeck;
import org.junit.Before;

import static junit.framework.Assert.assertEquals;
import static org.isma.poker.game.step.StepEnum.*;
import static org.isma.poker.model.FiftyTwoCardsEnum.*;

public abstract class Abstract3PlayersGameSessionTest extends AbstractPokerTest {
    protected final Player player1 = new Player("p1");
    protected final Player player2 = new Player("p2");
    protected final Player player3 = new Player("p3");

    protected GameEventListener eventListener;

    @Override
    protected int getPlayerAmount() {
        return 3;
    }

    @Before
    public void setUp() throws Exception {
        eventListener = new GameEventListener();

        game = buildGame();
        game.addEventListener(eventListener);
        tableInfos = game.getTableInfos();
        deck = (MockDeck) game.getDeck();
        deckFactory.forceHands(EIGHT_OF_CLUBS, NINE_OF_CLUBS, TEN_OF_DIAMONDS, KNAVE_OF_DIAMONDS, TWO_OF_CLUBS, SEVEN_OF_HEARTS);

        PlayerAction.buyChips(player1, game, 100);
        PlayerAction.buyChips(player2, game, 80);
        PlayerAction.buyChips(player3, game, 60);
        PlayerAction.sitIn(player1, game);
        PlayerAction.sitIn(player2, game);
        PlayerAction.sitIn(player3, game);
    }

    protected void gotoStep(StepEnum step) throws Exception {
        assertEquals(5, tableInfos.getSmallBlindAmount());
        assertEquals(10, tableInfos.getBigBlindAmount());
        assertEquals(player1, tableInfos.getDealer());
        assertEquals(player2, tableInfos.getSmallBlindPlayer());
        assertEquals(player3, tableInfos.getBigBlindPlayer());
        assertEquals(tableInfos.getSmallBlindPlayer(), tableInfos.getCurrentPlayer());
        assertEquals(BLINDS, game.getStep());
        if (step.getOrder() > BLINDS.getOrder()) {
            PlayerAction.paySmallBlind(player2, game);
            PlayerAction.payBigBlind(player3, game);
            assertEquals(15, tableInfos.getTotalPot());
            assertEquals(BETS_1, game.getStep());
            assertEquals(player1, tableInfos.getCurrentPlayer());
        }
        if (step.getOrder() > BETS_1.getOrder()) {
            deckFactory.forceFlop(TEN_OF_SPADES, TWO_OF_HEARTS, THREE_OF_HEARTS);
            PlayerAction.call(player1, game);
            PlayerAction.call(player2, game);
            PlayerAction.check(player3, game);
            assertEquals(BETS_2, game.getStep());
        }
        if (step.getOrder() > BETS_2.getOrder()) {
            deckFactory.forceTurnOrRiver(KNAVE_OF_CLUBS);
            PlayerAction.check(player1, game);
            PlayerAction.check(player2, game);
            PlayerAction.check(player3, game);
            assertEquals(BETS_3, game.getStep());
        }
        if (step.getOrder() > BETS_3.getOrder()) {
            deckFactory.forceTurnOrRiver(QUEEN_OF_HEARTS);
            PlayerAction.check(player1, game);
            PlayerAction.check(player2, game);
            PlayerAction.check(player3, game);
            assertEquals(BETS_4, game.getStep());
        }
        if (step.getOrder() > BETS_4.getOrder()) {
            PlayerAction.check(player1, game);
            PlayerAction.check(player2, game);
            PlayerAction.check(player3, game);
            assertEquals(SHOWDOWN, game.getStep());
        }
        if (step.getOrder() > SHOWDOWN.getOrder()) {
            PlayerAction.show(player1, game);
            PlayerAction.show(player2, game);
            PlayerAction.show(player3, game);
            assertEquals(BLINDS, game.getStep());
        }
    }
}
