package org.isma.poker.game.step;

public interface PokerStepGame {
    Step getStep();

    void executeFirstBetStep();

    void executeBetStep();

    void executeBlindStep();

    void executeHandsDealingStep();

    void executeCommunityCardsDealingStep(int number);

    void executeShowDownStep();

    void executeEndStep() throws InvalidStepActionException;
}
