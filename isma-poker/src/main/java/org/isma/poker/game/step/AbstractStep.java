package org.isma.poker.game.step;

public abstract class AbstractStep {
    protected Step step;

    public void setUp(PokerStepGame game) throws InvalidStepActionException {
        if (game.getStep() != step) {
            throw new InvalidStepActionException(step, game.getStep());
        }
        doSetUp(game);
    }

    protected abstract void doSetUp(PokerStepGame game) throws InvalidStepActionException;

    public void setStep(Step step) {
        this.step = step;
    }
}
