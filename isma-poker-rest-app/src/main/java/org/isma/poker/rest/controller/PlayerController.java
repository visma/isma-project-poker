package org.isma.poker.rest.controller;

import org.apache.log4j.Logger;
import org.isma.poker.game.actions.PokerActionEnum;
import org.isma.poker.game.exceptions.PokerGameException;
import org.isma.poker.game.step.InvalidStepActionException;
import org.isma.poker.model.HandEvaluation;
import org.isma.poker.rest.dto.PlayerDTO;
import org.isma.poker.rest.dto.TableDTO;
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
public class PlayerController {
    private static final Logger logger = Logger.getLogger(PlayerController.class);

    @Inject
    private PlayerActionRestService playerActionRestService;

    @Inject
    private GameRestService gameRestService;


//    public PlayerController() {
//    }

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

    @RequestMapping(method = GET, value = "/room/{roomId}/status")
    @ResponseBody
    public TableDTO getTableStatus(@PathVariable Integer roomId) {
      return gameRestService.getTable(roomId);
    }

    @RequestMapping(method = GET, value = "/room/{roomId}/players")
    @ResponseBody
    public List<PlayerDTO> getPlayers(@PathVariable Integer roomId) {
        return gameRestService.getPlayers(roomId);
    }

    /**
     * *********************************************************************************
     * ACTIONS JOUEURS
     * **********************************************************************************
     */

    @RequestMapping(method = GET, value = "/room/{roomId}/actions/{nickname}")
    @ResponseBody
    public List<PokerActionEnum> getActions(
            @PathVariable Integer roomId,
            @PathVariable String nickname) {
        return playerActionRestService.getActions(roomId, nickname);
    }

    @RequestMapping(method = POST, value = "/room/{roomId}/buychips/{nickname}/{chips}")
    @ResponseBody
    public PlayerDTO buyChips(
            @PathVariable Integer roomId,
            @PathVariable String nickname,
            @PathVariable Integer chips) throws InvalidStepActionException {
        return playerActionRestService.buyChips(roomId, nickname, chips);
    }


    @RequestMapping(method = POST, value = "/room/{roomId}/sitin/{nickname}")
    @ResponseBody
    public PlayerDTO sitIn(
            @PathVariable Integer roomId,
            @PathVariable String nickname) throws PokerGameException {
        return playerActionRestService.sitIn(roomId, nickname);
    }

    @RequestMapping(value = "/room/{roomId}/fold/{nickname}", method = POST)
    @ResponseBody
    public PlayerDTO fold(
            @PathVariable Integer roomId,
            @PathVariable String nickname) throws PokerGameException {
        return playerActionRestService.fold(roomId, nickname);
    }

    @RequestMapping(value = "/room/{roomId}/paysmallblind/{nickname}", method = POST)
    @ResponseBody
    public PlayerDTO paySmallblind(
            @PathVariable Integer roomId,
            @PathVariable String nickname) throws PokerGameException {
        return playerActionRestService.paySmallblind(roomId, nickname);
    }

    @RequestMapping(value = "/room/{roomId}/paybigblind/{nickname}", method = POST)
    @ResponseBody
    public PlayerDTO payBigblind(
            @PathVariable Integer roomId,
            @PathVariable String nickname) throws PokerGameException {
        return playerActionRestService.payBigblind(roomId, nickname);
    }

    @RequestMapping(value = "/room/{roomId}/check/{nickname}", method = POST)
    @ResponseBody
    public PlayerDTO check(
            @PathVariable Integer roomId,
            @PathVariable String nickname) throws PokerGameException {
        return playerActionRestService.check(roomId, nickname);
    }

    @RequestMapping(value = "/room/{roomId}/call/{nickname}", method = POST)
    @ResponseBody
    public PlayerDTO call(
            @PathVariable Integer roomId,
            @PathVariable String nickname) throws PokerGameException {
        return playerActionRestService.call(roomId, nickname);
    }


    @RequestMapping(value = "/room/{roomId}/bet/{nickname}/{chips}", method = POST)
    @ResponseBody
    public PlayerDTO bet(
            @PathVariable Integer roomId,
            @PathVariable String nickname,
            @PathVariable Integer chips) throws PokerGameException {
        return playerActionRestService.bet(roomId, nickname, chips);
    }

    @RequestMapping(value = "/room/{roomId}/raise/{nickname}/{chips}", method = POST)
    @ResponseBody
    public PlayerDTO raise(
            @PathVariable Integer roomId,
            @PathVariable String nickname,
            @PathVariable Integer chips) throws PokerGameException {
        return playerActionRestService.raise(roomId, nickname, chips);
    }

    @RequestMapping(value = "/room/{roomId}/allin/{nickname}", method = POST)
    @ResponseBody
    public PlayerDTO allIn(
            @PathVariable Integer roomId,
            @PathVariable String nickname) throws PokerGameException {
        return playerActionRestService.allIn(roomId, nickname);
    }

    @RequestMapping(value = "/room/{roomId}/show/{nickname}", method = POST)
    @ResponseBody
    public HandEvaluation show(
            @PathVariable Integer roomId,
            @PathVariable String nickname) throws PokerGameException {
        return playerActionRestService.show(roomId, nickname);
    }

}
