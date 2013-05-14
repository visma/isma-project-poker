package org.isma.poker.game;

import org.isma.poker.game.step.InvalidStepActionException;
import org.isma.poker.game.step.PokerStepGame;
import org.isma.poker.game.step.StepEnum;

class GameStep {
    private StepEnum step;
    private final PokerStepGame game;
    private boolean stepOver;

    public GameStep(PokerStepGame stepGame) {
        this.game = stepGame;
        step = StepEnum.END;
    }

    public void nextStep() throws InvalidStepActionException {
        stepOver = false;
        step = step.nextStep();
        step.setUp(game);
    }

    public void gotoEnd() {
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
