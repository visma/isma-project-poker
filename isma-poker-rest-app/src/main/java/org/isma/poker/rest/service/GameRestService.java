package org.isma.poker.rest.service;

import org.isma.poker.commons.dto.PlayerDTO;
import org.isma.poker.commons.dto.TableDTO;
import org.isma.poker.commons.mapper.PlayerMapper;
import org.isma.poker.game.GameSession;
import org.isma.poker.game.model.PlayerInfos;
import org.isma.poker.rest.manager.RoomManager;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

import static org.isma.poker.commons.mapper.TableMapper.toDTO;

@Service
public class GameRestService {
    @Inject
    private RoomManager roomManager;

    public GameRestService() {
    }

    public void createRoom(Integer roomId) throws Exception {
        roomManager.createRoom(roomId);
    }

    public TableDTO getTable(Integer roomId) {
        GameSession game = roomManager.getGame(roomId);
        TableDTO dto = toDTO(game);
        dto.setStep(game.getStep());
        return dto;
    }

    public List<PlayerDTO> getPlayers(Integer roomId) {
        GameSession game = roomManager.getGame(roomId);
        List<PlayerInfos> playersInfos = roomManager.getGame(roomId).getTableFacade().getPlayersInfos();
        return PlayerMapper.toDTOs(playersInfos, game);
    }

    public PlayerDTO getPlayer(Integer roomId, String nickname) {
        GameSession game = roomManager.getGame(roomId);
        PlayerInfos playersInfos = roomManager.getGame(roomId).getTableFacade().getPlayerInfos(nickname);
        return PlayerMapper.toDTO(playersInfos, game);
    }
}