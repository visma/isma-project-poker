package org.isma.poker.game.results;

import org.isma.poker.HandEvaluationEnum;
import org.isma.poker.game.Player;

public class Loser extends AbstractFinalPlayerState {
    public Loser(Player player, HandEvaluationEnum handEvaluation) {
        super(player, handEvaluation);
    }
}
