package org.isma.poker.game.results;

import org.isma.poker.model.HandEvaluation;
import org.isma.poker.game.model.Player;

public class Winner extends AbstractFinalPlayerState {
    private int prize;

    public Winner(Player player, HandEvaluation handEvaluation) {
        super(player, handEvaluation);
    }


    public int getPrize() {
        return prize;
    }

    public void addPrize(int prize) {
        this.prize += prize;
    }
}
