package org.isma.poker.game.comparators;

import org.isma.poker.HandEvaluator;
import org.isma.poker.game.Player;

import java.util.Comparator;

public class PlayerHandComparator implements Comparator<Player> {
    private final HandEvaluator handEvaluator = new HandEvaluator();

    @Override
    public int compare(Player p1, Player p2) {
        return handEvaluator.getHandComparator().compare(p1.getHand(), p2.getHand());
    }
}
