package org.isma.poker.game;

import org.isma.poker.exceptions.InvalidPlayerBetException;
import org.isma.poker.exceptions.InvalidPlayerTurnException;

public interface PlayerBetListener {
    boolean buy(Player player, int chips);

    void call(Player player) throws InvalidPlayerTurnException, InvalidPlayerBetException;

    void check(Player player) throws InvalidPlayerBetException, InvalidPlayerTurnException;

    void bet(Player player, int chips) throws InvalidPlayerBetException, InvalidPlayerTurnException;

    void raise(Player player, int chips) throws InvalidPlayerBetException, InvalidPlayerTurnException;

    void show(Player player) throws InvalidPlayerTurnException, InvalidPlayerBetException;

    void fold(Player player) throws InvalidPlayerBetException, InvalidPlayerTurnException;

    void allIn(Player player) throws InvalidPlayerBetException, InvalidPlayerTurnException;

    void paySmallBlind(Player player);

    void payBigBlind(Player player);
}
