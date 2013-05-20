package org.isma.poker.game.actions;

import org.isma.poker.game.exceptions.InvalidPlayerBetException;
import org.isma.poker.game.model.Player;
import org.isma.poker.game.model.Table;
import org.isma.poker.game.step.InvalidStepActionException;
import org.isma.poker.game.step.PokerActionStepGame;

import static org.isma.poker.game.actions.PokerActionEnum.FOLD;

public class FoldAction extends AbstractPlayerAction {
    public FoldAction(PokerActionStepGame gameSession, Table table) {
        super(FOLD, gameSession, table);
    }

    @Override
    protected void doAction(Player player) throws InvalidPlayerBetException, InvalidStepActionException {
        table.removeFromRound(player);
    }
}
