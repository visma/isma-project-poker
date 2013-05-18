package org.isma.poker.game.step;

public class CommunityCardsDealingStep extends AbstractStep {
    private final int numberOfCards;

    public CommunityCardsDealingStep(int numberOfCards) {
        this.numberOfCards = numberOfCards;
    }

    @Override
    public void doSetUp(PokerStepGame game) throws InvalidStepActionException {
        game.executeCommunityCardsDealingStep(numberOfCards);
    }
}
