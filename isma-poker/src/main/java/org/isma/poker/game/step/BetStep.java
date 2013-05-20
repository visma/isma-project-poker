package org.isma.poker.game.step;

public class BetStep extends AbstractStep {
    @Override
    public void doRun(PokerStepRunner runner) throws InvalidStepActionException {
        runner.executeBetStep();
    }
}
