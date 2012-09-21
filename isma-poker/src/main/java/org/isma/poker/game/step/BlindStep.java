package org.isma.poker.game.step;

import org.isma.poker.game.GameSession;

public class BlindStep extends AbstractStep {
    @Override
    public void doSetUp(GameSession game) {
        game.executeBlindStep();
    }
}
