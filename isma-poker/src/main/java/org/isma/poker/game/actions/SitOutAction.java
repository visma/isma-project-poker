package org.isma.poker.game.actions;

import org.isma.poker.game.event.SitOutEvent;
import org.isma.poker.game.exceptions.InvalidPlayerBetException;
import org.isma.poker.game.model.Player;
import org.isma.poker.game.model.Table;
import org.isma.poker.game.step.InvalidStepActionException;
import org.isma.poker.game.step.PokerActionStepGame;
import org.isma.poker.game.step.Step;
import org.isma.poker.game.step.StepEnum;

import static org.isma.poker.game.actions.PokerActionEnum.SIT_OUT;

public class SitOutAction extends AbstractPlayerAction {

    public SitOutAction(PokerActionStepGame game, Table table) {
        super(SIT_OUT, game, table);
    }

    @Override
    protected void doAction(Player player) throws InvalidPlayerBetException, InvalidStepActionException {
        table.handleFold(player);
        Step step = game.getStep();
        LOG.debug(String.format("SitOutAction.doAction(%s, %s)", player.getNickname(), step));

        if (step == StepEnum.BLINDS){
            blindPlayerSitOut(player);
        }else {
            playerSitOut(player);
        }

        game.notifyEvent(new SitOutEvent(player));
    }

    @Override
    protected void endPlayerTurn(Player player) throws InvalidStepActionException {
        super.endPlayerTurn(player);
        table.handleSitOut(player);
        if (table.getAliveWithChipsPlayers().size() < 2){
            game.freeze();

        }
    }

    private void playerSitOut(Player player) {
        table.handleFold(player);
    }

    private void blindPlayerSitOut(Player player) {
        if (player == table.getSmallBlindPlayer()){
            table.smallBlindSitOut();
        }else if (player == table.getBigBlindPlayer()){
            table.bigBlindSitOut();
        }
    }
}