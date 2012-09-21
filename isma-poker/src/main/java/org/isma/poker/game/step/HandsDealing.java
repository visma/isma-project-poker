package org.isma.poker.game.step;

import org.isma.poker.game.GameSession;

public class HandsDealing extends AbstractStep {
    @Override
    public void doSetUp(GameSession game) {
        game.executeHandsDealingStep();
    }
}
