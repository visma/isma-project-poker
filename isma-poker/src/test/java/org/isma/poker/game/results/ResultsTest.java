package org.isma.poker.game.results;

import org.isma.poker.game.AbstractPokerTest;
import org.isma.poker.game.GameSession;
import org.isma.poker.game.Player;
import org.isma.poker.game.Pot;
import org.isma.poker.model.FiftyTwoCardsEnum;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static junit.framework.Assert.assertEquals;
import static org.isma.poker.HandEvaluationEnum.PAIR;
import static org.isma.poker.HandEvaluationEnum.STRAIGHT;
import static org.isma.poker.model.FiftyTwoCardsEnum.*;

public class ResultsTest extends AbstractPokerTest {
    private Player player1;
    private Player player2;
    private Player player3;

    @Before
    public void setUp() throws Exception {
        GameSession game = buildGame();
        player1 = new Player("toto");
        player2 = new Player("titi");
        player3 = new Player("tata");
        player1.buyChips(game, 100);
        player2.buyChips(game, 100);
        player3.buyChips(game, 90);
        player1.getHand().addAll(asList(QUEEN_OF_CLUBS, ACE_OF_CLUBS));
        player2.getHand().addAll(asList(KING_OF_CLUBS, KING_OF_DIAMONDS));
        player3.getHand().addAll(asList(FOUR_OF_SPADES, SIX_OF_DIAMONDS));
        List<FiftyTwoCardsEnum> communityCards = asList(THREE_OF_CLUBS, FIVE_OF_CLUBS, SEVEN_OF_DIAMONDS, NINE_OF_DIAMONDS, KNAVE_OF_HEARTS);
        player1.getHand().addAll(communityCards);
        player2.getHand().addAll(communityCards);
        player3.getHand().addAll(communityCards);
    }

    @Test
    public void test_winners() throws Exception {
        Results results = new Results(getPot(), getPlayers());

        assertEquals(2, results.getWinners().size());
        Winner winnerPlayer3 = results.getWinners().get(0);
        Winner winnerPlayer2 = results.getWinners().get(1);

        assertEquals(player3, winnerPlayer3.getPlayer());
        assertEquals(270, winnerPlayer3.getPrize());
        assertEquals(STRAIGHT, winnerPlayer3.getHandEvaluation());

        assertEquals(player2, winnerPlayer2.getPlayer());
        assertEquals(20, winnerPlayer2.getPrize());
        assertEquals(PAIR, winnerPlayer2.getHandEvaluation());
    }

    private List<Player> getPlayers() {
        return asList(player1, player2, player3);
    }

    private Pot getPot() {
        Pot pot = new Pot();
        pot.add(player1, 100);
        pot.add(player2, 100);
        pot.add(player3, 90);
        return pot;
    }
}
