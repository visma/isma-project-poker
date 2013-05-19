package org.isma.poker.game.model;

import org.isma.poker.model.HandEvaluation;

public class Loser extends AbstractFinalPlayerState implements Cloneable {
    public Loser(Player player, HandEvaluation handEvaluation) {
        super(player, handEvaluation);
    }

    @Override
    public Loser clone() {
        return new Loser(this.getPlayer().clone(), this.getHandEvaluation());
    }
}
