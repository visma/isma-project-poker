package org.isma.poker.game.event;

import org.isma.poker.game.model.Player;

public class CallEvent extends GameEvent{
    private final Player player;

    public CallEvent(Player player){
        this.player = player;
    }
    @Override
    public String getDescription() {
        return String.format("CallEvent(%s(%s))", player.getNickname(), player.getChips());
    }
}
