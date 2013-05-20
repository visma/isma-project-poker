package org.isma.poker.game.step;

public abstract class AbstractStep {
    private Step step;

    public void setUp(PokerStepExecutable game) throws InvalidStepActionException {
        if (game.getStep() != step) {
            throw new InvalidStepActionException(step, game.getStep());
        }
        doSetUp(game);
    }

    protected abstract void doSetUp(PokerStepExecutable game) throws InvalidStepActionException;

    public void setStep(Step step) {
        this.step = step;
    }
}
