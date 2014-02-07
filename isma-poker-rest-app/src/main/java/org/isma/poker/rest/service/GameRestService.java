package org.isma.poker.rest.service;

import org.isma.poker.commons.dto.PlayerDTO;
import org.isma.poker.commons.dto.TableDTO;
import org.isma.poker.commons.mapper.PlayerMapper;
import org.isma.poker.commons.mapper.TableMapper;
import org.isma.poker.game.GameSession;
import org.isma.poker.game.model.PlayerInfos;
import org.isma.poker.rest.manager.PlayerSessionManager;
import org.isma.poker.rest.manager.RoomManager;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

import static org.isma.poker.commons.mapper.TableMapper.toDTO;

@Service
public class GameRestService {
    @Inject
    private RoomManager roomManager;

    @Inject
    private PlayerSessionManager playerSessionManager;

    public GameRestService() {
    }

    public String getPlayerNickname(String authCode){
        return playerSessionManager.getPlayerNickname(authCode);
    }

    public void createRoom(Integer roomId) throws Exception {
        roomManager.createRoom(roomId);
    }

    public TableDTO getTable(Integer roomId, String nickname) {
        GameSession game = roomManager.getGame(roomId);
        TableDTO dto = toDTO(game);
        dto.setStep(game.getStep());
        TableMapper.hiddeHoleCards(game, dto, nickname);
        return dto;
    }

    public List<PlayerDTO> getPlayers(Integer roomId, String nickname) {
        GameSession game = roomManager.getGame(roomId);
        List<PlayerInfos> playersInfos = roomManager.getGame(roomId).getTableFacade().getPlayersInfos();
        List<PlayerDTO> playerDTOs = PlayerMapper.toDTOs(playersInfos, game);
        PlayerMapper.hiddeHoleCards(game, playerDTOs, nickname);
        return playerDTOs;
    }

    public PlayerDTO getPlayer(Integer roomId, String nickname) {
        GameSession game = roomManager.getGame(roomId);
        PlayerInfos playersInfos = roomManager.getGame(roomId).getTableFacade().getPlayerInfos(nickname);
        PlayerDTO playerDTO = PlayerMapper.toDTO(playersInfos, game);
        PlayerMapper.hiddeHoleCards(game, playerDTO, nickname);
        return playerDTO;
    }

    public String login(String nickname) throws Exception {
        String authCode = playerSessionManager.register(nickname);
        return authCode;
    }
}
