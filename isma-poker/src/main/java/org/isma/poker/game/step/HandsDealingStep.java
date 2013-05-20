package org.isma.poker.game.step;

public class HandsDealingStep extends AbstractStep {
    @Override
    public void doSetUp(PokerStepExecutable game) throws InvalidStepActionException {
        game.executeHandsDealingStep();
    }
}
