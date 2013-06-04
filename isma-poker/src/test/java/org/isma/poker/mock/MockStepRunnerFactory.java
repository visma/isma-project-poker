package org.isma.poker.mock;

import org.isma.poker.game.GameSession;
import org.isma.poker.game.GameStepRunner;
import org.isma.poker.game.IGameStepRunnerFactory;

public class MockStepRunnerFactory implements IGameStepRunnerFactory{
    @Override
    public GameStepRunner buildStepRunner(GameSession gameSession) {
        return new MockGameStepRunner();
    }
}
