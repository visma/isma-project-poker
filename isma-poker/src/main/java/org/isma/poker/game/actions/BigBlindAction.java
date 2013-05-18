package org.isma.poker.game.actions;

import org.isma.poker.game.event.BlindEvent;
import org.isma.poker.game.exceptions.InvalidPlayerBetException;
import org.isma.poker.game.model.GameConfiguration;
import org.isma.poker.game.model.Player;
import org.isma.poker.game.model.Table;
import org.isma.poker.game.step.InvalidStepActionException;
import org.isma.poker.game.step.PokerStepGame;

import static org.isma.poker.game.actions.PokerActionEnum.PAY_BIG_BLIND;

public class BigBlindAction extends AbstractPlayerAction {
    private GameConfiguration configuration;

    public BigBlindAction(PokerStepGame gameSession, Table table, GameConfiguration gameConfiguration) {
        super(PAY_BIG_BLIND, gameSession, table);
        this.configuration = gameConfiguration;
    }

    @Override
    protected void doAction(Player player) throws InvalidPlayerBetException, InvalidStepActionException {
        gameSession.notify(new BlindEvent(player, false, configuration.getBigBlindAmount()));
        table.addBigBlind(configuration);
        int paid = PlayerAction.payChips(player, configuration.getBigBlindAmount());
        table.addToPot(player, paid);
    }
}