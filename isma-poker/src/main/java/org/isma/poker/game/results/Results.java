package org.isma.poker.game.results;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ComparatorUtils;
import org.apache.commons.collections.Predicate;
import org.isma.poker.HandEvaluator;
import org.isma.poker.game.comparators.PlayerHandComparator;
import org.isma.poker.game.comparators.PlayerStateComparator;
import org.isma.poker.game.model.*;
import org.isma.poker.model.Hand;
import org.isma.poker.model.HandEvaluation;
import org.isma.utils.collections.CollectionHelper;

import java.util.*;

import static org.apache.commons.collections.ComparatorUtils.reversedComparator;

public class Results implements Cloneable {
    private final HandEvaluator handEvaluator = new HandEvaluator();
    private static final PlayerHandComparator COMPARATOR = new PlayerHandComparator();

    private final Map<Player, Winner> winners = new HashMap<Player, Winner>();
    private final Map<Player, Loser> losers = new HashMap<Player, Loser>();

    public Results(Pot pot, List<Player> inGamePlayers) {
        List<SplitPot> splitPots = pot.buildSplitPots();
        Map<Integer, List<Player>> indexedMap = CollectionHelper.buildIndexedMap(inGamePlayers, reversedComparator(COMPARATOR));
        int winnerRank = 0;
        while (pot.getTotal(splitPots) > 0) {
            deliverPrizesByRank(splitPots, indexedMap, winnerRank);
            winnerRank++;
        }
        buildLosers(inGamePlayers);
    }

    private Results(Results toClone) {
        for (Player player : toClone.winners.keySet()) {
            Winner winner = toClone.winners.get(player);
            winners.put(player.clone(), winner.clone());
        }
        for (Player player : toClone.losers.keySet()) {
            Loser loser = toClone.losers.get(player);
            losers.put(player.clone(), loser.clone());
        }
    }

    private void buildLosers(List<Player> inGamePlayers) {
        for (Player player : inGamePlayers) {
            if (!winners.containsKey(player)) {
                Hand hand = player.getHand();
                HandEvaluation evaluation = hand.isEmpty() ? null : handEvaluator.evaluate(hand);
                losers.put(player, new Loser(player, evaluation));
            }
        }
    }

    private void deliverPrizesByRank(List<SplitPot> splitPots, Map<Integer, List<Player>> indexedMap, int winnerRank) {
        List<Player> winnerList = indexedMap.get(winnerRank);
        for (final SplitPot splitPot : new ArrayList<SplitPot>(splitPots)) {
            int splitCount = CollectionUtils.countMatches(winnerList, new PlayerEngagedOnSplitPot(splitPot));
            if (splitCount == 0) {
                continue;
            }
            int prize = splitPot.getTotal() / splitCount;
            for (Player winner : winnerList) {
                if (splitPot.getPlayers().contains(winner)) {
                    deliverPrize(winner, prize);
                    splitPots.remove(splitPot);
                }
            }
        }
    }

    private void deliverPrize(final Player winnerPlayer, int prize) {
        if (winners.get(winnerPlayer) == null) {
            Hand hand = winnerPlayer.getHand();
            HandEvaluation evaluation = hand.isEmpty() ? null : handEvaluator.evaluate(hand);
            Winner winner = new Winner(winnerPlayer, evaluation);
            winners.put(winnerPlayer, winner);
        }
        winners.get(winnerPlayer).addPrize(prize);
    }

    private <E extends AbstractFinalPlayerState> List<E> getPlayersStates(Map<Player, E> map) {
        List<E> list = new ArrayList<E>(map.values());
        Collections.sort(list, ComparatorUtils.reversedComparator(new PlayerStateComparator()));
        return list;
    }

    private class PlayerEngagedOnSplitPot implements Predicate {
        private final SplitPot splitPot;

        PlayerEngagedOnSplitPot(SplitPot splitPot) {
            this.splitPot = splitPot;
        }

        @Override
        public boolean evaluate(Object object) {
            return splitPot.getPlayers().contains(object);
        }
    }

    public List<Winner> getWinners() {
        return getPlayersStates(winners);
    }

    public List<Loser> getLosers() {
        return getPlayersStates(losers);
    }

    //TODO TU (trouver une m�thode efficace de tester clone() avec plantage si clone() n'est pas updat� avec de nouveaux attributs)
    @Override
    public Results clone() {
        return new Results(this);
    }
}
