package org.isma.poker.game.model;

import org.isma.poker.game.model.Player;

import java.util.HashMap;
import java.util.Map;

abstract class AbstractPot {
    final Map<Player, Integer> potMap = new HashMap<Player, Integer>();

    public abstract int getTotal();

    public void clear() {
        potMap.clear();
    }

}
