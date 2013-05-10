package org.isma.poker.game.model;

import org.isma.poker.game.model.AbstractPot;
import org.isma.poker.game.model.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SplitPot extends AbstractPot {
    public SplitPot(Map<Player, Integer> potMap) {
        this.potMap.putAll(potMap);
    }

    public int getTotal() {
        int total = 0;
        for (int playerBet : potMap.values()) {
            total += playerBet;
        }
        return total;
    }

    public List<Player> getPlayers() {
        return new ArrayList<Player>(potMap.keySet());
    }
}
