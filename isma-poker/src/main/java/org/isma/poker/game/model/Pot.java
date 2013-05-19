package org.isma.poker.game.model;

import java.util.*;

public class Pot extends AbstractPot implements Cloneable{

    public int getTotal() {
        return getTotal(buildSplitPots());
    }

    public int getTotal(List<SplitPot> splitPots) {
        int total = 0;
        for (SplitPot splitPot : splitPots) {
            total += splitPot.getTotal();
        }
        return total;
    }

    public List<SplitPot> buildSplitPots() {
        Map<Player, Integer> clonePotMap = new HashMap<Player, Integer>(potMap);
        List<SplitPot> splitPots = new ArrayList<SplitPot>();
        while (!clonePotMap.isEmpty()) {
            splitPots.add(buildSplitPot(clonePotMap));
        }
        return splitPots;
    }

    private SplitPot buildSplitPot(Map<Player, Integer> clonePotMap) {
        Map<Player, Integer> splitPotMap = new HashMap<Player, Integer>();
        int minimumBet = getMinimumBet(clonePotMap);
        for (Player player : new HashSet<Player>(clonePotMap.keySet())) {
            int playerBet = clonePotMap.get(player);
            int remaining = playerBet - minimumBet;
            if (remaining > 0) {
                clonePotMap.put(player, remaining);
            } else {
                clonePotMap.remove(player);
            }
            splitPotMap.put(player, minimumBet);
        }
        return new SplitPot(splitPotMap);
    }

    private int getMinimumBet(Map<Player, Integer> clonePotMap) {
        int minimumBet = 999999999;
        for (Player player : clonePotMap.keySet()) {
            int playerBet = clonePotMap.get(player);
            if (playerBet < minimumBet) {
                minimumBet = playerBet;
            }
        }
        return minimumBet;
    }


    public void add(Player player, int paid) {
        int amt = potMap.get(player) == null ? 0 : potMap.get(player);
        potMap.put(player, amt + paid);
    }

    public int getTotalBet(Player player) {
        return potMap.get(player);
    }

    //TODO TU
    @Override
    public Pot clone() {
        Pot clone = new Pot();
        for (Player player : potMap.keySet()) {
            clone.potMap.put(player.clone(), potMap.get(player));
        }
        return clone;
    }
}
