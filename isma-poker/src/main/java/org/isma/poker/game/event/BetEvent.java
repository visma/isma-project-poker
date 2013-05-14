package org.isma.poker.game.event;

import org.isma.poker.game.model.Player;

public class BetEvent extends GameEvent{
    private final Player player;
    private final int bet;

    public BetEvent(Player player, int bet) {
        this.player = player;
        this.bet = bet;
    }

    @Override
    public String getDescription() {
        return String.format("BetEvent(bet=%s %s(%s))", bet, player.getNickname(), player.getChips());
    }
}
