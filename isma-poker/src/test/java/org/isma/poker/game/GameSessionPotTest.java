package org.isma.poker.game;

import org.junit.Test;

import java.util.List;

import static java.lang.String.format;
import static junit.framework.Assert.assertEquals;
import static org.isma.poker.game.StepEnum.BETS_4;
import static org.isma.poker.game.StepEnum.SHOWDOWN;

public class GameSessionPotTest extends Abstract3PlayersGameSessionTest {


    @Test
    public void test_3Players_2SplitPots() throws Exception {
        gotoStep(BETS_4);
        int previousPot = 30;
        assertEquals(90, player1.getChips());
        assertEquals(70, player2.getChips());
        assertEquals(50, player3.getChips());
        assertEquals(3, tableInfos.getRaisesRemaining());
        assertCurrentBets(
                0, 0, 0,
                0, previousPot);

        //BET4 --> first bets
        player1.bet(20, game);
        assertEquals(70, player1.getChips());
        assertCurrentBets(
                20, 0, 0,
                20, 20 + previousPot);

        player2.call(game);
        assertEquals(50, player2.getChips());
        assertCurrentBets(
                20, 20, 0,
                20, 40 + previousPot);

        player3.raise(20, game);
        assertEquals(10, player3.getChips());
        assertEquals(2, tableInfos.getRaisesRemaining());
        assertCurrentBets(
                20, 20, 40,
                40, 80 + previousPot);


        //BET4 --> second bets (cause of a raise)
        player1.raise(20, game);
        assertEquals(1, tableInfos.getRaisesRemaining());
        assertEquals(30, player1.getChips());
        assertCurrentBets(
                60, 20, 40,
                60, 120 + previousPot);

        player2.call(game);
        assertEquals(10, player2.getChips());
        assertCurrentBets(
                60, 60, 40,
                60, 160 + previousPot);

        player3.allIn(game);
        assertEquals(0, player3.getChips());
        assertCurrentBets(
                60, 60, 50,
                60, 170 + previousPot);

        List<SplitPot> splitPots = game.getPot().buildSplitPots();
        assertEquals(2, splitPots.size());
        SplitPot firstPot = splitPots.get(0);
        SplitPot secondPot = splitPots.get(1);
        assertEquals(180, firstPot.getTotal());
        assertEquals(3, firstPot.getPlayers().size());
        assertEquals(20, secondPot.getTotal());
        assertEquals(2, secondPot.getPlayers().size());

        game.nextStep();
        assertEquals(SHOWDOWN, game.getStep());
    }

    private void assertCurrentBets(int expectedPlayer1CurrentBet,
                                   int expectedPlayer2CurrentBet,
                                   int expectedPlayer3CurrentBet,
                                   int currentBet,
                                   int totalPot) {
        assertCurrentBet(expectedPlayer1CurrentBet, player1);
        assertCurrentBet(expectedPlayer2CurrentBet, player2);
        assertCurrentBet(expectedPlayer3CurrentBet, player3);
        assertEquals(currentBet, tableInfos.getCurrentBet());
        assertEquals(totalPot, tableInfos.getTotalPot());
    }

    private void assertCurrentBet(int expectedPlayerCurrentBet, Player player) {
        int actualPlayerCurrentBet = tableInfos.getCurrentStepBet(player);
        String message = format("Player %s current bet - expected  : %s, actual : %s",
                player.getNickname(),
                expectedPlayerCurrentBet,
                actualPlayerCurrentBet);
        assertEquals(message, expectedPlayerCurrentBet, actualPlayerCurrentBet);
    }

}
