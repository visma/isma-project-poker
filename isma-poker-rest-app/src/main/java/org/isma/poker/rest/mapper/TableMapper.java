package org.isma.poker.rest.mapper;

import org.isma.poker.game.GameSession;
import org.isma.poker.game.model.TableFacade;
import org.isma.poker.rest.dto.TableDTO;

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
        return dto;
    }
}
