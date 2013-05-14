package org.isma.poker.game.step;

public class CommunityCardsDealing extends AbstractStep {
    private final int numberOfCards;

    public CommunityCardsDealing(int numberOfCards) {
        this.numberOfCards = numberOfCards;
    }

    @Override
    public void doSetUp(PokerStepGame game) throws InvalidStepActionException {
        game.executeCommunityCardsDealingStep(numberOfCards);
    }
}
