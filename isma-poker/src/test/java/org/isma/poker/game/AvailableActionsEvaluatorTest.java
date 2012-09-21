package org.isma.poker.game;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.isma.poker.game.PokerActionEnum.*;
import static org.isma.poker.game.StepEnum.BETS_1;
import static org.isma.poker.game.StepEnum.BETS_2;
import static org.junit.Assert.assertEquals;

//TODO faire plein de cas de tests la
public class AvailableActionsEvaluatorTest extends Abstract3PlayersGameSessionTest {
    private AvailableActionsEvaluator evaluator = new AvailableActionsEvaluator();

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void test_evaluate_not_current_player() throws Exception {
        gotoStep(BETS_1);
        assertEquals(player1, tableInfos.getCurrentPlayer());
        List<PokerActionEnum> availablePokerActions = evaluator.evaluate(game, player2);
        assertEquals(1, availablePokerActions.size());
        assertEquals(SIT_OUT, availablePokerActions.get(0));
    }

    @Test
    public void test_evaluate_first_player_actions() throws Exception {
        gotoStep(BETS_1);
        assertEquals(player1, tableInfos.getCurrentPlayer());
        List<PokerActionEnum> availablePokerActions = evaluator.evaluate(game, player1);
        assertEquals(5, availablePokerActions.size());
        assertEquals(SIT_OUT, availablePokerActions.get(0));
        assertEquals(FOLD, availablePokerActions.get(1));
        assertEquals(CALL, availablePokerActions.get(2));
        assertEquals(RAISE, availablePokerActions.get(3));
        assertEquals(ALLIN, availablePokerActions.get(4));
    }

    @Test
    public void test_evaluate_small_blind_player_actions() throws Exception {
        gotoStep(BETS_1);
        player1.call(game);
        assertEquals(player2, tableInfos.getCurrentPlayer());
        List<PokerActionEnum> availablePokerActions = evaluator.evaluate(game, player2);
        assertEquals(5, availablePokerActions.size());
        assertEquals(SIT_OUT, availablePokerActions.get(0));
        assertEquals(FOLD, availablePokerActions.get(1));
        assertEquals(CALL, availablePokerActions.get(2));
        assertEquals(RAISE, availablePokerActions.get(3));
        assertEquals(ALLIN, availablePokerActions.get(4));
    }

    @Test
    public void test_evaluate_second_player_actions_case_no_bet_before() throws Exception {
        gotoStep(BETS_2);
        player1.check(game);
        assertEquals(player2, tableInfos.getCurrentPlayer());
        List<PokerActionEnum> availablePokerActions = evaluator.evaluate(game, player2);
        assertEquals(5, availablePokerActions.size());
        assertEquals(SIT_OUT, availablePokerActions.get(0));
        assertEquals(FOLD, availablePokerActions.get(1));
        assertEquals(CHECK, availablePokerActions.get(2));
        assertEquals(BET, availablePokerActions.get(3));
        assertEquals(ALLIN, availablePokerActions.get(4));
    }

    @Test
    public void test_evaluate_second_player_actions_case_bet_before() throws Exception {
        gotoStep(BETS_2);
        player1.bet(20, game);
        assertEquals(player2, tableInfos.getCurrentPlayer());
        List<PokerActionEnum> availablePokerActions = evaluator.evaluate(game, player2);
        assertEquals(5, availablePokerActions.size());
        assertEquals(SIT_OUT, availablePokerActions.get(0));
        assertEquals(FOLD, availablePokerActions.get(1));
        assertEquals(CALL, availablePokerActions.get(2));
        assertEquals(RAISE, availablePokerActions.get(3));
        assertEquals(ALLIN, availablePokerActions.get(4));
    }
}
