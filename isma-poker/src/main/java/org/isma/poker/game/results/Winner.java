package org.isma.poker.game.results;

import org.isma.poker.HandEvaluationEnum;
import org.isma.poker.game.Player;

public class Winner extends AbstractFinalPlayerState {
    private int prize;

    public Winner(Player player, HandEvaluationEnum handEvaluation) {
        super(player, handEvaluation);
    }


    public int getPrize() {
        return prize;
    }

    public void addPrize(int prize) {
        this.prize += prize;
    }
}
