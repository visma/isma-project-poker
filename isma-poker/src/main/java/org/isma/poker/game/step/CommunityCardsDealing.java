package org.isma.poker.game.step;

import org.isma.poker.game.GameSession;

public class CommunityCardsDealing extends AbstractStep {
    private int numberOfCards;

    public CommunityCardsDealing(int numberOfCards) {
        this.numberOfCards = numberOfCards;
    }

    @Override
    public void doSetUp(GameSession game) {
        game.executeCommunityCardsDealingStep(numberOfCards);
    }
}
