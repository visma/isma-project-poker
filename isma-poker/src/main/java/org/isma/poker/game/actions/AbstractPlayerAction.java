package org.isma.poker.game.actions;

import org.apache.log4j.Logger;
import org.isma.poker.game.exceptions.InvalidPlayerBetException;
import org.isma.poker.game.model.InvalidPlayerTurnException;
import org.isma.poker.game.model.Player;
import org.isma.poker.game.model.Table;
import org.isma.poker.game.step.InvalidStepActionException;
import org.isma.poker.game.step.PokerStepGame;
import org.isma.poker.game.step.Step;
import org.isma.poker.game.step.StepEnum;

import static java.lang.String.format;
import static org.isma.poker.game.actions.PokerActionEnum.SIT_OUT;
import static org.isma.poker.game.step.StepEnum.SHOWDOWN;

public abstract class AbstractPlayerAction {
    protected static final Logger LOG = Logger.getLogger(AbstractPlayerAction.class);
    private static final AvailableActionsEvaluator AVAILABLE_ACTIONS_EVALUATOR = new AvailableActionsEvaluator();

    private final PokerActionEnum action;
    protected final PokerStepGame gameSession;
    protected final Table table;


    AbstractPlayerAction(PokerActionEnum action, PokerStepGame gameSession, Table table) {
        this.action = action;
        this.gameSession = gameSession;
        this.table = table;
    }

    private void checkAvailableAction(Player player) throws InvalidPlayerTurnException {
        if (action != SIT_OUT && table.getCurrentPlayer() != player) {
            throw new InvalidPlayerTurnException(player);
        }
        if (!AVAILABLE_ACTIONS_EVALUATOR.evaluate(gameSession, player).contains(action)) {
            throw new IllegalArgumentException("invalid action :" + action);
        }
    }


    public void execute(Player player) throws InvalidPlayerTurnException, InvalidPlayerBetException, InvalidStepActionException {
        Step cloneStep = gameSession.getStep();
        LOG.debug(format("execute(%s, %s).begin(%s)",
                player.getNickname(),
                action,
                cloneStep));
        checkAvailableAction(player);
        doAction(player);
        endPlayerTurn();
        LOG.debug(format("execute(%s, %s).end(%s)",
                player.getNickname(),
                action,
                cloneStep));
    }

    private void endPlayerTurn() throws InvalidStepActionException {
        if (table.isRoundOver()) {
            gameSession.gotoEnd();
        } else {
            StepEnum step = (StepEnum) gameSession.getStep();
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
        gameSession.finishStep();
    }

    protected abstract void doAction(Player player) throws InvalidPlayerBetException, InvalidStepActionException;
}
