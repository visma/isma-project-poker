package org.isma.poker.game.step;

public interface PokerStepGame {
    Step getStep();

    void executeFirstBetStep();

    void executeBetStep();

    void executeBlindStep() throws InvalidStepActionException;

    void executeHandsDealingStep() throws InvalidStepActionException;

    void executeCommunityCardsDealingStep(int number) throws InvalidStepActionException;

    void executeShowDownStep();

    void executeEndStep() throws InvalidStepActionException;
}
