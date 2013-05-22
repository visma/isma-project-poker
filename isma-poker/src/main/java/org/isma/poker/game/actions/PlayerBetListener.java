package org.isma.poker.game.actions;

import org.isma.poker.game.exceptions.PokerGameException;
import org.isma.poker.game.model.Player;
import org.isma.poker.game.step.InvalidStepActionException;

public interface PlayerBetListener {
    boolean buy(Player player, int chips) throws InvalidStepActionException;

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

    void sitOut(Player player) throws PokerGameException;
}
