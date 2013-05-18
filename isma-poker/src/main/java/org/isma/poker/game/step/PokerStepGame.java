package org.isma.poker.game.step;

import org.isma.poker.game.event.GameEvent;
import org.isma.poker.game.model.TableInfos;

public interface PokerStepGame {
    void beginRoundIfPossible() throws InvalidStepActionException;

    void nextStep() throws InvalidStepActionException;

    void finishStep() throws InvalidStepActionException;

    void executeFirstBetStep();

    void executeBetStep();

    void executeBlindStep() throws InvalidStepActionException;

    void executeHandsDealingStep() throws InvalidStepActionException;

    void executeCommunityCardsDealingStep(int number) throws InvalidStepActionException;

    void executeShowDownStep();

    void executeEndStep() throws InvalidStepActionException;

    void notify(GameEvent event);

    TableInfos getTableInfos();

    Step getStep();
}
