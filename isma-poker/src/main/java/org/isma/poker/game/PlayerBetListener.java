package org.isma.poker.game;

import org.isma.poker.exceptions.InvalidPlayerBetException;
import org.isma.poker.exceptions.InvalidPlayerTurnException;

public interface PlayerBetListener {
    public boolean buy(Player player, int chips);

    public void call(Player player) throws InvalidPlayerTurnException, InvalidPlayerBetException;

    public void check(Player player) throws InvalidPlayerBetException, InvalidPlayerTurnException;

    public void bet(Player player, int chips) throws InvalidPlayerBetException, InvalidPlayerTurnException;

    public void raise(Player player, int chips) throws InvalidPlayerBetException, InvalidPlayerTurnException;

    public void show(Player player) throws InvalidPlayerTurnException, InvalidPlayerBetException;

    public void fold(Player player) throws InvalidPlayerBetException, InvalidPlayerTurnException;

    public void allIn(Player player) throws InvalidPlayerBetException, InvalidPlayerTurnException;

    public void paySmallBlind(Player player);

    public void payBigBlind(Player player);
}
