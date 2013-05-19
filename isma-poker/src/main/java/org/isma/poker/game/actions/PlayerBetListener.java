package org.isma.poker.game.actions;

import org.isma.poker.game.exceptions.PokerGameException;
import org.isma.poker.game.model.Player;

public interface PlayerBetListener {
    boolean buy(Player player, int chips);

    void call(Player player) throws PokerGameException;

    void check(Player player) throws PokerGameException;

    void bet(Player player, int chips) throws PokerGameException;

    void raise(Player player, int chips) throws PokerGameException;

    void show(Player player) throws PokerGameException;

    void fold(Player player) throws PokerGameException;

    void allIn(Player player) throws PokerGameException;

    void paySmallBlind(Player player) throws PokerGameException;

    void payBigBlind(Player player) throws PokerGameException;

    void sitIn(Player player) throws PokerGameException;
}
