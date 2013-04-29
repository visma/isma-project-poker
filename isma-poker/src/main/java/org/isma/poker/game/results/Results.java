package org.isma.poker.game.results;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ComparatorUtils;
import org.apache.commons.collections.Predicate;
import org.isma.poker.HandEvaluator;
import org.isma.poker.comparators.PlayerHandComparator;
import org.isma.poker.game.Player;
import org.isma.poker.game.Pot;
import org.isma.poker.game.SplitPot;
import org.isma.utils.collections.CollectionHelper;

import java.util.*;

import static org.apache.commons.collections.ComparatorUtils.reversedComparator;

public class Results {
    private final HandEvaluator handEvaluator = new HandEvaluator();
    public static final PlayerHandComparator COMPARATOR = new PlayerHandComparator();

    private final Pot pot;
    private final Map<Player, Winner> winners = new HashMap<Player, Winner>();
    private final Map<Player, Loser> losers = new HashMap<Player, Loser>();


    private class PlayerEngagedOnSplitPot implements Predicate {
        private SplitPot splitPot;

        PlayerEngagedOnSplitPot(SplitPot splitPot) {
            this.splitPot = splitPot;
        }

        @Override
        public boolean evaluate(Object object) {
            return splitPot.getPlayers().contains(object);
        }
    }

    public Results(Pot pot, List<Player> inGamePlayers) {
        List<SplitPot> splitPots = pot.buildSplitPots();
        this.pot = pot;
        Map<Integer, List<Player>> indexedMap = CollectionHelper.buildIndexedMap(inGamePlayers, reversedComparator(COMPARATOR));
        int winnerRank = 0;
        while (pot.getTotal(splitPots) > 0) {
            deliverPrizesByRank(splitPots, indexedMap, winnerRank);
            winnerRank++;
        }
        buildLosers(inGamePlayers);
    }

    private void buildLosers(List<Player> inGamePlayers) {
        for (Player player : inGamePlayers) {
            if (!winners.containsKey(player)) {
                losers.put(player, new Loser(player, handEvaluator.evaluate(player.getHand())));
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
            Winner winner = new Winner(winnerPlayer, handEvaluator.evaluate(winnerPlayer.getHand()));
            winners.put(winnerPlayer, winner);
        }
        winners.get(winnerPlayer).addPrize(prize);
    }

    public List<Winner> getWinners() {
        return getPlayersStates(winners);
    }

    public List<Loser> getLosers() {
        return getPlayersStates(losers);
    }

    private <E extends AbstractFinalPlayerState> List<E> getPlayersStates(Map<Player, E> map) {
        List<E> list = new ArrayList<E>(map.values());
        Collections.sort(list, ComparatorUtils.reversedComparator(new PlayerStateComparator()));
        return list;
    }

}
