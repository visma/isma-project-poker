package org.isma.poker.game.model;

import org.isma.poker.game.model.Player;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractPot {
    protected Map<Player, Integer> potMap = new HashMap<Player, Integer>();

    public abstract int getTotal();

    public void clear() {
        potMap.clear();
    }

}
