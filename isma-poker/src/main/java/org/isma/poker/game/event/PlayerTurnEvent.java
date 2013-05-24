package org.isma.poker.game.event;

import org.isma.poker.game.model.Player;

public class PlayerTurnEvent extends GameEvent {
    private Player player;

    public PlayerTurnEvent(Player player) {
        this.player = player.clone();
    }

    @Override
    public String getDescription() {
        return String.format("PlayerTurnEvent{%s}", player.getNickname());
    }
}
