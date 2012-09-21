package org.isma.poker.game;

public class PlayerInfos {
    private Player player;
    private int position;

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
