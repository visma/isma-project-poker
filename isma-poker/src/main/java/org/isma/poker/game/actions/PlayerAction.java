package org.isma.poker.game.actions;

import org.apache.log4j.Logger;
import org.isma.poker.game.exceptions.PokerGameException;
import org.isma.poker.game.model.Player;
import org.isma.poker.game.step.InvalidStepActionException;

public class PlayerAction {
    private static final Logger LOG = Logger.getLogger(Player.class);

    //TODO faire un TU ou le 2 joueur s'assoit avec 0 jetons, en achete et verifier que la partie demarre
    public static void buyChips(Player player, PlayerBetListener game, int chips) throws InvalidStepActionException {
        game.buy(player, chips);
    }

    public static void allIn(Player player, PlayerBetListener game) throws PokerGameException {
        game.allIn(player);
    }

    public static void raise(Player player, PlayerBetListener game, int chips) throws PokerGameException {
        game.raise(player, chips);
    }

    public static void fold(Player player, PlayerBetListener game) throws PokerGameException {
        game.fold(player);
    }

    public static void show(Player player, PlayerBetListener game) throws PokerGameException {
        game.show(player);
    }

    public static void call(Player player, PlayerBetListener game) throws PokerGameException {
        game.call(player);
    }

    public static void bet(Player player, PlayerBetListener game, int chips) throws PokerGameException {
        game.bet(player, chips);
    }


    public static void paySmallBlind(Player player, PlayerBetListener game) throws PokerGameException {
        game.paySmallBlind(player);
    }

    public static void payBigBlind(Player player, PlayerBetListener game) throws PokerGameException {
        game.payBigBlind(player);
    }

    public static void check(Player player, PlayerBetListener game) throws PokerGameException {
        game.check(player);
    }

    public static void sitIn(Player player, PlayerBetListener game) throws PokerGameException {
        game.sitIn(player);
    }

    public static void sitOut(Player player, PlayerBetListener game) throws PokerGameException {
        game.sitOut(player);
    }


    //TODO crade a deplacer ailleurs
    public static int payChips(Player player, int priceToPay) {
        if (!player.hasChips()) {
            throw new RuntimeException("impossible to pay if no chips");
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


}
