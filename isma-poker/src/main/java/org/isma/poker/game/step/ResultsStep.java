package org.isma.poker.game.step;

import org.isma.poker.exceptions.InvalidStepActionException;
import org.isma.poker.game.GameSession;
import org.isma.poker.game.results.Results;

public class ResultsStep extends AbstractStep {
    @Override
    public void doSetUp(GameSession game) throws InvalidStepActionException {
        Results results = game.buildResults();
        game.executeEndStep(results);
    }
}
