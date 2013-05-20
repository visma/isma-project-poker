package org.isma.poker.game.step;

public class FirstBetStep extends AbstractStep {
    @Override
    public void doSetUp(PokerStepExecutable game) throws InvalidStepActionException {
        game.executeFirstBetStep();
    }
}
