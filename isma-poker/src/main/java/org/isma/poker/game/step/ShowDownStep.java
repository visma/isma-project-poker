package org.isma.poker.game.step;

public class ShowDownStep extends AbstractStep {
    @Override
    public void doSetUp(PokerStepGame game) {
        game.executeShowDownStep();
    }
}
