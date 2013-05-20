package org.isma.poker.game.actions;

import org.isma.poker.game.Abstract3PlayersGameSessionTest;
import org.isma.poker.game.model.InvalidPlayerTurnException;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.isma.poker.game.actions.PlayerAction.*;
import static org.isma.poker.game.actions.PokerActionEnum.*;
import static org.isma.poker.game.step.StepEnum.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

//TODO faire PLEIN de cas de tests la
//TODO a tester mieux !!!
public class AvailableActionsEvaluatorTest extends Abstract3PlayersGameSessionTest {
    private AvailableActionsEvaluator evaluator = new AvailableActionsEvaluator();

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void no_raise_available_if_max_reached(){
        fail("todo");
    }

    @Test
    public void evaluate_showdown_not_fold_no_more_money() throws Exception {
        //Given
        gotoStep(BETS_4);
        check(player1, game);
        check(player2, game);

        //When
        allIn(player3, game);
        call(player1, game);
        call(player2, game);

        //Then - all players can show (even those with no money)
        assertEquals(SHOWDOWN, game.getStep());
        show(player1, game);
        show(player2, game);
        assertEquals(0, player3.getChips());
        show(player3, game);
    }

    @Test
    public void evaluate_small_blind_player() throws Exception {
        //Given/When
        gotoStep(BLINDS);
        assertEquals(player2, tableFacade.getSmallBlindPlayer());
        assertEquals(player2, tableFacade.getCurrentPlayer());

        //Then
        List<PokerActionEnum> availablePokerActions = evaluator.evaluate(game, player2);
        assertEquals(2, availablePokerActions.size());
        assertEquals(SIT_OUT, availablePokerActions.get(0));
        assertEquals(PAY_SMALL_BLIND, availablePokerActions.get(1));
        paySmallBlind(player2, game);
    }

    @Test
    public void evaluate_big_blind_player() throws Exception {
        gotoStep(BLINDS);
        assertEquals(player2, tableFacade.getSmallBlindPlayer());
        assertEquals(player3, tableFacade.getBigBlindPlayer());

        paySmallBlind(player2, game);
        assertEquals(player3, tableFacade.getCurrentPlayer());

        List<PokerActionEnum> availablePokerActions = evaluator.evaluate(game, player3);
        assertEquals(2, availablePokerActions.size());
        assertEquals(SIT_OUT, availablePokerActions.get(0));
        assertEquals(PAY_BIG_BLIND, availablePokerActions.get(1));
    }

    @Test(expected = InvalidPlayerTurnException.class)
    public void evaluate_no_current_small_blind_player() throws Exception {
        gotoStep(BLINDS);
        assertEquals(player2, tableFacade.getSmallBlindPlayer());
        assertEquals(player3, tableFacade.getBigBlindPlayer());
        List<PokerActionEnum> availablePokerActions = evaluator.evaluate(game, player3);
        assertEquals(1, availablePokerActions.size());
        assertEquals(SIT_OUT, availablePokerActions.get(0));
        PlayerAction.payBigBlind(player3, game);
    }


    @Test(expected = InvalidPlayerTurnException.class)
    public void evaluate_no_current_big_blind_player() throws Exception {
        gotoStep(BLINDS);
        assertEquals(player2, tableFacade.getSmallBlindPlayer());
        assertEquals(player3, tableFacade.getBigBlindPlayer());

        paySmallBlind(player2, game);
        PlayerAction.payBigBlind(player1, game);
    }

    @Test
    public void evaluate_not_current_player() throws Exception {
        gotoStep(BETS_1);
        assertEquals(player1, tableFacade.getCurrentPlayer());
        List<PokerActionEnum> availablePokerActions = evaluator.evaluate(game, player2);
        assertEquals(1, availablePokerActions.size());
        assertEquals(SIT_OUT, availablePokerActions.get(0));
    }

    @Test
    public void evaluate_first_player_actions() throws Exception {
        gotoStep(BETS_1);
        assertEquals(player1, tableFacade.getCurrentPlayer());
        List<PokerActionEnum> availablePokerActions = evaluator.evaluate(game, player1);
        assertEquals(5, availablePokerActions.size());
        assertEquals(SIT_OUT, availablePokerActions.get(0));
        assertEquals(FOLD, availablePokerActions.get(1));
        assertEquals(CALL, availablePokerActions.get(2));
        assertEquals(RAISE, availablePokerActions.get(3));
        assertEquals(ALLIN, availablePokerActions.get(4));
    }

    @Test
    public void evaluate_small_blind_player_actions() throws Exception {
        gotoStep(BETS_1);
        PlayerAction.call(player1, game);
        assertEquals(player2, tableFacade.getCurrentPlayer());
        List<PokerActionEnum> availablePokerActions = evaluator.evaluate(game, player2);
        assertEquals(5, availablePokerActions.size());
        assertEquals(SIT_OUT, availablePokerActions.get(0));
        assertEquals(FOLD, availablePokerActions.get(1));
        assertEquals(CALL, availablePokerActions.get(2));
        assertEquals(RAISE, availablePokerActions.get(3));
        assertEquals(ALLIN, availablePokerActions.get(4));
    }

    @Test
    public void evaluate_second_player_actions_case_no_bet_before() throws Exception {
        gotoStep(BETS_2);
        PlayerAction.check(player1, game);
        assertEquals(player2, tableFacade.getCurrentPlayer());
        List<PokerActionEnum> availablePokerActions = evaluator.evaluate(game, player2);
        assertEquals(5, availablePokerActions.size());
        assertEquals(SIT_OUT, availablePokerActions.get(0));
        assertEquals(FOLD, availablePokerActions.get(1));
        assertEquals(CHECK, availablePokerActions.get(2));
        assertEquals(BET, availablePokerActions.get(3));
        assertEquals(ALLIN, availablePokerActions.get(4));
    }

    @Test
    public void evaluate_second_player_actions_case_bet_before() throws Exception {
        gotoStep(BETS_2);
        PlayerAction.bet(player1, game, 20);
        assertEquals(player2, tableFacade.getCurrentPlayer());
        List<PokerActionEnum> availablePokerActions = evaluator.evaluate(game, player2);
        assertEquals(5, availablePokerActions.size());
        assertEquals(SIT_OUT, availablePokerActions.get(0));
        assertEquals(FOLD, availablePokerActions.get(1));
        assertEquals(CALL, availablePokerActions.get(2));
        assertEquals(RAISE, availablePokerActions.get(3));
        assertEquals(ALLIN, availablePokerActions.get(4));
    }
}
