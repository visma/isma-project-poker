package org.isma.poker.game.step;

public class HandsDealingStep extends AbstractStep {
    @Override
    public void doSetUp(PokerStepGame game) throws InvalidStepActionException {
        game.executeHandsDealingStep();
    }
}
