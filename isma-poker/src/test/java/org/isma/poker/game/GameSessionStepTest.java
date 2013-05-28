package org.isma.poker.game;

import org.isma.poker.game.actions.PlayerAction;
import org.isma.poker.game.event.RoundEndEvent;
import org.isma.poker.game.model.Loser;
import org.isma.poker.game.model.Winner;
import org.isma.poker.game.results.Results;
import org.isma.poker.model.Card;
import org.junit.Test;

import static junit.framework.Assert.*;
import static org.isma.poker.game.actions.PlayerAction.*;
import static org.isma.poker.game.step.StepEnum.*;
import static org.isma.poker.model.FiftyTwoCardsEnum.*;
import static org.isma.poker.model.HandEvaluation.STRAIGHT;
import static org.isma.poker.model.HandEvaluation.TWO_PAIR;

public class GameSessionStepTest extends Abstract2PlayersGameSessionTest {

    @Test
    public void two_players_nextPlayer() throws Exception {
        gotoStep(BETS_1);
        assertEquals(player2, tableFacade.getCurrentPlayer());
        assertEquals(player1, tableFacade.getNextBetPlayer(false));
        assertEquals(15, tableFacade.getTotalPot());
        call(player2, game);
        assertEquals(20, tableFacade.getTotalPot());
        //Player 1 check
        assertEquals(player1, tableFacade.getCurrentPlayer());
        assertEquals(player2, tableFacade.getNextBetPlayer(false));
        check(player1, game);
        assertEquals(BETS_2, game.getStep());
    }

    @Test
    public void two_players_step_blinds() throws Exception {
        gotoStep(BLINDS);
        //
        assertEquals(player1, tableFacade.getDealer());
        assertEquals(player2, tableFacade.getSmallBlindPlayer());
        assertEquals(player1, tableFacade.getBigBlindPlayer());

        assertEquals(0, tableFacade.getCurrentBet());
        PlayerAction.paySmallBlind(player2, game);
        assertEquals(5, tableFacade.getCurrentBet());
        PlayerAction.payBigBlind(player1, game);
        assertEquals(10, tableFacade.getCurrentBet());

        //----- ROUND 1 / BLINDS -----
        assertEquals(BETS_1, game.getStep());
        assertEquals(90, player1.getChips());
        assertEquals(95, player2.getChips());
        assertEquals(15, tableFacade.getTotalPot());
    }

    @Test
    public void two_players_step_handsDealing() throws Exception {
        gotoStep(HANDS_DEALING);
        assertEquals(BETS_1, game.getStep());
        assertEquals(2, player1.getHand().size());
        assertEquals(2, player2.getHand().size());

        assertEquals(EIGHT_OF_CLUBS.getCard(), player2.getHand().get(0));
        assertEquals(NINE_OF_CLUBS.getCard(), player2.getHand().get(1));

        assertEquals(TEN_OF_DIAMONDS.getCard(), player1.getHand().get(0));
        assertEquals(KNAVE_OF_DIAMONDS.getCard(), player1.getHand().get(1));

        assertTrue(tableFacade.getCommunityCards().isEmpty());
    }

    @Test
    public void two_players_step_bet1() throws Exception {
        gotoStep(BETS_1);
        // Player 2 call big blind
        assertEquals(player2, tableFacade.getCurrentPlayer());
        call(player2, game);
        assertEquals(90, player2.getChips());
        //Player 1 check
        assertEquals(player1, tableFacade.getCurrentPlayer());
        assertFalse(game.isStepOver());
        check(player1, game);
        assertEquals(BETS_2, game.getStep());
        assertEquals(90, player1.getChips());
        assertEquals(20, tableFacade.getTotalPot());
    }

    @Test
    public void two_players_step_bet1_multiples_raises() throws Exception {
        gotoStep(BETS_1);
        // Player 2 call big blind
        call(player2, game);
        assertEquals(90, player2.getChips());
        //Player 1 raise 1/3
        raise(player1, game, 10);
        //Player 2 raise 2/3
        raise(player2, game, 10);
        //Player 1 raise 3/3
        assertFalse(game.isStepOver());
        raise(player1, game, 10);
        call(player2, game);

        assertEquals(BETS_2, game.getStep());
        assertEquals(60, player1.getChips());
        assertEquals(60, player1.getChips());
        assertEquals(80, tableFacade.getTotalPot());
    }

    @Test
    public void two_players_step_flop() throws Exception {
        gotoStep(FLOP);
        assertEquals(BETS_2, game.getStep());
        assertEquals(3, tableFacade.getCommunityCards().size());
        assertEquals(5, player1.getHand().size());
        assertEquals(5, player2.getHand().size());

        Card flopCard1 = tableFacade.getCommunityCards().get(0);
        Card flopCard2 = tableFacade.getCommunityCards().get(1);
        Card flopCard3 = tableFacade.getCommunityCards().get(2);

        assertEquals("10 of Spades", flopCard1.toString());
        assertEquals("2 of Hearts", flopCard2.toString());
        assertEquals("3 of Hearts", flopCard3.toString());
        assertEquals(flopCard1, player1.getHand().get(2));
        assertEquals(flopCard1, player2.getHand().get(2));
        assertEquals(flopCard2, player1.getHand().get(3));
        assertEquals(flopCard2, player2.getHand().get(3));
        assertEquals(flopCard3, player1.getHand().get(4));
        assertEquals(flopCard3, player2.getHand().get(4));
    }

    @Test
    public void two_players_step_bet2() throws Exception {
        gotoStep(BETS_2);
        assertEquals(20, tableFacade.getTotalPot());
        //
        assertEquals(player1, tableFacade.getDealer());
        assertEquals(player2, tableFacade.getCurrentPlayer());
        //Player 2 check
        check(player2, game);
        assertEquals(BETS_2, game.getStep());
        assertEquals(90, player2.getChips());
        //Player 1 check
        assertEquals(player1, tableFacade.getCurrentPlayer());
        assertEquals(BETS_2, game.getStep());
        check(player1, game);
        assertEquals(90, player1.getChips());
        assertEquals(BETS_3, game.getStep());
        assertEquals(20, tableFacade.getTotalPot());
    }

    @Test
    public void two_players_step_turn() throws Exception {
        gotoStep(TURN);
        assertEquals(BETS_3, game.getStep());
        assertEquals(4, tableFacade.getCommunityCards().size());
        assertEquals(6, player1.getHand().size());
        assertEquals(6, player2.getHand().size());

        Card turn = tableFacade.getCommunityCards().get(3);

        assertEquals("Knave of Clubs", turn.toString());
        assertEquals(turn, player1.getHand().get(5));
        assertEquals(turn, player2.getHand().get(5));
    }

    @Test
    public void two_players_step_bet3() throws Exception {
        gotoStep(BETS_3);
        assertEquals(20, tableFacade.getTotalPot());
        //
        assertEquals(player1, tableFacade.getDealer());
        assertEquals(player2, tableFacade.getCurrentPlayer());

        //Player 2 check
        check(player2, game);
        assertEquals(90, player2.getChips());
        assertEquals(player1, tableFacade.getCurrentPlayer());
        assertEquals(BETS_3, game.getStep());

        //Player 1 check
        check(player1, game);
        assertEquals(90, player1.getChips());
        assertEquals(BETS_4, game.getStep());
        assertEquals(20, tableFacade.getTotalPot());
    }

    @Test
    public void two_players_step_river() throws Exception {
        gotoStep(RIVER);
        assertEquals(BETS_4, game.getStep());
        assertEquals(5, tableFacade.getCommunityCards().size());
        assertEquals(7, player1.getHand().size());
        assertEquals(7, player2.getHand().size());

        Card river = tableFacade.getCommunityCards().get(4);

        assertEquals("Queen of Hearts", river.toString());
        assertEquals(river, player1.getHand().get(6));
        assertEquals(river, player2.getHand().get(6));
    }

    @Test
    public void two_players_step_bet4() throws Exception {
        gotoStep(BETS_4);
        assertEquals(20, tableFacade.getTotalPot());
        //
        assertEquals(player1, tableFacade.getDealer());
        assertEquals(player2, tableFacade.getCurrentPlayer());

        //Player 2 check
        check(player2, game);
        assertEquals(90, player2.getChips());
        assertEquals(player1, tableFacade.getCurrentPlayer());
        assertEquals(BETS_4, game.getStep());

        //Player 1 check
        check(player1, game);
        assertEquals(90, player1.getChips());
        assertEquals(SHOWDOWN, game.getStep());
        assertEquals(20, tableFacade.getTotalPot());
    }

    @Test
    public void two_players_step_showdown() throws Exception {
        gotoStep(SHOWDOWN);
        assertEquals(player2, tableFacade.getCurrentPlayer());

        //
        show(player2, game);
        assertEquals(player1, tableFacade.getCurrentPlayer());
        assertEquals(SHOWDOWN, game.getStep());
        assertFalse(game.isRoundOver());
        while (eventListener.poll() != null) {
        }

        //
        show(player1, game);
        assertEquals(BLINDS, game.getStep());
        eventListener.poll();
        RoundEndEvent gameEvent = (RoundEndEvent) eventListener.poll();
        Results results = gameEvent.getResults();
        assertEquals(1, results.getWinners().size());
        Winner winner = results.getWinners().get(0);

        assertEquals(player2, winner.getPlayer());
        assertEquals(20, winner.getPrize());
        assertEquals(STRAIGHT, winner.getHandEvaluation());
        assertEquals(1, results.getLosers().size());

        Loser loser = results.getLosers().get(0);
        assertEquals(player1, loser.getPlayer());
        assertEquals(TWO_PAIR, loser.getHandEvaluation());

        assertEquals(110, player2.getChips());
        assertEquals(90, player1.getChips());
    }

    @Test
    public void two_players_second_round_start() throws Exception {
        //Given - go to 2nd round
        gotoStep(BETS_4);
        bet(player2, game, 10);
        call(player1, game);
        show(player2, game);
        show(player1, game);

        assertEquals(BLINDS, game.getStep());
        assertEquals(0, player1.getHand().size());
        assertEquals(0, player2.getHand().size());
        assertEquals(0, tableFacade.getCurrentBet());

        assertEquals(120, player2.getChips());
        assertEquals(80, player1.getChips());

        assertEquals(player2, tableFacade.getDealer());
        assertEquals(player1, tableFacade.getSmallBlindPlayer());
        assertEquals(player2, tableFacade.getBigBlindPlayer());
        assertEquals(0, tableFacade.getCurrentBet());

        //When
        paySmallBlind(player1, game);
        assertEquals(5, game.getTableFacade().getCurrentBet());
        payBigBlind(player2, game);
        assertEquals(10, game.getTableFacade().getCurrentBet());
        assertEquals(BETS_1, game.getStep());
        assertEquals(5, tableFacade.getRemainingChipsToPay(player1));
        call(player1, game);

        //Then
        assertEquals(110, player2.getChips());
        assertEquals(70, player1.getChips());
        assertEquals(20, tableFacade.getTotalPot());
    }

}
