package org.isma.poker.game;

import org.apache.log4j.Logger;
import org.isma.poker.game.step.InvalidStepActionException;
import org.isma.poker.game.step.PokerStepRunner;
import org.isma.poker.game.step.StepEnum;

class GameStepRunner {
    private static final Logger LOG = Logger.getLogger(GameStepRunner.class);
    private final PokerStepRunner runner;

    private StepEnum step;
    private boolean stepOver;

    public GameStepRunner(PokerStepRunner runner) {
        this.runner = runner;
        step = StepEnum.END;
    }

    public void nextStep() throws InvalidStepActionException {
        stepOver = false;
        step = step.getNextStep();
        LOG.debug("nextStep : " + step);
        step.run(runner);
    }

    public void gotoEnd() throws InvalidStepActionException {
        LOG.debug("gotoEnd : " + step);
        step = StepEnum.END;
        step.run(runner);
    }

    public void freeze(){
        step = StepEnum.END;
    }

    public StepEnum getStep() {
        return step;
    }

    public void finish() throws InvalidStepActionException {
        stepOver = true;
        nextStep();
    }

    public boolean isOver() {
        return stepOver;
    }

    @Override
    public String toString() {
        return step.name();
    }

}
