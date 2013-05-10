package org.isma.poker.game.step;

public class FirstBetStep extends AbstractStep {
    @Override
    public void doSetUp(PokerStepGame game) {
        game.executeFirstBetStep();
    }
}
