package org.isma.poker.game.event;

import org.isma.poker.game.model.Player;

public class BuyEvent extends GameEvent{
    private final Player player;
    private final int chips;

    public BuyEvent(Player player, int chips){
        this.chips = chips;
        this.player = player.clone();
    }
    @Override
    public String getDescription() {
        return String.format("BuyEvent(%s(%s))", player.getNickname(), chips);
    }
}

