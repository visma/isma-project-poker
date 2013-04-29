package org.isma.poker.game;

import org.apache.log4j.Logger;
import org.isma.poker.exceptions.InvalidPlayerBetException;
import org.isma.poker.exceptions.InvalidPlayerTurnException;
import org.isma.poker.model.Hand;

public class Player {
    private static final Logger LOG = Logger.getLogger(Player.class);
    private final String nickname;
    protected int chips;
    private final Hand hand = new Hand();
    private boolean fold = false;

    public Player(String nickname) {
        this.nickname = nickname;
        chips = 0;
    }

    public void paySmallBlind(PlayerBetListener game) {
        game.paySmallBlind(this);
    }

    public void payBigBlind(PlayerBetListener game) {
        game.payBigBlind(this);
    }

    public void call(PlayerBetListener game) throws InvalidPlayerTurnException, InvalidPlayerBetException {
        game.call(this);
    }

    public void check(PlayerBetListener game) throws InvalidPlayerTurnException, InvalidPlayerBetException {
        game.check(this);
    }

    public void bet(int chips, PlayerBetListener game) throws InvalidPlayerTurnException, InvalidPlayerBetException {
        game.bet(this, chips);
    }

    public void show(PlayerBetListener game) throws InvalidPlayerTurnException, InvalidPlayerBetException {
        game.show(this);
    }

    public void raise(int chips, PlayerBetListener game) throws InvalidPlayerTurnException, InvalidPlayerBetException {
        game.raise(this, chips);
    }

    public void allIn(PlayerBetListener game) throws InvalidPlayerTurnException, InvalidPlayerBetException {
        game.allIn(this);
    }

    public void fold(PlayerBetListener game) throws InvalidPlayerBetException, InvalidPlayerTurnException {
        game.fold(this);
    }

    public void buyChips(PlayerBetListener game, int chips) {
        if (game.buy(this, chips)) {
            this.chips += chips;
        }
    }

    public String getNickname() {
        return nickname;
    }

    public int getChips() {
        return chips;
    }

    public Hand getHand() {
        return hand;
    }

    public boolean hasChips() {
        return chips > 0;
    }

    @Override
    public String toString() {
        return "Player{" +
                "nickname='" + nickname + '\'' +
                ", chips=" + chips +
                ", hand=" + hand +
                '}';
    }

    public int payChips(int priceToPay) {
        if (chips == 0) {
            throw new RuntimeException("avoid this...");
        }
        if (chips < priceToPay) {
            priceToPay = chips;
            chips = 0;
        } else {
            chips -= priceToPay;
        }
        LOG.debug(String.format("Player{%s}.payChips(%s)", nickname, priceToPay));
        return priceToPay;
    }

    public void setFold(boolean fold) {
        this.fold = fold;
    }

    public boolean isFold() {
        return fold;
    }


    public void win(int prize) {
        chips += prize;
    }

}
