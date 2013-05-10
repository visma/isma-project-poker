package org.isma.poker.game.step;

import org.isma.poker.game.results.Results;

public class ResultsStep extends AbstractStep {
    @Override
    public void doSetUp(PokerStepGame game) throws InvalidStepActionException {
        game.executeEndStep();
    }
}
