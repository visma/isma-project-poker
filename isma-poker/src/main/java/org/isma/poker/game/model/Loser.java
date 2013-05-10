package org.isma.poker.game.model;

import org.isma.poker.model.HandEvaluation;

public class Loser extends AbstractFinalPlayerState {
    public Loser(Player player, HandEvaluation handEvaluation) {
        super(player, handEvaluation);
    }
}
