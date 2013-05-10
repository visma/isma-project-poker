package org.isma.poker.game.model;

import org.isma.poker.model.HandEvaluation;
import org.isma.poker.game.model.Player;

public abstract class AbstractFinalPlayerState {
    private final Player player;
    private final HandEvaluation handEvaluation;

    AbstractFinalPlayerState(Player player, HandEvaluation handEvaluation) {
        this.player = player;
        this.handEvaluation = handEvaluation;
    }

    public Player getPlayer() {
        return player;
    }

    public HandEvaluation getHandEvaluation() {
        return handEvaluation;
    }

}
