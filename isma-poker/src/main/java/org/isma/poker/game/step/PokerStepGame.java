package org.isma.poker.game.step;

import org.isma.poker.game.event.GameEvent;
import org.isma.poker.game.exceptions.PokerGameException;
import org.isma.poker.game.model.TableInfos;

public interface PokerStepGame {
    void beginRoundIfPossible() throws InvalidStepActionException;

    void nextStep() throws PokerGameException;

    void gotoEnd() throws InvalidStepActionException;

    void finishStep() throws InvalidStepActionException;

    void executeFirstBetStep() throws InvalidStepActionException;

    void executeBetStep() throws InvalidStepActionException;

    void executeBlindStep();

    void executeHandsDealingStep() throws InvalidStepActionException;

    void executeCommunityCardsDealingStep(int number) throws InvalidStepActionException;

    void executeShowDownStep();

    void executeEndStep() throws InvalidStepActionException;

    void notify(GameEvent event);

    TableInfos getTableInfos();

    Step getStep();
}
