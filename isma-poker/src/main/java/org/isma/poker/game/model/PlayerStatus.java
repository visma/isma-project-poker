package org.isma.poker.game.model;

/**
 * INGAME : Alive in the round
 * PLAYING : Current player
 * FOLDED : Folded
 * WAITING : Waiting that game starts
 * OUT_OF_CASH : Eliminated
 */
public enum PlayerStatus {
    INGAME,
    PLAYING,
    FOLDED,
    WAITING,
    OUT_OF_CASH
}
