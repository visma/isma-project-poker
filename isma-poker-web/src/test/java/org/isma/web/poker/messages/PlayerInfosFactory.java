package org.isma.web.poker.messages;

import org.isma.poker.game.Player;
import org.isma.poker.game.PlayerBetListener;
import org.isma.poker.game.PlayerInfos;

public class PlayerInfosFactory {

    public static PlayerInfos buildPlayerInfo(int position, String nickname, int chips) {
        Player player = new Player(nickname) {
            @Override
            public void buyChips(PlayerBetListener game, int chips) {
                this.chips += chips;
            }
        };
        player.buyChips(null, chips);
        return new PlayerInfos(player, position);
    }
}
