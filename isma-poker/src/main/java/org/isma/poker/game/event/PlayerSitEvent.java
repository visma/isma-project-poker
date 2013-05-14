package org.isma.poker.game.event;

import org.isma.poker.game.model.Player;

public class PlayerSitEvent extends GameEvent {
    private Player player;

    public PlayerSitEvent(Player player) {
        this.player = player;
    }

    @Override
    public String getDescription() {
        return String.format("PlayerSitEvent{%s, %s}", player.getNickname(), player.getChips());
    }
}
