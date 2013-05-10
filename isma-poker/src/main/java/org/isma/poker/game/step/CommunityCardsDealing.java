package org.isma.poker.game.step;

public class CommunityCardsDealing extends AbstractStep {
    private int numberOfCards;

    public CommunityCardsDealing(int numberOfCards) {
        this.numberOfCards = numberOfCards;
    }

    @Override
    public void doSetUp(PokerStepGame game) {
        game.executeCommunityCardsDealingStep(numberOfCards);
    }
}
