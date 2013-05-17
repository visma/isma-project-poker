package org.isma.poker.game;

import org.isma.poker.game.actions.PlayerAction;
import org.isma.poker.game.event.RoundEndEvent;
import org.isma.poker.game.model.Loser;
import org.isma.poker.game.model.Winner;
import org.isma.poker.game.results.Results;
import org.isma.poker.model.Card;
import org.junit.Test;

import static junit.framework.Assert.*;
import static org.isma.poker.game.step.StepEnum.*;
import static org.isma.poker.model.HandEvaluation.STRAIGHT;
import static org.isma.poker.model.HandEvaluation.TWO_PAIR;

public class GameSessionStepTest extends Abstract2PlayerGameSessionTest {

    @Test
    public void two_players_nextPlayer() throws Exception {
        gotoStep(BETS_1);
        assertEquals(player2, tableInfos.getCurrentPlayer());
        assertEquals(player1, tableInfos.getNextPlayer());
        assertEquals(15, tableInfos.getTotalPot());
        PlayerAction.call(player2, game);
        assertEquals(20, tableInfos.getTotalPot());
        //Player 1 check
        assertEquals(player1, tableInfos.getCurrentPlayer());
        assertEquals(player2, tableInfos.getNextPlayer());
        PlayerAction.check(player1, game);
        assertEquals(BETS_2, game.getStep());
    }

    @Test
    public void two_players_step_blinds() throws Exception {
        gotoStep(BLINDS);
        //
        assertEquals(player1, tableInfos.getDealer());
        assertEquals(player2, tableInfos.getSmallBlindPlayer());
        assertEquals(player1, tableInfos.getBigBlindPlayer());

        assertEquals(0, tableInfos.getCurrentBet());
        PlayerAction.paySmallBlind(player2, game);
        assertEquals(5, tableInfos.getCurrentBet());
        PlayerAction.payBigBlind(player1, game);
        assertEquals(10, tableInfos.getCurrentBet());

        //----- ROUND 1 / BLINDS -----
        assertEquals(BETS_1, game.getStep());
        assertEquals(90, player1.getChips());
        assertEquals(95, player2.getChips());
        assertEquals(15, tableInfos.getTotalPot());
    }

    @Test
    public void two_players_step_handsDealing() throws Exception {
        gotoStep(HANDS_DEALING);
        assertEquals(BETS_1, game.getStep());
        assertEquals(2, player1.getHand().size());
        assertEquals(2, player2.getHand().size());
        assertEquals("8 of Clubs", player1.getHand().get(0).toString());
        assertEquals("9 of Clubs", player1.getHand().get(1).toString());
        assertEquals("10 of Diamonds", player2.getHand().get(0).toString());
        assertEquals("Knave of Diamonds", player2.getHand().get(1).toString());
        assertTrue(tableInfos.getCommunityCards().isEmpty());
    }

    @Test
    public void two_players_step_bet1() throws Exception {
        gotoStep(BETS_1);
        // Player 2 call big blind
        assertEquals(player2, tableInfos.getCurrentPlayer());
        PlayerAction.call(player2, game);
        assertEquals(90, player2.getChips());
        //Player 1 check
        assertEquals(player1, tableInfos.getCurrentPlayer());
        assertFalse(game.isStepOver());
        PlayerAction.check(player1, game);
        assertEquals(BETS_2, game.getStep());
        assertEquals(90, player1.getChips());
        assertEquals(20, tableInfos.getTotalPot());
    }

    @Test
    public void two_players_step_bet1_multiples_raises() throws Exception {
        gotoStep(BETS_1);
        // Player 2 call big blind
        PlayerAction.call(player2, game);
        assertEquals(90, player2.getChips());
        //Player 1 raise 1/3
        PlayerAction.raise(player1, game, 10);
        //Player 2 raise 2/3
        PlayerAction.raise(player2, game, 10);
        //Player 1 raise 3/3
        assertFalse(game.isStepOver());
        PlayerAction.raise(player1, game, 10);
        PlayerAction.call(player2, game);

        assertEquals(BETS_2, game.getStep());
        assertEquals(60, player1.getChips());
        assertEquals(60, player1.getChips());
        assertEquals(80, tableInfos.getTotalPot());
    }

    @Test
    public void two_players_step_flop() throws Exception {
        gotoStep(FLOP);
        assertEquals(BETS_2, game.getStep());
        assertEquals(3, tableInfos.getCommunityCards().size());
        assertEquals(5, player1.getHand().size());
        assertEquals(5, player2.getHand().size());

        Card flopCard1 = tableInfos.getCommunityCards().get(0);
        Card flopCard2 = tableInfos.getCommunityCards().get(1);
        Card flopCard3 = tableInfos.getCommunityCards().get(2);

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
        assertEquals(20, tableInfos.getTotalPot());
        //
        assertEquals(player1, tableInfos.getDealer());
        assertEquals(player2, tableInfos.getCurrentPlayer());
        //Player 2 check
        PlayerAction.check(player2, game);
        assertEquals(BETS_2, game.getStep());
        assertEquals(90, player2.getChips());
        //Player 1 check
        assertEquals(player1, tableInfos.getCurrentPlayer());
        assertEquals(BETS_2, game.getStep());
        PlayerAction.check(player1, game);
        assertEquals(90, player1.getChips());
        assertEquals(BETS_3, game.getStep());
        assertEquals(20, tableInfos.getTotalPot());
    }

    @Test
    public void two_players_step_turn() throws Exception {
        gotoStep(TURN);
        assertEquals(BETS_3, game.getStep());
        assertEquals(4, tableInfos.getCommunityCards().size());
        assertEquals(6, player1.getHand().size());
        assertEquals(6, player2.getHand().size());

        Card turn = tableInfos.getCommunityCards().get(3);

        assertEquals("Knave of Clubs", turn.toString());
        assertEquals(turn, player1.getHand().get(5));
        assertEquals(turn, player2.getHand().get(5));
    }

    @Test
    public void two_players_step_bet3() throws Exception {
        gotoStep(BETS_3);
        assertEquals(20, tableInfos.getTotalPot());
        //
        assertEquals(player1, tableInfos.getDealer());
        assertEquals(player2, tableInfos.getCurrentPlayer());

        //Player 2 check
        PlayerAction.check(player2, game);
        assertEquals(90, player2.getChips());
        assertEquals(player1, tableInfos.getCurrentPlayer());
        assertEquals(BETS_3, game.getStep());

        //Player 1 check
        PlayerAction.check(player1, game);
        assertEquals(90, player1.getChips());
        assertEquals(BETS_4, game.getStep());
        assertEquals(20, tableInfos.getTotalPot());
    }

    @Test
    public void two_players_step_river() throws Exception {
        gotoStep(RIVER);
        assertEquals(BETS_4, game.getStep());
        assertEquals(5, tableInfos.getCommunityCards().size());
        assertEquals(7, player1.getHand().size());
        assertEquals(7, player2.getHand().size());

        Card river = tableInfos.getCommunityCards().get(4);

        assertEquals("Queen of Hearts", river.toString());
        assertEquals(river, player1.getHand().get(6));
        assertEquals(river, player2.getHand().get(6));
    }

    @Test
    public void two_players_step_bet4() throws Exception {
        gotoStep(BETS_4);
        assertEquals(20, tableInfos.getTotalPot());
        //
        assertEquals(player1, tableInfos.getDealer());
        assertEquals(player2, tableInfos.getCurrentPlayer());

        //Player 2 check
        PlayerAction.check(player2, game);
        assertEquals(90, player2.getChips());
        assertEquals(player1, tableInfos.getCurrentPlayer());
        assertEquals(BETS_4, game.getStep());

        //Player 1 check
        PlayerAction.check(player1, game);
        assertEquals(90, player1.getChips());
        assertEquals(SHOWDOWN, game.getStep());
        assertEquals(20, tableInfos.getTotalPot());
    }

    @Test
    public void two_players_step_showdown() throws Exception {
        gotoStep(SHOWDOWN);
        assertEquals(player2, tableInfos.getCurrentPlayer());

        //
        PlayerAction.show(player2, game);
        assertEquals(player1, tableInfos.getCurrentPlayer());
        assertEquals(SHOWDOWN, game.getStep());
        assertFalse(game.isRoundOver());
        while (eventListener.poll() != null){
        }

        //
        PlayerAction.show(player1, game);
        assertEquals(BLINDS, game.getStep());
        eventListener.poll();
        RoundEndEvent gameEvent = (RoundEndEvent) eventListener.poll();
        Results results = gameEvent.getResults();
        assertEquals(1, results.getWinners().size());
        Winner winner = results.getWinners().get(0);
        assertEquals(player1, winner.getPlayer());
        assertEquals(20, winner.getPrize());
        assertEquals(STRAIGHT, winner.getHandEvaluation());
        assertEquals(1, results.getLosers().size());
        Loser loser = results.getLosers().get(0);
        assertEquals(player2, loser.getPlayer());
        assertEquals(TWO_PAIR, loser.getHandEvaluation());
        assertEquals(110, player1.getChips());
        assertEquals(90, player2.getChips());
    }

    @Test
    public void two_players_second_round_start() throws Exception {
        gotoStep(END);

        assertEquals(BLINDS, game.getStep());
        assertEquals(0, player1.getHand().size());
        assertEquals(0, player2.getHand().size());
        assertEquals(110, player1.getChips());
        assertEquals(90, player2.getChips());
        assertEquals(player2, tableInfos.getDealer());
        assertEquals(player1, tableInfos.getSmallBlindPlayer());
        assertEquals(player2, tableInfos.getBigBlindPlayer());
        assertEquals(tableInfos.getBigBlindAmount(), tableInfos.getCurrentBet());
        //----- ROUND 1 / BLINDS -----
        assertEquals(105, player1.getChips());
        assertEquals(80, player2.getChips());
        assertEquals(15, tableInfos.getTotalPot());
    }

}
