package org.isma.poker.game.step;

import org.isma.poker.exceptions.InvalidStepActionException;
import org.isma.poker.game.GameSession;
import org.isma.poker.game.StepEnum;

//TODO vérifier la matrice de dépendances...
public abstract class AbstractStep {
    protected StepEnum stepEnum;

    public void setUp(GameSession game) throws Exception {
        if (game.getStep() != stepEnum) {
            throw new InvalidStepActionException(stepEnum, game.getStep());
        }
        doSetUp(game);
    }

    protected abstract void doSetUp(GameSession game) throws Exception;

    public void setStep(StepEnum stepEnum) {
        this.stepEnum = stepEnum;
    }
}
