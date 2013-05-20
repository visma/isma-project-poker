package org.isma.poker.game.step;

public class EndStep extends AbstractStep {
    @Override
    public void doSetUp(PokerStepGame game) throws InvalidStepActionException {
        game.executeEndStep();
    }
}