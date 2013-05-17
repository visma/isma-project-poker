package org.isma.poker.game.actions;

import org.isma.poker.game.exceptions.InvalidPlayerBetException;
import org.isma.poker.game.model.InvalidPlayerTurnException;
import org.isma.poker.game.model.Player;
import org.isma.poker.game.step.InvalidStepActionException;

public interface PlayerBetListener {
    boolean buy(Player player, int chips);

    void call(Player player) throws InvalidPlayerTurnException, InvalidPlayerBetException, InvalidStepActionException;

    void check(Player player) throws InvalidPlayerBetException, InvalidPlayerTurnException, InvalidStepActionException;

    void bet(Player player, int chips) throws InvalidPlayerBetException, InvalidPlayerTurnException, InvalidStepActionException;

    void raise(Player player, int chips) throws InvalidPlayerBetException, InvalidPlayerTurnException, InvalidStepActionException;

    void show(Player player) throws InvalidPlayerTurnException, InvalidPlayerBetException, InvalidStepActionException;

    void fold(Player player) throws InvalidPlayerBetException, InvalidPlayerTurnException, InvalidStepActionException;

    void allIn(Player player) throws InvalidPlayerBetException, InvalidPlayerTurnException, InvalidStepActionException;

    void paySmallBlind(Player player) throws InvalidPlayerTurnException, InvalidPlayerBetException, InvalidStepActionException;

    void payBigBlind(Player player) throws InvalidStepActionException, InvalidPlayerBetException, InvalidPlayerTurnException;

    void sitIn(Player player) throws InvalidStepActionException;
}
