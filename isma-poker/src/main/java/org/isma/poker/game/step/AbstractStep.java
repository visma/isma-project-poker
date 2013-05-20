package org.isma.poker.game.step;

public abstract class AbstractStep {
    private Step step;

    public void run(PokerStepRunner runner) throws InvalidStepActionException {
        if (runner.getStep() != step) {
            throw new InvalidStepActionException(step, runner.getStep());
        }
        doRun(runner);
    }

    protected abstract void doRun(PokerStepRunner game) throws InvalidStepActionException;

    public void setStep(Step step) {
        this.step = step;
    }
}
