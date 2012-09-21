package org.isma.poker.comparators;

import org.isma.poker.game.Player;

import java.util.Comparator;

public class PlayerHandComparator implements Comparator<Player> {
    private final HandComparator handComparator = new HandComparator();

    @Override
    public int compare(Player p1, Player p2) {
        return handComparator.compare(p1.getHand(), p2.getHand());
    }
}
