package org.isma.poker.game;

import org.isma.poker.mock.MockDeck;
import org.isma.poker.model.FiftyTwoCardsEnum;
import org.junit.Before;

import static junit.framework.Assert.*;
import static org.isma.poker.game.StepEnum.*;
import static org.isma.poker.model.FiftyTwoCardsEnum.*;

public abstract class Abstract2PlayerGameSessionTest extends AbstractPokerTest {
    protected final Player player1 = new Player("p1");
    protected final Player player2 = new Player("p2");

    @Before
    public void setUp() throws Exception {
        game = buildGame();
        tableInfos = game.getTableInfos();
        game.addPlayer(player1);
        game.addPlayer(player2);
        player1.buyChips(game, 100);
        player2.buyChips(game, 100);
        game.start();
        deck = (MockDeck) game.getDeck();
    }

    protected void gotoStep(StepEnum step) throws Exception {
        assertEquals(5, tableInfos.getSmallBlindAmount());
        assertEquals(10, tableInfos.getBigBlindAmount());
        assertEquals(0, tableInfos.getTotalPot());
        assertEquals(END, game.getStep());
        game.nextStep();
        assertEquals(BLINDS, game.getStep());
        if (step.getOrder() > BLINDS.getOrder()) {
            forceHands(EIGHT_OF_CLUBS, NINE_OF_CLUBS, TEN_OF_DIAMONDS, KNAVE_OF_DIAMONDS);
            game.nextStep();
            assertEquals(HANDS_DEALING, game.getStep());
        }
        if (step.getOrder() > HANDS_DEALING.getOrder()) {
            game.nextStep();
            assertEquals(BETS_1, game.getStep());
        }
        if (step.getOrder() > BETS_1.getOrder()) {
            assertFalse(game.isStepOver());
            player2.call(game);
            player1.check(game);
            assertTrue(game.isStepOver());
            forceFlop(TEN_OF_SPADES, TWO_OF_HEARTS, THREE_OF_HEARTS);
            game.nextStep();
            assertEquals(FLOP, game.getStep());
        }
        if (step.getOrder() > FLOP.getOrder()) {
            game.nextStep();
            assertEquals(BETS_2, game.getStep());
        }
        if (step.getOrder() > BETS_2.getOrder()) {
            player2.check(game);
            player1.check(game);
            forceTurnOrRiver(KNAVE_OF_CLUBS);
            game.nextStep();
            assertEquals(TURN, game.getStep());
        }
        if (step.getOrder() > TURN.getOrder()) {
            game.nextStep();
            assertEquals(BETS_3, game.getStep());
        }
        if (step.getOrder() > BETS_3.getOrder()) {
            player2.check(game);
            player1.check(game);
            forceTurnOrRiver(QUEEN_OF_HEARTS);
            game.nextStep();
            assertEquals(RIVER, game.getStep());
        }
        if (step.getOrder() > RIVER.getOrder()) {
            game.nextStep();
            assertEquals(BETS_4, game.getStep());
        }
        if (step.getOrder() > BETS_4.getOrder()) {
            player2.check(game);
            player1.check(game);
            game.nextStep();
            assertEquals(SHOWDOWN, game.getStep());
        }
        if (step.getOrder() > SHOWDOWN.getOrder()) {
            player2.show(game);
            player1.show(game);
            game.nextStep();
            assertEquals(END, game.getStep());
        }
    }

    protected void forceHands(FiftyTwoCardsEnum player1Card1, FiftyTwoCardsEnum player1Card2,
                              FiftyTwoCardsEnum player2Card1, FiftyTwoCardsEnum player2Card2) {
        deck.prepareCards(player1Card1, player2Card1, player1Card2, player2Card2);
    }

}
