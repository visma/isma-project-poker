package org.isma.poker.game.step;

public class BlindStep extends AbstractStep {
    @Override
    public void doRun(PokerStepRunner runner) throws InvalidStepActionException {
        runner.executeBlindStep();
    }
}
