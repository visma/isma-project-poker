package org.isma.poker.game.results;

import org.isma.poker.comparators.HandComparator;

import java.util.Comparator;

public class PlayerStateComparator implements Comparator<AbstractFinalPlayerState> {
    private HandComparator handComparator = new HandComparator();

    @Override
    public int compare(AbstractFinalPlayerState p1, AbstractFinalPlayerState p2) {
        return handComparator.compare(p1.getPlayer().getHand(), p2.getPlayer().getHand());
    }
}
