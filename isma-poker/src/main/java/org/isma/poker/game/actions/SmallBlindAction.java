package org.isma.poker.game.actions;

import org.isma.poker.game.event.BlindEvent;
import org.isma.poker.game.exceptions.InvalidPlayerBetException;
import org.isma.poker.game.model.GameConfiguration;
import org.isma.poker.game.model.Player;
import org.isma.poker.game.model.Table;
import org.isma.poker.game.step.InvalidStepActionException;
import org.isma.poker.game.step.PokerActionStepGame;

import static org.isma.poker.game.actions.PokerActionEnum.PAY_SMALL_BLIND;

public class SmallBlindAction extends AbstractPlayerAction {
    private GameConfiguration configuration;

    public SmallBlindAction(PokerActionStepGame gameSession, Table table, GameConfiguration gameConfiguration) {
        super(PAY_SMALL_BLIND, gameSession, table);
        this.configuration = gameConfiguration;
    }

    @Override
    protected void doAction(Player player) throws InvalidPlayerBetException, InvalidStepActionException {
        game.notifyEvent(new BlindEvent(player, true, configuration.getSmallBlindAmount()));
        table.addSmallBlind(configuration);
        int paid = PlayerAction.payChips(player, configuration.getSmallBlindAmount());
        table.addToPot(player, paid);
    }
}