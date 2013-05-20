package org.isma.poker.game.step;

public interface PokerStepRunner {
    Step getStep();

    void executeFirstBetStep() throws InvalidStepActionException;

    void executeBetStep() throws InvalidStepActionException;

    void executeBlindStep();

    void executeHandsDealingStep() throws InvalidStepActionException;

    void executeCommunityCardsDealingStep(int number) throws InvalidStepActionException;

    void executeShowDownStep();

    void executeEndStep() throws InvalidStepActionException;
}
