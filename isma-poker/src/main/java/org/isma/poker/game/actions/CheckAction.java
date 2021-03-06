package org.isma.poker.game.actions;

import org.isma.poker.game.event.CheckEvent;
import org.isma.poker.game.exceptions.InvalidPlayerBetException;
import org.isma.poker.game.model.Player;
import org.isma.poker.game.model.Table;
import org.isma.poker.game.step.InvalidStepActionException;
import org.isma.poker.game.step.PokerActionStepGame;

import static org.isma.poker.game.actions.PokerActionEnum.CHECK;
import static org.isma.poker.game.exceptions.InvalidPlayerBetException.InvalidBetEnum.CHECK_FORBIDDEN;

public class CheckAction extends AbstractPlayerAction {
    public CheckAction(PokerActionStepGame gameSession, Table table) {
        super(CHECK, gameSession, table);
    }

    @Override
    protected void doAction(Player player) throws InvalidPlayerBetException, InvalidStepActionException {
        int currentPlayerBet = table.getCurrentStepBet(player);
        int remainingToPay = table.getCurrentBet() - currentPlayerBet;
        if (remainingToPay != 0) {
            throw new InvalidPlayerBetException(CHECK_FORBIDDEN);
        }
        game.notifyEvent(new CheckEvent(player));
    }
}
