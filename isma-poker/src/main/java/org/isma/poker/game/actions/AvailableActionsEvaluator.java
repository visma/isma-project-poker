package org.isma.poker.game.actions;

import org.isma.poker.game.model.Player;
import org.isma.poker.game.model.TableFacade;
import org.isma.poker.game.step.PokerActionStepGame;

import java.util.List;

import static java.util.Arrays.asList;
import static org.isma.poker.game.actions.PokerActionEnum.*;
import static org.isma.poker.game.step.StepEnum.BLINDS;
import static org.isma.poker.game.step.StepEnum.SHOWDOWN;

public class AvailableActionsEvaluator {

    public List<PokerActionEnum> evaluate(PokerActionStepGame gameSession, Player player) {
        TableFacade tableFacade = gameSession.getTableFacade();

        if (player != tableFacade.getCurrentPlayer() || player.isFold()){
            return asList(SIT_OUT);
        }
        if (gameSession.getStep() == BLINDS) {
            if (player == tableFacade.getSmallBlindPlayer()) {
                return asList(SIT_OUT, PAY_SMALL_BLIND);
            }
            if (player == tableFacade.getBigBlindPlayer()) {
                return asList(SIT_OUT, PAY_BIG_BLIND);
            }
        }
        if (gameSession.getStep() == SHOWDOWN) {
            return asList(SIT_OUT, FOLD, SHOW);
        }
        if (tableFacade.getCurrentBet() == 0 && !player.hasChips()) {
            return asList(SIT_OUT, FOLD, CHECK);
        }
        if (tableFacade.getCurrentBet() == 0 && player.hasChips()) {
            return asList(SIT_OUT, FOLD, CHECK, BET, ALLIN);
        }
        int remainingChipsToPay = tableFacade.getRemainingChipsToPay(player);
        if (remainingChipsToPay >= player.getChips() && player.hasChips()) {
            return asList(SIT_OUT, FOLD, ALLIN);
        }
        if (remainingChipsToPay == 0) {
            return asList(SIT_OUT, FOLD, CHECK, RAISE, ALLIN);
        }
        return asList(SIT_OUT, FOLD, CALL, RAISE, ALLIN);
    }
}
