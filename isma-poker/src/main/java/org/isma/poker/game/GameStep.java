package org.isma.poker.game;

import org.apache.log4j.Logger;
import org.isma.poker.game.step.InvalidStepActionException;
import org.isma.poker.game.step.PokerStepGame;
import org.isma.poker.game.step.StepEnum;

class GameStep {
    private StepEnum step;
    private final PokerStepGame game;
    private boolean stepOver;
    private static final Logger LOG = Logger.getLogger(GameStep.class);

    public GameStep(PokerStepGame stepGame) {
        this.game = stepGame;
        step = StepEnum.END;
    }

    public void nextStep() throws InvalidStepActionException {
        stepOver = false;
        step = step.getNextStep();
        LOG.debug("nextStep : " + step);
        step.setUp(game);
    }

    public void gotoEnd() throws InvalidStepActionException {
        LOG.debug("gotoEnd : " + step);
        step = StepEnum.END;
        step.setUp(game);
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
