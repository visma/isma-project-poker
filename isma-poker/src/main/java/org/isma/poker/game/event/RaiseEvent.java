package org.isma.poker.game.event;

import org.isma.poker.game.model.Player;

public class RaiseEvent extends GameEvent {
    private final Player player;
    private final int raiseAmount;

    public RaiseEvent(Player player, int raiseAmount) {
        this.player = player.clone();
        this.raiseAmount = raiseAmount;
    }

    @Override
    public String getDescription() {
        return String.format("RaiseEvent(raise=%s %s)", raiseAmount, player.getNickname());
    }
}
