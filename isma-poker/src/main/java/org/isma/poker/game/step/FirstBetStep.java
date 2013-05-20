package org.isma.poker.game.step;

public class FirstBetStep extends AbstractStep {
    @Override
    public void doRun(PokerStepRunner runner) throws InvalidStepActionException {
        runner.executeFirstBetStep();
    }
}
