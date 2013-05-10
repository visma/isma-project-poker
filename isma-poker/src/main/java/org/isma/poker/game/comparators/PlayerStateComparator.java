package org.isma.poker.game.comparators;

import org.isma.poker.HandEvaluator;
import org.isma.poker.game.results.AbstractFinalPlayerState;

import java.util.Comparator;

public class PlayerStateComparator implements Comparator<AbstractFinalPlayerState> {
    private HandEvaluator handEvaluator = new HandEvaluator();

    @Override
    public int compare(AbstractFinalPlayerState p1, AbstractFinalPlayerState p2) {
        return handEvaluator.getHandComparator().compare(p1.getPlayer().getHand(), p2.getPlayer().getHand());
    }
}
