package org.isma.poker.game.event;

import org.isma.poker.game.model.Player;

public class SitOutEvent extends GameEvent {
    private final Player player;

    public SitOutEvent(Player player) {
        this.player = player.clone();
    }

    @Override
    public String getDescription() {
        return String.format("SitOutEvent(%s)", player.getNickname());
    }
}
