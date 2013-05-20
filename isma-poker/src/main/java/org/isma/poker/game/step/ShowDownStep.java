package org.isma.poker.game.step;

public class ShowDownStep extends AbstractStep {
    @Override
    public void doSetUp(PokerStepExecutable game) {
        game.executeShowDownStep();
    }
}
