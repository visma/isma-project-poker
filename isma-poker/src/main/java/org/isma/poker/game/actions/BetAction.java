package org.isma.poker.game.actions;

import org.isma.poker.game.event.BetEvent;
import org.isma.poker.game.exceptions.InvalidPlayerBetException;
import org.isma.poker.game.model.Player;
import org.isma.poker.game.model.Table;
import org.isma.poker.game.step.InvalidStepActionException;
import org.isma.poker.game.step.PokerActionStepGame;

import static org.isma.poker.game.actions.PokerActionEnum.BET;
import static org.isma.poker.game.exceptions.InvalidPlayerBetException.InvalidBetEnum.*;

public class BetAction extends AbstractPlayerAction {
    private final int chips;
    private final int minimumBetAllowed;

    public BetAction(PokerActionStepGame game, Table table, int chips, int minimumBetAllowed) {
        super(BET, game, table);
        this.chips = chips;
        this.minimumBetAllowed = minimumBetAllowed;
    }

    @Override
    protected void doAction(Player player) throws InvalidPlayerBetException, InvalidStepActionException {
        game.notifyEvent(new BetEvent(player, chips));
        if (table.getCurrentBet() != 0) {
            throw new InvalidPlayerBetException(BET_FORBIDDEN);
        }
        if (chips < minimumBetAllowed) {
            throw new InvalidPlayerBetException(TO_CHEAP_BET);
        }
        if (player.getChips() < chips) {
            throw new InvalidPlayerBetException(NOT_ENOUGH_CHIPS);
        }
        PlayerAction.payChips(player, chips);
        table.addToPot(player, chips);
        table.addBet(chips);
    }
}
