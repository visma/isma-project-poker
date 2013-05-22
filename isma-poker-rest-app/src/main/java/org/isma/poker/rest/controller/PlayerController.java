package org.isma.poker.rest.controller;

import org.apache.log4j.Logger;
import org.isma.poker.HandEvaluator;
import org.isma.poker.factory.DeckFactory;
import org.isma.poker.game.GameSession;
import org.isma.poker.game.actions.PokerActionEnum;
import org.isma.poker.game.exceptions.PokerGameException;
import org.isma.poker.game.factory.TableFactory;
import org.isma.poker.game.model.GameConfiguration;
import org.isma.poker.game.model.InvalidGameConfigurationException;
import org.isma.poker.game.model.Player;
import org.isma.poker.game.model.PlayerInfos;
import org.isma.poker.game.step.InvalidStepActionException;
import org.isma.poker.game.step.Step;
import org.isma.poker.model.HandEvaluation;
import org.isma.poker.services.PlayerActionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import java.util.List;

import static java.util.Collections.emptyList;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping(value = "/poker")
public class PlayerController {
    private static final Logger logger = Logger.getLogger(PlayerController.class);
    private GameSession gameSession;

    @Inject
    private PlayerActionService playerActionService;

    public PlayerController() throws InvalidGameConfigurationException {
        GameConfiguration configuration = new GameConfiguration(5, 10, false, true);
        gameSession = new GameSession(configuration, new DeckFactory(), new TableFactory());
        gameSession.init(2);
    }

    /************************************************************************************
     * INFOS TABLE
     ************************************************************************************/
    //TODO pour jmeter : a supprimer
    @RequestMapping(method = POST, value = "/room/{roomId}/reset")
    @ResponseBody
    public String reset(
            @PathVariable Integer roomId) throws InvalidGameConfigurationException {
        GameConfiguration configuration = new GameConfiguration(5, 10, false, true);
        gameSession = new GameSession(configuration, new DeckFactory(), new TableFactory());
        gameSession.init(2);
        return "OK";
    }

    @RequestMapping(method = GET, value = "/room/{roomId}/status")
    @ResponseBody
    public Step getTableStatus(
            @PathVariable Integer roomId) {
        return gameSession.getStep();
    }

    @RequestMapping(method = GET, value = "/room/{roomId}/players")
    @ResponseBody
    public List<PlayerInfos> getPlayers(
            @PathVariable Integer roomId) {
        return gameSession.getTableFacade().getPlayersInfos();
    }
    /************************************************************************************
     * ACTIONS JOUEURS
     ************************************************************************************/

    @RequestMapping(method = GET, value = "/room/{roomId}/actions/{nickname}")
    @ResponseBody
    public List<PokerActionEnum> getActions(
            @PathVariable Integer roomId,
            @PathVariable String nickname) {
        Player player = gameSession.getTableFacade().getPlayerInfos(nickname).getPlayer();
        if (player == null) {
            return emptyList();
        }
        return gameSession.getAvailableActions(player);
    }

    @RequestMapping(method = POST, value = "/room/{roomId}/buychips/{nickname}/{chips}")
    @ResponseBody
    public Player buyChips(
            @PathVariable Integer roomId,
            @PathVariable String nickname,
            @PathVariable Integer chips) throws InvalidStepActionException {
        Player player = gameSession.getTableFacade().getPlayerInfos(nickname).getPlayer();
        playerActionService.buyChips(gameSession, player, chips);
        return player;
    }


    @RequestMapping(method = POST, value = "/room/{roomId}/sitin/{nickname}")
    @ResponseBody
    public Player sitIn(
            @PathVariable Integer roomId,
            @PathVariable String nickname) throws PokerGameException {
        Player newPlayer = new Player(nickname);
        playerActionService.sitIn(gameSession, newPlayer);
        return newPlayer;
    }

    @RequestMapping(value = "/room/{roomId}/fold/{nickname}", method = POST)
    public void fold(
            @PathVariable Integer roomId,
            @PathVariable String nickname) throws PokerGameException {
        Player player = gameSession.getTableFacade().getPlayerInfos(nickname).getPlayer();
        playerActionService.fold(gameSession, player);
    }

    @RequestMapping(value = "/room/{roomId}/paysmallblind/{nickname}", method = POST)
    @ResponseBody
    public Player paySmallblind(
            @PathVariable Integer roomId,
            @PathVariable String nickname) throws PokerGameException {
        Player player = gameSession.getTableFacade().getPlayerInfos(nickname).getPlayer();
        playerActionService.paySmallblind(gameSession, player);
        return player;
    }

    @RequestMapping(value = "/room/{roomId}/paybigblind/{nickname}", method = POST)
    @ResponseBody
    public Player payBigblind(
            @PathVariable Integer roomId,
            @PathVariable String nickname) throws PokerGameException {
        Player player = gameSession.getTableFacade().getPlayerInfos(nickname).getPlayer();
        playerActionService.payBigblind(gameSession, player);
        return player;
    }

    @RequestMapping(value = "/room/{roomId}/check/{nickname}", method = POST)
    @ResponseBody
    public Player check(
            @PathVariable Integer roomId,
            @PathVariable String nickname) throws PokerGameException {
        Player player = gameSession.getTableFacade().getPlayerInfos(nickname).getPlayer();
        playerActionService.check(gameSession, player);
        return player;
    }

    @RequestMapping(value = "/room/{roomId}/call/{nickname}", method = POST)
    @ResponseBody
    public Player call(
            @PathVariable Integer roomId,
            @PathVariable String nickname) throws PokerGameException {
        Player player = gameSession.getTableFacade().getPlayerInfos(nickname).getPlayer();
        playerActionService.call(gameSession, player);
        return player;
    }


    @RequestMapping(value = "/room/{roomId}/bet/{nickname}/{chips}", method = POST)
    @ResponseBody
    public Player bet(
            @PathVariable Integer roomId,
            @PathVariable String nickname,
            @PathVariable Integer chips) throws PokerGameException {
        Player player = gameSession.getTableFacade().getPlayerInfos(nickname).getPlayer();
        playerActionService.bet(gameSession, player, chips);
        return player;
    }

    @RequestMapping(value = "/room/{roomId}/raise/{nickname}/{chips}", method = POST)
    @ResponseBody
    public Player raise(
            @PathVariable Integer roomId,
            @PathVariable String nickname,
            @PathVariable Integer chips) throws PokerGameException {
        Player player = gameSession.getTableFacade().getPlayerInfos(nickname).getPlayer();
        playerActionService.raise(gameSession, player, chips);
        return player;
    }

    @RequestMapping(value = "/room/{roomId}/allin/{nickname}", method = POST)
    @ResponseBody
    public Player allIn(
            @PathVariable Integer roomId,
            @PathVariable String nickname) throws PokerGameException {
        Player player = gameSession.getTableFacade().getPlayerInfos(nickname).getPlayer();
        playerActionService.allIn(gameSession, player);
        return player;
    }

    @RequestMapping(value = "/room/{roomId}/show/{nickname}", method = POST)
    @ResponseBody
    public HandEvaluation show(
            @PathVariable Integer roomId,
            @PathVariable String nickname) throws PokerGameException {
        Player player = gameSession.getTableFacade().getPlayerInfos(nickname).getPlayer();
        playerActionService.show(gameSession, player);
        return new HandEvaluator().evaluate(player.getHand());
    }

}
