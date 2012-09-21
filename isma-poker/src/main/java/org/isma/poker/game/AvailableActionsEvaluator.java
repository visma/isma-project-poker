package org.isma.poker.game;

import java.util.List;

import static java.util.Arrays.asList;
import static org.isma.poker.game.PokerActionEnum.*;
import static org.isma.poker.game.StepEnum.SHOWDOWN;

public class AvailableActionsEvaluator {

    public List<PokerActionEnum> evaluate(GameSession gameSession, Player player) {
        TableInfos tableInfos = gameSession.getTableInfos();
        if (!player.hasChips()) {
            return asList(SIT_OUT);
        }
        if (tableInfos.getCurrentPlayer() != player) {
            return asList(SIT_OUT);
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
        if (remainingChipsToPay == 0){
            return asList(SIT_OUT, FOLD, CHECK, RAISE, ALLIN);
        }
        return asList(SIT_OUT, FOLD, CALL, RAISE, ALLIN);
    }
}
