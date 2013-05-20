package org.isma.poker.game.step;

public class ShowDownStep extends AbstractStep {
    @Override
    public void doRun(PokerStepRunner runner) {
        runner.executeShowDownStep();
    }
}
