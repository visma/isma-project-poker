package org.isma.poker.commons.dto;

import org.isma.poker.game.step.Step;

import java.util.ArrayList;
import java.util.List;

public class TableDTO {
    private int smallBlindAmount;
    private int bigBlindAmount;
    private PlayerDTO currentPlayer;
    private PlayerDTO smallBlindPlayer;
    private PlayerDTO bigBlindPlayer;
    private int currentBet;
    private int pot;
    private Step step;
    private List<String> cards = new ArrayList<String>();

    public TableDTO() {
    }

    public int getSmallBlindAmount() {
        return smallBlindAmount;
    }

    public void setSmallBlindAmount(int smallBlindAmount) {
        this.smallBlindAmount = smallBlindAmount;
    }

    public int getBigBlindAmount() {
        return bigBlindAmount;
    }

    public void setBigBlindAmount(int bigBlindAmount) {
        this.bigBlindAmount = bigBlindAmount;
    }

    public PlayerDTO getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(PlayerDTO currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public PlayerDTO getSmallBlindPlayer() {
        return smallBlindPlayer;
    }

    public void setSmallBlindPlayer(PlayerDTO smallBlindPlayer) {
        this.smallBlindPlayer = smallBlindPlayer;
    }

    public PlayerDTO getBigBlindPlayer() {
        return bigBlindPlayer;
    }

    public void setBigBlindPlayer(PlayerDTO bigBlindPlayer) {
        this.bigBlindPlayer = bigBlindPlayer;
    }

    public void setCurrentBet(int currentBet) {
        this.currentBet = currentBet;
    }

    public int getCurrentBet() {
        return currentBet;
    }

    public void setStep(Step step) {
        this.step = step;
    }

    public Step getStep() {
        return step;
    }

    public int getPot() {
        return pot;
    }

    public void setPot(int pot) {
        this.pot = pot;
    }

    public List<String> getCards() {
        return cards;
    }
}
