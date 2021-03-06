package org.isma.poker.game.actions;

import org.isma.poker.game.event.RaiseEvent;
import org.isma.poker.game.exceptions.InvalidPlayerBetException;
import org.isma.poker.game.model.Player;
import org.isma.poker.game.model.Table;
import org.isma.poker.game.step.InvalidStepActionException;
import org.isma.poker.game.step.PokerActionStepGame;

import static org.isma.poker.game.actions.PokerActionEnum.RAISE;
import static org.isma.poker.game.exceptions.InvalidPlayerBetException.InvalidBetEnum.*;

public class RaiseAction extends AbstractPlayerAction {
    private final int additionalChips;
    private final int minBetAllowed;

    public RaiseAction(PokerActionStepGame gameSession, Table table, int additionalChips, int minBetAllowed) {
        super(RAISE, gameSession, table);
        this.additionalChips = additionalChips;
        this.minBetAllowed = minBetAllowed;
    }

    @Override
    protected void doAction(Player player) throws InvalidPlayerBetException, InvalidStepActionException {
        int currentBet = table.getCurrentBet();
        int currentPaid = table.getCurrentStepBet(player);
        int raiseCost = (currentBet - currentPaid) + additionalChips;

        checkLegalRaiseMove(player, currentBet, raiseCost);

        table.decreaseRaiseRemainings();
        PlayerAction.payChips(player, raiseCost);
        table.addToPot(player, raiseCost);
        table.addBet(additionalChips);

        game.notifyEvent(new RaiseEvent(player, additionalChips));
    }

    private void checkLegalRaiseMove(Player player, int currentBet, int raiseCost) throws InvalidPlayerBetException {
        if (table.getRaisesRemaining() == 0) {
            throw new InvalidPlayerBetException(RAISE_FORBIDDEN);
        }
        if (currentBet == 0) {
            throw new InvalidPlayerBetException(RAISE_FORBIDDEN);
        }
        if (additionalChips < minBetAllowed) {
            throw new InvalidPlayerBetException(TO_CHEAP_BET);
        }
        if (player.getChips() < raiseCost) {
            throw new InvalidPlayerBetException(NOT_ENOUGH_CHIPS);
        }
    }
}
