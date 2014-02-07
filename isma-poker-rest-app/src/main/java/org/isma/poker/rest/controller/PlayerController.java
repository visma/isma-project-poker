package org.isma.poker.rest.controller;

import org.apache.log4j.Logger;
import org.isma.poker.commons.dto.PlayerDTO;
import org.isma.poker.commons.dto.TableDTO;
import org.isma.poker.game.actions.PokerActionEnum;
import org.isma.poker.game.exceptions.PokerGameException;
import org.isma.poker.game.step.InvalidStepActionException;
import org.isma.poker.model.HandEvaluation;
import org.isma.poker.rest.service.GameRestService;
import org.isma.poker.rest.service.PlayerActionRestService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping(value = "/poker")
public class PlayerController extends AbstractController {
    private static final Logger logger = Logger.getLogger(PlayerController.class);

    @Inject
    private PlayerActionRestService playerActionRestService;

    @Inject
    private GameRestService gameRestService;


    /**
     * *********************************************************************************
     * INFOS TABLE
     * **********************************************************************************
     */
    @RequestMapping(method = POST, value = "/room/{roomId}/createroom")
    @ResponseBody
    public String createRoom(@PathVariable Integer roomId) throws Exception {
        gameRestService.createRoom(roomId);
        return "OK";
    }

    @RequestMapping(method = GET, value = "/room/{roomId}/{authCode}")
    @ResponseBody
    public TableDTO getTable(@PathVariable Integer roomId,
                             @PathVariable String authCode) {
        return gameRestService.getTable(roomId, gameRestService.getPlayerNickname(authCode));
    }

    @RequestMapping(method = GET, value = "/room/{roomId}/players/{authCode}")
    @ResponseBody
    public List<PlayerDTO> getPlayers(@PathVariable Integer roomId,
                                      @PathVariable String authCode) {
        return gameRestService.getPlayers(roomId, gameRestService.getPlayerNickname(authCode));
    }

    @RequestMapping(method = GET, value = "/room/{roomId}/player/{authCode}")
    @ResponseBody
    public PlayerDTO getPlayer(@PathVariable Integer roomId, @PathVariable String authCode) {
        return gameRestService.getPlayer(roomId, gameRestService.getPlayerNickname(authCode));
    }

    /**
     * *********************************************************************************
     * ACTIONS JOUEURS
     * **********************************************************************************
     */

    @RequestMapping(method = POST, value = "/login/{nickname}")
    @ResponseBody
    public String login(
            @PathVariable String nickname) throws Exception {
        return gameRestService.login(nickname);
    }

    @RequestMapping(method = POST, value = "/room/{roomId}/sitin/{authCode}")
    @ResponseBody
    public PlayerDTO sitIn(
            @PathVariable Integer roomId,
            @PathVariable String authCode) throws PokerGameException {
        return playerActionRestService.sitIn(roomId, gameRestService.getPlayerNickname(authCode));
    }

    @RequestMapping(method = GET, value = "/room/{roomId}/actions/{nickname}")
    @ResponseBody
    public List<PokerActionEnum> getActions(
            @PathVariable Integer roomId,
            @PathVariable String nickname) {
        return playerActionRestService.getActions(roomId, nickname);
    }

    @RequestMapping(method = POST, value = "/room/{roomId}/buychips/{authCode}/{chips}")
    @ResponseBody
    public PlayerDTO buyChips(
            @PathVariable Integer roomId,
            @PathVariable String authCode,
            @PathVariable Integer chips) throws InvalidStepActionException {
        return playerActionRestService.buyChips(roomId, gameRestService.getPlayerNickname(authCode), chips);
    }

    @RequestMapping(value = "/room/{roomId}/fold/{authCode}", method = POST)
    @ResponseBody
    public PlayerDTO fold(
            @PathVariable Integer roomId,
            @PathVariable String authCode) throws PokerGameException {
        return playerActionRestService.fold(roomId, gameRestService.getPlayerNickname(authCode));
    }

    @RequestMapping(value = "/room/{roomId}/paysmallblind/{authCode}", method = POST)
    @ResponseBody
    public PlayerDTO paySmallblind(
            @PathVariable Integer roomId,
            @PathVariable String authCode) throws PokerGameException {
        return playerActionRestService.paySmallblind(roomId, gameRestService.getPlayerNickname(authCode));
    }

    @RequestMapping(value = "/room/{roomId}/paybigblind/{authCode}", method = POST)
    @ResponseBody
    public PlayerDTO payBigblind(
            @PathVariable Integer roomId,
            @PathVariable String authCode) throws PokerGameException {
        return playerActionRestService.payBigblind(roomId, gameRestService.getPlayerNickname(authCode));
    }

    @RequestMapping(value = "/room/{roomId}/check/{authCode}", method = POST)
    @ResponseBody
    public PlayerDTO check(
            @PathVariable Integer roomId,
            @PathVariable String authCode) throws PokerGameException {
        return playerActionRestService.check(roomId, gameRestService.getPlayerNickname(authCode));
    }

    @RequestMapping(value = "/room/{roomId}/call/{authCode}", method = POST)
    @ResponseBody
    public PlayerDTO call(
            @PathVariable Integer roomId,
            @PathVariable String authCode) throws PokerGameException {
        return playerActionRestService.call(roomId, gameRestService.getPlayerNickname(authCode));
    }


    @RequestMapping(value = "/room/{roomId}/bet/{authCode}/{chips}", method = POST)
    @ResponseBody
    public PlayerDTO bet(
            @PathVariable Integer roomId,
            @PathVariable String authCode,
            @PathVariable Integer chips) throws PokerGameException {
        return playerActionRestService.bet(roomId, gameRestService.getPlayerNickname(authCode), chips);
    }

    @RequestMapping(value = "/room/{roomId}/raise/{authCode}/{chips}", method = POST)
    @ResponseBody
    public PlayerDTO raise(
            @PathVariable Integer roomId,
            @PathVariable String authCode,
            @PathVariable Integer chips) throws PokerGameException {
        return playerActionRestService.raise(roomId, gameRestService.getPlayerNickname(authCode), chips);
    }

    @RequestMapping(value = "/room/{roomId}/allin/{authCode}", method = POST)
    @ResponseBody
    public PlayerDTO allIn(
            @PathVariable Integer roomId,
            @PathVariable String authCode) throws PokerGameException {
        return playerActionRestService.allIn(roomId, gameRestService.getPlayerNickname(authCode));
    }

    @RequestMapping(value = "/room/{roomId}/show/{authCode}", method = POST)
    @ResponseBody
    public HandEvaluation show(
            @PathVariable Integer roomId,
            @PathVariable String authCode) throws PokerGameException {
        return playerActionRestService.show(roomId, gameRestService.getPlayerNickname(authCode));
    }

}
