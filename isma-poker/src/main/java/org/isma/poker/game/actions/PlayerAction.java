package org.isma.poker.game.actions;

import org.apache.log4j.Logger;
import org.isma.poker.game.exceptions.InvalidPlayerBetException;
import org.isma.poker.game.model.InvalidPlayerTurnException;
import org.isma.poker.game.model.Player;
import org.isma.poker.game.step.InvalidStepActionException;

public class PlayerAction {
    private static final Logger LOG = Logger.getLogger(Player.class);


    public static void buyChips(Player player, PlayerBetListener game, int chips) {
        if (game.buy(player, chips)) {
            player.setChips(player.getChips() + chips);
        }
    }

    public static void allIn(Player player, PlayerBetListener game) throws InvalidPlayerTurnException, InvalidPlayerBetException, InvalidStepActionException {
        game.allIn(player);
    }

    public static void raise(Player player, PlayerBetListener game, int chips) throws InvalidPlayerTurnException, InvalidPlayerBetException, InvalidStepActionException {
        game.raise(player, chips);
    }

    public static void fold(Player player, PlayerBetListener game) throws InvalidPlayerBetException, InvalidPlayerTurnException, InvalidStepActionException {
        game.fold(player);
    }

    public static void show(Player player, PlayerBetListener game) throws InvalidPlayerTurnException, InvalidPlayerBetException, InvalidStepActionException {
        game.show(player);
    }

    public static void call(Player player, PlayerBetListener game) throws InvalidPlayerTurnException, InvalidPlayerBetException, InvalidStepActionException {
        game.call(player);
    }

    public static void bet(Player player, PlayerBetListener game, int chips) throws InvalidPlayerTurnException, InvalidPlayerBetException, InvalidStepActionException {
        game.bet(player, chips);
    }

    public static int payChips(Player player, int priceToPay) {
        if (!player.hasChips()) {
            throw new RuntimeException("avoid this...");
        }
        int chips = player.getChips();
        if (chips < priceToPay) {
            priceToPay = chips;
            chips = 0;
        } else {
            chips -= priceToPay;
        }
        player.setChips(chips);
        LOG.debug(String.format("Player{%s}.payChips(%s)", player.getNickname(), priceToPay));
        return priceToPay;
    }

    public static void paySmallBlind(Player player, PlayerBetListener playerBetListener) {
        playerBetListener.paySmallBlind(player);
    }

    public static void payBigBlind(Player player, PlayerBetListener playerBetListener) throws InvalidStepActionException {
        playerBetListener.payBigBlind(player);
    }

    public static void win(Player player, int prize) {
        player.setChips(player.getChips() + prize);
    }

    public static void check(Player player, PlayerBetListener game) throws InvalidPlayerTurnException, InvalidPlayerBetException, InvalidStepActionException {
        game.check(player);
    }

    public static void sitIn(Player player, PlayerBetListener game) throws InvalidStepActionException {
        game.sitIn(player);
    }
}
