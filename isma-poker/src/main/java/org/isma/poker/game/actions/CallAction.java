package org.isma.poker.game.actions;

import org.isma.poker.game.event.CallEvent;
import org.isma.poker.game.exceptions.InvalidPlayerBetException;
import org.isma.poker.game.model.Player;
import org.isma.poker.game.model.Table;
import org.isma.poker.game.step.InvalidStepActionException;
import org.isma.poker.game.step.PokerStepGame;

import static org.isma.poker.game.actions.PokerActionEnum.CALL;

public class CallAction extends AbstractPlayerAction {
    public CallAction(PokerStepGame gameSession, Table table) {
        super(CALL, gameSession, table);
    }

    @Override
    protected void doAction(Player player) throws InvalidPlayerBetException, InvalidStepActionException {
        int currentPlayerBet = table.getCurrentStepBet(player);
        int remainingToPay = table.getCurrentBet() - currentPlayerBet;
        int paid = PlayerAction.payChips(player, remainingToPay);
        table.addToPot(player, paid);
        gameSession.notify(new CallEvent(player));
    }
}
