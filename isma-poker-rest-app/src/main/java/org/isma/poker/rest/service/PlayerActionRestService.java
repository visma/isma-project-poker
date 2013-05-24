package org.isma.poker.rest.service;

import org.isma.poker.game.GameSession;
import org.isma.poker.game.actions.PokerActionEnum;
import org.isma.poker.game.exceptions.PokerGameException;
import org.isma.poker.game.model.Player;
import org.isma.poker.game.model.PlayerInfos;
import org.isma.poker.game.step.InvalidStepActionException;
import org.isma.poker.model.HandEvaluation;
import org.isma.poker.rest.dto.PlayerDTO;
import org.isma.poker.rest.manager.RoomManager;
import org.isma.poker.services.PlayerActionService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

import static java.util.Collections.emptyList;
import static org.isma.poker.rest.mapper.PlayerMapper.toDTO;

@Service
public class PlayerActionRestService {
    @Inject
    private RoomManager roomManager;

    @Inject
    private PlayerActionService service;

    public PlayerActionRestService() {
    }

    public PlayerDTO buyChips(Integer roomId, String nickname, Integer chips) throws InvalidStepActionException {
        GameSession game = roomManager.getGame(roomId);
        PlayerInfos player = game.getTableFacade().getPlayerInfos(nickname);
        service.buyChips(game, player.getPlayer(), chips);
        return toDTO(player, game);
    }


    public List<PokerActionEnum> getActions(Integer roomId, String nickname) {
        GameSession game = roomManager.getGame(roomId);
        Player player = game.getTableFacade().getPlayerInfos(nickname).getPlayer();
        if (player == null) {
            return emptyList();
        }
        return game.getAvailableActions(player);
    }

    public PlayerDTO sitIn(Integer roomId, String nickname) throws PokerGameException {
        GameSession game = roomManager.getGame(roomId);
        Player newPlayer = new Player(nickname);
        service.sitIn(game, newPlayer);
        return toDTO(newPlayer, game);
    }

    public PlayerDTO fold(Integer roomId, String nickname) throws PokerGameException {
        GameSession game = roomManager.getGame(roomId);
        PlayerInfos player = game.getTableFacade().getPlayerInfos(nickname);
        service.fold(game, player.getPlayer());
        return toDTO(player, game);
    }

    public PlayerDTO paySmallblind(Integer roomId, String nickname) throws PokerGameException {
        GameSession game = roomManager.getGame(roomId);
        PlayerInfos player = game.getTableFacade().getPlayerInfos(nickname);
        service.paySmallblind(game, player.getPlayer());
        return toDTO(player, game);
    }

    public PlayerDTO payBigblind(Integer roomId, String nickname) throws PokerGameException {
        GameSession game = roomManager.getGame(roomId);
        PlayerInfos player = game.getTableFacade().getPlayerInfos(nickname);
        service.payBigblind(game, player.getPlayer());
        return toDTO(player, game);
    }

    public PlayerDTO check(Integer roomId, String nickname) throws PokerGameException {
        GameSession game = roomManager.getGame(roomId);
        PlayerInfos player = game.getTableFacade().getPlayerInfos(nickname);
        service.check(game, player.getPlayer());
        return toDTO(player, game);
    }

    public PlayerDTO call(Integer roomId, String nickname) throws PokerGameException {
        GameSession game = roomManager.getGame(roomId);
        PlayerInfos player = game.getTableFacade().getPlayerInfos(nickname);
        service.call(game, player.getPlayer());
        return toDTO(player, game);
    }

    public PlayerDTO bet(Integer roomId, String nickname, Integer chips) throws PokerGameException {
        GameSession game = roomManager.getGame(roomId);
        PlayerInfos player = game.getTableFacade().getPlayerInfos(nickname);
        service.bet(game, player.getPlayer(), chips);
        return toDTO(player, game);
    }

    public PlayerDTO raise(Integer roomId, String nickname, Integer chips) throws PokerGameException {
        GameSession game = roomManager.getGame(roomId);
        PlayerInfos player = game.getTableFacade().getPlayerInfos(nickname);
        service.raise(game, player.getPlayer(), chips);
        return toDTO(player, game);
    }

    public PlayerDTO allIn(Integer roomId, String nickname) throws PokerGameException {
        GameSession game = roomManager.getGame(roomId);
        PlayerInfos player = game.getTableFacade().getPlayerInfos(nickname);
        service.allIn(game, player.getPlayer());
        return toDTO(player, game);

    }

    public HandEvaluation show(Integer roomId, String nickname) throws PokerGameException {
        GameSession game = roomManager.getGame(roomId);
        PlayerInfos player = game.getTableFacade().getPlayerInfos(nickname);
        return service.show(game, player.getPlayer());

    }
}
