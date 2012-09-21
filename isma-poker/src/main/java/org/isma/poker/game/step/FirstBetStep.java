package org.isma.poker.game.step;

import org.isma.poker.game.GameSession;

public class FirstBetStep extends AbstractStep{
    @Override
    public void doSetUp(GameSession game) {
        game.executeFirstBetStep();
    }
}
