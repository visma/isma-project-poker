package org.isma.poker.game.actions;

import org.isma.poker.game.exceptions.InvalidPlayerBetException;
import org.isma.poker.game.model.Player;
import org.isma.poker.game.model.Table;
import org.isma.poker.game.step.InvalidStepActionException;
import org.isma.poker.game.step.PokerStepGame;

import static java.lang.String.format;
import static org.isma.poker.game.actions.PokerActionEnum.ALLIN;
import static org.isma.poker.game.exceptions.InvalidPlayerBetException.InvalidBetEnum.RAISE_FORBIDDEN;

public class AllinAction extends AbstractPlayerAction {
    public AllinAction(PokerStepGame gameSession, Table table) {
        super(ALLIN, gameSession, table);
    }

    @Override
    protected void doAction(Player player) throws InvalidPlayerBetException, InvalidStepActionException {
        int currentBet = table.getCurrentBet();
        int currentPaid = table.getCurrentStepBet(player);
        int additionalChips = player.getChips() - currentBet;
        int toPay = player.getChips();
        //additionalChips = additionalChips < 0 ? 0 : additionalChips;

        LOG.debug(format("currentBet : %s", currentBet));
        LOG.debug(format("currentPaid : %s", currentPaid));
        LOG.debug(format("additionalChips : %s", additionalChips));
        LOG.debug(format("toPay : %s", toPay));

        if (additionalChips > 0 && table.getRaisesRemaining() == 0) {
            throw new InvalidPlayerBetException(RAISE_FORBIDDEN);
        }
        int raiseCost = (currentBet - currentPaid) + additionalChips;
        LOG.debug(format("raiseCost : %s", raiseCost));
        if (raiseCost > 0) {
            table.decreaseRaiseRemainings();
        }
        PlayerAction.payChips(player, toPay);
        table.addToPot(player, toPay);
        if (additionalChips > 0) {
            table.addBet(additionalChips);
        }
    }
}
