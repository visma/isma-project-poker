package org.isma.poker.services;

import org.isma.poker.HandEvaluator;
import org.isma.poker.game.GameSession;
import org.isma.poker.game.actions.PlayerAction;
import org.isma.poker.game.exceptions.PokerGameException;
import org.isma.poker.game.model.Player;
import org.isma.poker.game.step.InvalidStepActionException;
import org.isma.poker.model.HandEvaluation;

public class PlayerActionService {

    //@Autowired
    public PlayerActionService() {
    }

    public void buyChips(GameSession game, Player player, int chips) throws InvalidStepActionException {
        PlayerAction.buyChips(player, game, chips);
    }

    public void sitIn(GameSession game, Player player) throws PokerGameException {
        PlayerAction.sitIn(player, game);
    }

    public void fold(GameSession game, Player player) throws PokerGameException {
        PlayerAction.fold(player, game);
    }

    public void paySmallblind(GameSession game, Player player) throws PokerGameException {
        PlayerAction.paySmallBlind(player, game);
    }

    public void payBigblind(GameSession game, Player player) throws PokerGameException {
        PlayerAction.payBigBlind(player, game);
    }

    public void check(GameSession game, Player player) throws PokerGameException {
        PlayerAction.check(player, game);
    }

    public void call(GameSession game, Player player) throws PokerGameException {
        PlayerAction.call(player, game);
    }


    public void bet(GameSession game, Player player, int chips) throws PokerGameException {
        PlayerAction.bet(player, game, chips);
    }

    public void raise(GameSession game, Player player, int chips) throws PokerGameException {
        PlayerAction.raise(player, game, chips);
    }

    public void allIn(GameSession game, Player player) throws PokerGameException {
        PlayerAction.allIn(player, game);
    }

    public HandEvaluation show(GameSession game, Player player) throws PokerGameException {
        PlayerAction.show(player, game);
        return new HandEvaluator().evaluate(player.getHand());
    }


}
