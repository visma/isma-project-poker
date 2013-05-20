package org.isma.poker.game.step;

public class BetStep extends AbstractStep {
    @Override
    public void doSetUp(PokerStepExecutable game) throws InvalidStepActionException {
        game.executeBetStep();
    }
}
