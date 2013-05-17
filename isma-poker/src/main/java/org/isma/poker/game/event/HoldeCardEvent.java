package org.isma.poker.game.event;

import org.isma.poker.game.model.Player;

public class HoldeCardEvent extends GameEvent {
    private final Player player;

    public HoldeCardEvent(Player player) {
        this.player = player;
    }

    @Override
    public String getDescription() {
        return String.format("HoldeCardEvent{player:%s, cards:%s}", player.getNickname(), player.getHand());
    }
}
