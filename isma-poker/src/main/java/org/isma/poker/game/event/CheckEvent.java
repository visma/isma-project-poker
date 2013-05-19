package org.isma.poker.game.event;

import org.isma.poker.game.model.Player;

public class CheckEvent extends GameEvent {
    private Player player;

    public CheckEvent(Player player) {
        this.player = player.clone();
    }

    @Override
    public String getDescription() {
        return String.format("CheckEvent(%s(%s))", player.getNickname(), player.getChips());
    }
}
