package org.isma.poker.game.step;

import org.isma.poker.game.event.GameEvent;
import org.isma.poker.game.model.TableFacade;

public interface PokerActionStepGame {
    TableFacade getTableFacade();

    Step getStep();

    void gotoEndStep() throws InvalidStepActionException;

    void finishStep() throws InvalidStepActionException;

    void notifyEvent(GameEvent event);

    /**
     * game is freeze if there is less than 2 players with chips
     */
    void freeze();
}
