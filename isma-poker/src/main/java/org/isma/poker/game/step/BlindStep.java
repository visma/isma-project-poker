package org.isma.poker.game.step;

public class BlindStep extends AbstractStep {
    @Override
    public void doSetUp(PokerStepGame game) {
        game.executeBlindStep();
    }
}
