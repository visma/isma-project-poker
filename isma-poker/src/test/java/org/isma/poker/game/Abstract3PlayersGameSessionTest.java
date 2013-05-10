package org.isma.poker.game;

import org.isma.poker.game.actions.PlayerAction;
import org.isma.poker.game.model.Player;
import org.isma.poker.game.step.StepEnum;
import org.isma.poker.mock.MockDeck;
import org.isma.poker.model.FiftyTwoCardsEnum;
import org.junit.Before;

import static junit.framework.Assert.assertEquals;
import static org.isma.poker.game.step.StepEnum.*;
import static org.isma.poker.model.FiftyTwoCardsEnum.*;

public abstract class Abstract3PlayersGameSessionTest extends AbstractPokerTest {
    protected final Player player1 = new Player("p1");
    protected final Player player2 = new Player("p2");
    protected final Player player3 = new Player("p3");

    @Before
    public void setUp() throws Exception {
        game = buildGame();
        tableInfos = game.getTableInfos();
        game.addPlayer(player1);
        game.addPlayer(player2);
        game.addPlayer(player3);
        PlayerAction.buyChips(player1, game, 100);
        PlayerAction.buyChips(player2, game, 80);
        PlayerAction.buyChips(player3, game, 60);
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
            forceHands(EIGHT_OF_CLUBS, NINE_OF_CLUBS, TEN_OF_DIAMONDS, KNAVE_OF_DIAMONDS, TWO_OF_CLUBS, SEVEN_OF_HEARTS);
            game.nextStep();
            assertEquals(player1, tableInfos.getDealer());
            assertEquals(player2, tableInfos.getSmallBlindPlayer());
            assertEquals(player3, tableInfos.getBigBlindPlayer());
            assertEquals(player1, tableInfos.getCurrentPlayer());
            assertEquals(HANDS_DEALING, game.getStep());
        }
        if (step.getOrder() > HANDS_DEALING.getOrder()) {
            game.nextStep();
            assertEquals(BETS_1, game.getStep());
        }
        if (step.getOrder() > BETS_1.getOrder()) {
            PlayerAction.call(player1, game);
            PlayerAction.call(player2, game);
            PlayerAction.check(player3, game);
            forceFlop(TEN_OF_SPADES, TWO_OF_HEARTS, THREE_OF_HEARTS);
            game.nextStep();
            assertEquals(FLOP, game.getStep());
        }
        if (step.getOrder() > FLOP.getOrder()) {
            game.nextStep();
            assertEquals(BETS_2, game.getStep());
        }
        if (step.getOrder() > BETS_2.getOrder()) {
            PlayerAction.check(player1, game);
            PlayerAction.check(player2, game);
            PlayerAction.check(player3, game);
            forceTurnOrRiver(KNAVE_OF_CLUBS);
            game.nextStep();
            assertEquals(TURN, game.getStep());
        }
        if (step.getOrder() > TURN.getOrder()) {
            game.nextStep();
            assertEquals(BETS_3, game.getStep());
        }
        if (step.getOrder() > BETS_3.getOrder()) {
            PlayerAction.check(player1, game);
            PlayerAction.check(player2, game);
            PlayerAction.check(player3, game);
            forceTurnOrRiver(QUEEN_OF_HEARTS);
            game.nextStep();
            assertEquals(RIVER, game.getStep());
        }
        if (step.getOrder() > RIVER.getOrder()) {
            game.nextStep();
            assertEquals(BETS_4, game.getStep());
        }
        if (step.getOrder() > BETS_4.getOrder()) {
            PlayerAction.check(player1, game);
            PlayerAction.check(player2, game);
            PlayerAction.check(player3, game);
            game.nextStep();
            assertEquals(SHOWDOWN, game.getStep());
        }
        if (step.getOrder() > SHOWDOWN.getOrder()) {
            PlayerAction.show(player1, game);
            PlayerAction.show(player2, game);
            PlayerAction.show(player3, game);
            game.nextStep();
            assertEquals(END, game.getStep());
        }
    }

    protected void forceHands(FiftyTwoCardsEnum player1Card1, FiftyTwoCardsEnum player1Card2,
                              FiftyTwoCardsEnum player2Card1, FiftyTwoCardsEnum player2Card2,
                              FiftyTwoCardsEnum player3Card1, FiftyTwoCardsEnum player3Card2) {
        deck.prepareCards(player1Card1, player2Card1, player3Card1, player1Card2, player2Card2, player3Card2);
    }


}
