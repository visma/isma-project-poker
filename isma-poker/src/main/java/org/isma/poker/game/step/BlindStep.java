package org.isma.poker.game.step;

public class BlindStep extends AbstractStep {
    @Override
    public void doSetUp(PokerStepExecutable game) throws InvalidStepActionException {
        game.executeBlindStep();
    }
}
