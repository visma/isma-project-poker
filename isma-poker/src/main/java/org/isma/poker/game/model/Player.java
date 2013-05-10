package org.isma.poker.game.model;

import org.isma.poker.model.Hand;

public class Player {
    private final String nickname;
    private int chips;
    private final Hand hand = new Hand();
    private boolean fold = false;

    public Player(String nickname) {
        this.nickname = nickname;
        chips = 0;
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

    public void setFold(boolean fold) {
        this.fold = fold;
    }

    public boolean isFold() {
        return fold;
    }


    @Override
    public String toString() {
        return "Player{" +
                "nickname='" + nickname + '\'' +
                ", chips=" + chips +
                ", hand=" + hand +
                '}';
    }

    public void setChips(int chips) {
        this.chips = chips;
    }
}
