package org.isma.poker.game;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractPot {
    protected Map<Player, Integer> potMap = new HashMap<Player, Integer>();

    public abstract int getTotal();

    public void clear() {
        potMap.clear();
    }

}
