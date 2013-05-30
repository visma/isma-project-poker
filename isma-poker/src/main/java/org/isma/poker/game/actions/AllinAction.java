package org.isma.poker.game.actions;

import org.isma.poker.game.exceptions.InvalidPlayerBetException;
import org.isma.poker.game.model.Player;
import org.isma.poker.game.model.Table;
import org.isma.poker.game.step.InvalidStepActionException;
import org.isma.poker.game.step.PokerActionStepGame;

import static java.lang.String.format;
import static org.isma.poker.game.actions.PokerActionEnum.ALLIN;
import static org.isma.poker.game.exceptions.InvalidPlayerBetException.InvalidBetEnum.RAISE_FORBIDDEN;

public class AllinAction extends AbstractPlayerAction {
    public AllinAction(PokerActionStepGame gameSession, Table table) {
        super(ALLIN, gameSession, table);
    }

    @Override
    protected void doAction(Player player) throws InvalidPlayerBetException, InvalidStepActionException {
        int currentTableBet = table.getCurrentBet();
        int currentPaid = table.getCurrentStepBet(player);
        int betIncreaseCost = player.getChips() - currentTableBet + currentPaid;
        int toPay = player.getChips();

        LOG.debug(format("currentBet : %s", currentTableBet));
        LOG.debug(format("currentPaid : %s", currentPaid));
        LOG.debug(format("betIncreaseCost : %s", betIncreaseCost));
        LOG.debug(format("toPay : %s", toPay));

        if (betIncreaseCost > 0 && table.getRaisesRemaining() == 0) {
            throw new InvalidPlayerBetException(RAISE_FORBIDDEN);
        }
        if (betIncreaseCost > 0) {
            table.decreaseRaiseRemainings();
        }
        PlayerAction.payChips(player, toPay);
        table.addToPot(player, toPay);
        if (betIncreaseCost > 0) {
            table.addBet(betIncreaseCost);
        }
    }
}
