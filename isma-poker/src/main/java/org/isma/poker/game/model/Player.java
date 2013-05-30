package org.isma.poker.game.model;

import org.isma.poker.model.Hand;

public class Player implements Cloneable {
    private final String nickname;
    private final Hand hand = new Hand();
    private int chips;
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


    public void setChips(int chips) {
        this.chips = chips;
    }

    @Override
    public String toString() {
        return "Player{" +
                "nickname='" + nickname + '\'' +
                ", chips=" + chips +
                ", hand=" + hand +
                '}';
    }

    //TODO TU (trouver une méthode efficace de tester clone() avec plantage si clone() n'est pas updaté avec de nouveaux attributs)
    public Player clone() {
        Player clone = new Player(nickname);
        clone.chips = this.chips;
        clone.fold = this.fold;
        clone.hand.addAll(this.hand);
        return clone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;

        Player player = (Player) o;

        if (!nickname.equals(player.nickname)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return nickname.hashCode();
    }
}
