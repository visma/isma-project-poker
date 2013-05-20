package org.isma.poker.game.step;

public class CommunityCardsDealingStep extends AbstractStep {
    private final int numberOfCards;

    public CommunityCardsDealingStep(int numberOfCards) {
        this.numberOfCards = numberOfCards;
    }

    @Override
    public void doRun(PokerStepRunner runner) throws InvalidStepActionException {
        runner.executeCommunityCardsDealingStep(numberOfCards);
    }
}
