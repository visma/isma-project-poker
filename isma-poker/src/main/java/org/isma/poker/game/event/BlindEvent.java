package org.isma.poker.game.event;

import org.isma.poker.game.model.Player;

public class BlindEvent extends GameEvent {
    private final Player player;
    private final boolean small;
    private final int amount;

    public BlindEvent(Player player, boolean small, int amount) {
        this.player = player;
        this.small = small;
        this.amount = amount;
    }

    @Override
    public String getDescription() {
        return String.format("BlindEvent{%s, %s, %s}",
                player.getNickname(),
                small ? "small" : "big",
                amount);
    }
}
