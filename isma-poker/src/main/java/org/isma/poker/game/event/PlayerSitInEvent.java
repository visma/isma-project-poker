package org.isma.poker.game.event;

import org.isma.poker.game.model.Player;

public class PlayerSitInEvent extends GameEvent {
    private Player player;

    public PlayerSitInEvent(Player player) {
        this.player = player.clone();
    }

    @Override
    public String getDescription() {
        return String.format("PlayerSitInEvent{%s, %s}", player.getNickname(), player.getChips());
    }
}
