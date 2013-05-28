package org.isma.poker.commons.mapper;

import org.isma.poker.commons.dto.TableDTO;
import org.isma.poker.game.GameSession;
import org.isma.poker.game.model.TableFacade;
import org.isma.poker.helper.CardHelper;
import org.isma.poker.model.Card;

public class TableMapper {
    //TODO TU
    public static TableDTO toDTO(GameSession game) {
        TableFacade tableFacade = game.getTableFacade();
        TableDTO dto = new TableDTO();

        dto.setCurrentPlayer(PlayerMapper.toDTO(tableFacade.getCurrentPlayerInfos(), game));
        dto.setSmallBlindPlayer(PlayerMapper.toDTO(tableFacade.getSmallBlindPlayerInfos(), game));
        dto.setBigBlindPlayer(PlayerMapper.toDTO(tableFacade.getBigBlindPlayerInfos(), game));

        dto.setSmallBlindAmount(tableFacade.getSmallBlindAmount());
        dto.setBigBlindAmount(tableFacade.getBigBlindAmount());

        dto.setCurrentBet(tableFacade.getCurrentBet());
        dto.setPot(tableFacade.getTotalPot());

        dto.setStep(game.getStep());

        for (Card card : tableFacade.getCommunityCards()) {
            dto.getCards().add(CardHelper.toString(card));
        }
        return dto;
    }
}
