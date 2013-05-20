package org.isma.poker.game.actions;

import org.apache.log4j.Logger;
import org.isma.poker.game.exceptions.InvalidPlayerBetException;
import org.isma.poker.game.exceptions.PokerGameException;
import org.isma.poker.game.model.InvalidPlayerTurnException;
import org.isma.poker.game.model.Player;
import org.isma.poker.game.model.Table;
import org.isma.poker.game.step.InvalidStepActionException;
import org.isma.poker.game.step.PokerActionStepGame;
import org.isma.poker.game.step.Step;
import org.isma.poker.game.step.StepEnum;

import static java.lang.String.format;
import static org.isma.poker.game.actions.PokerActionEnum.SIT_OUT;
import static org.isma.poker.game.step.StepEnum.SHOWDOWN;

public abstract class AbstractPlayerAction {
    protected static final Logger LOG = Logger.getLogger(AbstractPlayerAction.class);
    private static final AvailableActionsEvaluator AVAILABLE_ACTIONS_EVALUATOR = new AvailableActionsEvaluator();

    private final PokerActionEnum action;
    protected final PokerActionStepGame game;
    protected final Table table;


    AbstractPlayerAction(PokerActionEnum action, PokerActionStepGame game, Table table) {
        this.action = action;
        this.game = game;
        this.table = table;
    }

    private void checkAvailableAction(Player player) throws InvalidPlayerTurnException {
        if (action != SIT_OUT && table.getCurrentPlayer() != player) {
            throw new InvalidPlayerTurnException(player);
        }
        if (!AVAILABLE_ACTIONS_EVALUATOR.evaluate(game, player).contains(action)) {
            throw new IllegalArgumentException("invalid action :" + action);
        }
    }


    public void execute(Player player) throws PokerGameException {
        Step cloneStep = game.getStep();
        LOG.debug(format("execute(%s, %s).begin(%s)",
                player.getNickname(),
                action,
                cloneStep));
        checkAvailableAction(player);
        doAction(player);
        endPlayerTurn(player);
        LOG.debug(format("execute(%s, %s).end(%s)",
                player.getNickname(),
                action,
                cloneStep));
    }

    protected void endPlayerTurn(Player player) throws InvalidStepActionException {
        if (table.isRoundOver()) {
            game.gotoEndStep();
        } else {
            StepEnum step = (StepEnum) game.getStep();
            if (step != SHOWDOWN) {
                if (!table.prepareNextPlayer(false)) {
                    endStep();
                }
            } else {
                if (!table.prepareNextPlayer(true)) {
                    endStep();
                }
            }
        }
    }

    private void endStep() throws InvalidStepActionException {
        game.finishStep();
    }

    protected abstract void doAction(Player player) throws InvalidPlayerBetException, InvalidStepActionException;
}
