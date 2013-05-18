package org.isma.poker.game.model;

public class PlayerInfos {
    private final Player player;
    private final int position;

    public PlayerInfos(Player player, int position) {
        this.player = player;
        this.position = position;
    }

    public Player getPlayer() {
        return player;
    }

    public int getPosition() {
        return position;
    }
}
