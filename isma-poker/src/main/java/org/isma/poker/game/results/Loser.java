package org.isma.poker.game.results;

import org.isma.poker.model.HandEvaluation;
import org.isma.poker.game.Player;

public class Loser extends AbstractFinalPlayerState {
    public Loser(Player player, HandEvaluation handEvaluation) {
        super(player, handEvaluation);
    }
}
