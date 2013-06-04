package org.isma.poker.game;

//TODO verifier cycles
public interface IGameStepRunnerFactory {
    GameStepRunner buildStepRunner(GameSession gameSession);
}
