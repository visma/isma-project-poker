package org.isma.poker.game.model;

import org.isma.poker.model.HandEvaluation;
import org.isma.poker.game.model.Player;

public abstract class AbstractFinalPlayerState {
    private Player player;
    private HandEvaluation handEvaluation;

    protected AbstractFinalPlayerState(Player player, HandEvaluation handEvaluation) {
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
