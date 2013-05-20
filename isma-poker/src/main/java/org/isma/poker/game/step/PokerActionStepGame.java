package org.isma.poker.game.step;

import org.isma.poker.game.event.GameEvent;
import org.isma.poker.game.model.TableInfos;

public interface PokerActionStepGame {
    TableInfos getTableInfos();

    Step getStep();

    void gotoEndStep() throws InvalidStepActionException;

    void finishStep() throws InvalidStepActionException;

    void notifyEvent(GameEvent event);
}
