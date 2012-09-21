package org.isma.poker.game;

import org.isma.poker.model.CommunityCards;

import java.util.ArrayList;
import java.util.List;

public class TableInfos {
    private final Table table;
    private final GameConfiguration configuration;

    public TableInfos(Table table, GameConfiguration configuration) {
        this.table = table;
        this.configuration = configuration;
    }

    public int getRaisesRemaining() {
        return table.getRaisesRemaining();
    }

    /**
     * @deprecated  replaced by @link #getDealerInfos()
     */
    @Deprecated
    public Player getDealer() {
        return table.getDealer();
    }

    /**
     * @deprecated  replaced by @link #getSmallBlindPlayerInfos()
     */
    @Deprecated
    public Player getSmallBlindPlayer() {
        return table.getSmallBlindPlayer();
    }

    /**
     * @deprecated  replaced by @link #getBigBlindPlayerInfos()
     */
    @Deprecated
    public Player getBigBlindPlayer() {
        return table.getBigBlindPlayer();
    }

    /**
     * @deprecated  replaced by @link #getCurrentPlayerInfos()
     */
    @Deprecated
    public Player getCurrentPlayer() {
        return table.getCurrentPlayer();
    }

    /**
     * @deprecated  replaced by @link #getNextPlayerInfos()
     */
    @Deprecated
    public Player getNextPlayer() {
        return table.getNextPlayer();
    }

    public int getCurrentBet() {
        return table.getCurrentBet();
    }

    public CommunityCards getCommunityCards() {
        return table.getCommunityCards();
    }

    public int getTotalPot() {
        table.getPot().buildSplitPots();
        return table.getPot().getTotal();
    }

    public int getCurrentStepBet(Player player) {
        return table.getCurrentStepBet(player);
    }

    public int getBigBlindAmount() {
        return configuration.getBigBlindAmount();
    }

    public int getSmallBlindAmount() {
        return configuration.getSmallBlindAmount();
    }

    public int getMaxPlayers() {
        return configuration.getMaxPlayers();
    }

    public boolean isTableFull() {
        return getPlayersInfos().size() == configuration.getMaxPlayers();
    }

    public List<PlayerInfos> getPlayersInfos() {
        List<PlayerInfos> playersInfos = new ArrayList<PlayerInfos>();
        List<Player> allPlayers = table.getAllPlayers();
        int position = 0;
        for (Player player : allPlayers) {
            playersInfos.add(new PlayerInfos(player, position++));
        }
        return playersInfos;
    }

    public PlayerInfos getCurrentPlayerInfos() {
        return buildPlayerInfo(getCurrentPlayer());
    }

    public PlayerInfos getDealerInfos() {
        return buildPlayerInfo(getDealer());
    }

    private PlayerInfos buildPlayerInfo(Player player) {
        return new PlayerInfos(player, table.getAllPlayers().indexOf(player));
    }

    public int getRemainingChipsToPay(Player player) {
        int paid = getCurrentStepBet(player);
        int currentBet = getCurrentBet();
        if (currentBet > paid) {
            return currentBet - paid;
        }
        return 0;
    }
}
