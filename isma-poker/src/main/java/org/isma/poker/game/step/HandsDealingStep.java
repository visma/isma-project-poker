package org.isma.poker.game.step;

public class HandsDealingStep extends AbstractStep {
    @Override
    public void doRun(PokerStepRunner runner) throws InvalidStepActionException {
        runner.executeHandsDealingStep();
    }
}
