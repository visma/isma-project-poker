package org.isma.poker.game.results;

import org.isma.poker.HandEvaluationEnum;
import org.isma.poker.game.Player;

public abstract class AbstractFinalPlayerState {
    private Player player;
    private HandEvaluationEnum handEvaluation;

    protected AbstractFinalPlayerState(Player player, HandEvaluationEnum handEvaluation) {
        this.player = player;
        this.handEvaluation = handEvaluation;
    }

    public Player getPlayer() {
        return player;
    }

    public HandEvaluationEnum getHandEvaluation() {
        return handEvaluation;
    }

}
