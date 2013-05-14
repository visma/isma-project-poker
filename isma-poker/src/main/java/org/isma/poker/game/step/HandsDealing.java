package org.isma.poker.game.step;

public class HandsDealing extends AbstractStep {
    @Override
    public void doSetUp(PokerStepGame game) throws InvalidStepActionException {
        game.executeHandsDealingStep();
    }
}
