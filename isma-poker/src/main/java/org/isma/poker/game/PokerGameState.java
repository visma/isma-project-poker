package org.isma.poker.game;

import org.isma.poker.game.step.Step;

public interface PokerGameState {
    TableInfos getTableInfos();

    Step getStep();
}
