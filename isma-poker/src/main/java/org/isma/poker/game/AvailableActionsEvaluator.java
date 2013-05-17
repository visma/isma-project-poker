package org.isma.poker.game;

import org.isma.poker.game.model.Player;

import java.util.List;

import static java.util.Arrays.asList;
import static org.isma.poker.game.PokerActionEnum.*;
import static org.isma.poker.game.step.StepEnum.BLINDS;
import static org.isma.poker.game.step.StepEnum.SHOWDOWN;

public class AvailableActionsEvaluator {

    public List<PokerActionEnum> evaluate(PokerGameState gameSession, Player player) {
        TableInfos tableInfos = gameSession.getTableInfos();

        if (player != tableInfos.getCurrentPlayer() || player.isFold()){
            return asList(SIT_OUT);
        }
        if (gameSession.getStep() == BLINDS) {
            if (player == tableInfos.getSmallBlindPlayer()) {
                return asList(SIT_OUT, PAY_SMALL_BLIND);
            }
            if (player == tableInfos.getBigBlindPlayer()) {
                return asList(SIT_OUT, PAY_BIG_BLIND);
            }
        }
        if (gameSession.getStep() == SHOWDOWN) {
            return asList(SIT_OUT, FOLD, SHOW);
        }
        if (tableInfos.getCurrentBet() == 0 && !player.hasChips()) {
            return asList(SIT_OUT, FOLD, CHECK);
        }
        if (tableInfos.getCurrentBet() == 0 && player.hasChips()) {
            return asList(SIT_OUT, FOLD, CHECK, BET, ALLIN);
        }
        int remainingChipsToPay = tableInfos.getRemainingChipsToPay(player);
        if (remainingChipsToPay >= player.getChips()) {
            return asList(SIT_OUT, FOLD, ALLIN);
        }
        if (remainingChipsToPay == 0) {
            return asList(SIT_OUT, FOLD, CHECK, RAISE, ALLIN);
        }
        return asList(SIT_OUT, FOLD, CALL, RAISE, ALLIN);
    }
}
