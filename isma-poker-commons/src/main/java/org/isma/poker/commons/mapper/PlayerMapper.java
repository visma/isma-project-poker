package org.isma.poker.commons.mapper;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.isma.poker.commons.dto.PlayerDTO;
import org.isma.poker.game.GameSession;
import org.isma.poker.game.model.Player;
import org.isma.poker.game.model.PlayerInfos;
import org.isma.poker.helper.CardHelper;
import org.isma.poker.model.Hand;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlayerMapper {

    public static PlayerDTO toDTO(Player player, GameSession game) {
        if (player == null) {
            return null;
        }
        Hand hand = player.getHand();
        String card1 = hand.size() >= 2 ? CardHelper.toString(hand.get(0)) : null;
        String card2 = hand.size() >= 2 ? CardHelper.toString(hand.get(1)) : null;
        PlayerDTO dto = new PlayerDTO(player.getNickname(), player.getChips(), player.isFold(), card1, card2);
        dto.setPosition(-1);
        dto.setCurrentBet(game.getTableFacade().getCurrentStepBet(player));
        return dto;
    }

    public static PlayerDTO toDTO(PlayerInfos playerInfos, GameSession game) {
        if (playerInfos == null || playerInfos.getPlayer() == null) {
            return null;
        }
        Player player = playerInfos.getPlayer();
        PlayerDTO dto = toDTO(player, game);
        dto.setPosition(playerInfos.getPosition());
        return dto;
    }

    public static List<PlayerDTO> toDTOs(List<PlayerInfos> playersInfos, final GameSession game) {
        if (playersInfos == null) {
            return Collections.emptyList();
        }
        List clones = new ArrayList<PlayerInfos>(playersInfos);
        CollectionUtils.transform(clones, new Transformer() {
            @Override
            public Object transform(Object input) {
                return toDTO((PlayerInfos) input, game);
            }
        });
        return (List<PlayerDTO>) clones;
    }
}
