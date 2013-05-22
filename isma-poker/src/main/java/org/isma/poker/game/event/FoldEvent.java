package org.isma.poker.game.event;

import org.isma.poker.game.model.Player;

public class FoldEvent extends GameEvent {
    private final Player player;

    public FoldEvent(Player player) {
        this.player = player.clone();
    }

    @Override
    public String getDescription() {
        return String.format("FoldEvent(%s)", player.getNickname());
    }
}
