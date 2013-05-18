package org.isma.poker.game.actions;

import org.isma.poker.game.event.ShowEvent;
import org.isma.poker.game.exceptions.InvalidPlayerBetException;
import org.isma.poker.game.model.Player;
import org.isma.poker.game.model.Table;
import org.isma.poker.game.step.InvalidStepActionException;
import org.isma.poker.game.step.PokerStepGame;

import static org.isma.poker.game.actions.PokerActionEnum.SHOW;

public class ShowAction extends AbstractPlayerAction {
    public ShowAction(PokerStepGame gameSession, Table table) {
        super(SHOW, gameSession, table);
    }

    @Override
    protected void doAction(Player player) throws InvalidPlayerBetException, InvalidStepActionException {
        gameSession.notify(new ShowEvent(player));
    }
}

