package org.isma.poker.game;

import org.apache.log4j.Logger;
import org.isma.poker.factory.IDeckFactory;
import org.isma.poker.game.factory.ITableFactory;
import org.isma.poker.game.actions.PlayerAction;
import org.isma.poker.game.actions.PlayerBetListener;
import org.isma.poker.game.exceptions.InvalidPlayerBetException;
import org.isma.poker.game.model.InvalidPlayerTurnException;
import org.isma.poker.game.model.*;
import org.isma.poker.game.results.Results;
import org.isma.poker.game.step.InvalidStepActionException;
import org.isma.poker.game.step.PokerStepGame;
import org.isma.poker.game.step.Step;
import org.isma.poker.game.step.StepEnum;
import org.isma.poker.model.Deck;

import java.util.List;

import static org.isma.poker.factory.IDeckFactory.DeckTypeEnum.FIFTY_TWO_CARDS_DECK;
import static org.isma.poker.game.PokerActionEnum.*;
import static org.isma.poker.game.exceptions.InvalidPlayerBetException.InvalidBetEnum.*;

public class GameSession implements PlayerBetListener, PokerStepGame, PokerGameState {
    private static final Logger LOG = Logger.getLogger(GameSession.class);
    private final AvailableActionsEvaluator availableActionsEvaluator = new AvailableActionsEvaluator();
    private final GameConfiguration configuration;
    private final IDeckFactory deckFactory;

    private Deck deck;
    private final GameStep gameStep;
    private final Table table;
    private final TableInfos tableInfos;


    public GameSession(GameConfiguration configuration, IDeckFactory deckFactory, ITableFactory tableFactory) {
        this.configuration = configuration;
        this.deckFactory = deckFactory;
        this.table = tableFactory.buildTable();
        this.tableInfos = new TableInfos(table, configuration);
        this.gameStep = new GameStep(this);
    }

    public void start() {
        deck = deckFactory.buildPokerDeck(FIFTY_TWO_CARDS_DECK);
    }


    public TableInfos getTableInfos() {
        return tableInfos;
    }

    // ********************************************************************************
    // **** Facade avec table
    // ********************************************************************************
    public void addPlayer(Player player) {
        table.add(player);
    }

    public Results buildResults() throws InvalidStepActionException {
        return new Results(table.getPot(), table.getAlivePlayers());
    }


    // ********************************************************************************
    // **** Facade avec step
    // ********************************************************************************
    public boolean isStepOver() {
        return gameStep.isOver();
    }


    public Step getStep() {
        return gameStep.getStep();
    }

    public boolean isRoundOver() {
        return gameStep.isOver() && gameStep.getStep() == StepEnum.END;
    }

    public void nextStep() throws Exception {
        if (table.isRoundOver()) {
            gameStep.gotoEnd();
        } else {
            gameStep.nextStep();
        }
    }

    // ********************************************************************************
    // **** PlayerBetListener implementation
    // ********************************************************************************


    @Override
    public void paySmallBlind(Player player) {
        table.addSmallBlind(configuration);
        int paid = PlayerAction.payChips(player, configuration.getSmallBlindAmount());
        table.addToPot(player, paid);
    }

    @Override
    public void payBigBlind(Player player) {
        table.addBigBlind(configuration);
        int paid = PlayerAction.payChips(player, configuration.getBigBlindAmount());
        table.addToPot(player, paid);
        gameStep.finish();
    }

    @Override
    public boolean buy(Player player, int chips) {
        //TODO
        return true;
    }

    @Override
    public void check(Player player) throws InvalidPlayerBetException, InvalidPlayerTurnException {
        new AbstractPlayerAction(this, CHECK) {
            @Override
            protected void doAction(Player player) throws InvalidPlayerBetException {
                int currentPlayerBet = table.getCurrentStepBet(player);
                int remainingToPay = table.getCurrentBet() - currentPlayerBet;
                if (remainingToPay != 0) {
                    throw new InvalidPlayerBetException(CHECK_FORBIDDEN);
                }
            }
        }.execute(player);
    }

    @Override
    public void call(Player player) throws InvalidPlayerTurnException, InvalidPlayerBetException {
        new AbstractPlayerAction(this, CALL) {
            @Override
            protected void doAction(Player player) throws InvalidPlayerBetException {
                int currentPlayerBet = table.getCurrentStepBet(player);
                int remainingToPay = table.getCurrentBet() - currentPlayerBet;
                int paid = PlayerAction.payChips(player, remainingToPay);
                table.addToPot(player, paid);
            }
        }.execute(player);
    }


    @Override
    public void bet(Player player, final int chips) throws InvalidPlayerBetException, InvalidPlayerTurnException {
        new AbstractPlayerAction(this, BET) {
            @Override
            protected void doAction(Player player) throws InvalidPlayerBetException {
                if (table.getCurrentBet() != 0) {
                    throw new InvalidPlayerBetException(BET_FORBIDDEN);
                }
                if (chips < configuration.getMinimumBetAllowed()) {
                    throw new InvalidPlayerBetException(TO_CHEAP_BET);
                }
                if (player.getChips() < chips) {
                    throw new InvalidPlayerBetException(NOT_ENOUGH_CHIPS);
                }
                PlayerAction.payChips(player, chips);
                table.addToPot(player, chips);
                table.addBet(chips);
            }
        }.execute(player);
    }

    @Override
    public void raise(Player player, final int chips) throws InvalidPlayerBetException, InvalidPlayerTurnException {
        new AbstractPlayerAction(this, RAISE) {
            @Override
            protected void doAction(Player player) throws InvalidPlayerBetException {
                int currentBet = table.getCurrentBet();
                int currentPaid = table.getCurrentStepBet(player);
                if (table.getRaisesRemaining() == 0) {
                    throw new InvalidPlayerBetException(RAISE_FORBIDDEN);
                }
                if (currentBet == 0) {
                    throw new InvalidPlayerBetException(RAISE_FORBIDDEN);
                }
                if (chips < configuration.getMinimumBetAllowed()) {
                    throw new InvalidPlayerBetException(TO_CHEAP_BET);
                }
                int raiseCost = (currentBet - currentPaid) + chips;
                if (player.getChips() < raiseCost) {
                    throw new InvalidPlayerBetException(NOT_ENOUGH_CHIPS);
                }
                table.decreaseRaiseRemainings();
                PlayerAction.payChips(player, raiseCost);
                table.addToPot(player, raiseCost);
                table.addBet(chips);
            }
        }.execute(player);
    }

    @Override
    public void allIn(Player player) throws InvalidPlayerBetException, InvalidPlayerTurnException {
        new AbstractPlayerAction(this, ALLIN) {
            @Override
            protected void doAction(Player player) throws InvalidPlayerBetException {
                int remainingChips = player.getChips();
                if (remainingChips == 0) {
                    throw new InvalidPlayerBetException(ALLIN_FORBIDDEN);
                }
                PlayerAction.payChips(player, remainingChips);
                table.addToPot(player, remainingChips);
                int currentBet = table.getCurrentBet();
                int currentPaid = table.getCurrentStepBet(player);
                int raiseCost = (currentPaid - currentBet) + remainingChips;
                if (raiseCost > 0) {
                    table.addBet(raiseCost);
                }
            }
        }.execute(player);
    }

    @Override
    public void show(Player player) throws InvalidPlayerTurnException, InvalidPlayerBetException {
        new AbstractPlayerAction(this, SHOW) {
            @Override
            protected void doAction(Player player) throws InvalidPlayerBetException {
                LOG.warn("GameSession.show not yet implemented...");
            }
        }.execute(player);
    }


    @Override
    public void fold(Player player) throws InvalidPlayerBetException, InvalidPlayerTurnException {
        new AbstractPlayerAction(this, FOLD) {
            @Override
            protected void doAction(Player player) throws InvalidPlayerBetException {
                table.removeFromRound(player);
            }
        }.execute(player);
    }


    // ********************************************************************************
    // **** Step executions
    // ********************************************************************************
    public void executeFirstBetStep() {
        table.prepareNextBetStep(true);
    }

    public void executeBetStep() {
        table.prepareNextBetStep(false);
    }

    public void executeBlindStep() {
        table.prepareNewRound();
        PlayerAction.paySmallBlind(table.getSmallBlindPlayer(), this);
        PlayerAction.payBigBlind(table.getBigBlindPlayer(), this);
    }

    public void executeHandsDealingStep() {
        table.prepareDealHandsStep(deck);
        gameStep.finish();
    }

    public void executeCommunityCardsDealingStep(int number) {
        table.prepapreDealCommunityCardStep(deck, number);
        gameStep.finish();
    }

    public void executeShowDownStep() {
        table.prepareShowDown();
    }

    public void executeEndStep() throws InvalidStepActionException {
        Results results = buildResults();
        for (Winner winner : results.getWinners()) {
            PlayerAction.win(winner.getPlayer(), winner.getPrize());
        }
        gameStep.finish();
    }

    // ********************************************************************************
    public Deck getDeck() {
        return deck;
    }

    public Pot getPot() {
        return table.getPot();
    }

    //TODO a tester
    public List<PokerActionEnum> getAvailablesActions(Player player) {
        return availableActionsEvaluator.evaluate(this, player);
    }

    private abstract class AbstractPlayerAction {
        private final GameSession gameSession;
        private final PokerActionEnum action;

        protected AbstractPlayerAction(GameSession gameSession, PokerActionEnum action) {
            this.gameSession = gameSession;
            this.action = action;
        }

        protected void checkAvailableAction(Player player) throws InvalidPlayerTurnException {
            if (action != SIT_OUT && table.getCurrentPlayer() != player) {
                throw new InvalidPlayerTurnException(player);
            }
            if (!gameSession.availableActionsEvaluator.evaluate(gameSession, player).contains(action)) {
                throw new IllegalArgumentException("invalid action :" + action);
            }
        }


        void execute(Player player) throws InvalidPlayerTurnException, InvalidPlayerBetException {
            checkAvailableAction(player);
            doAction(player);
            endPlayerTurn();
        }

        protected void endPlayerTurn() {
            if (!table.prepareNextPlayer()) {
                gameStep.finish();
            }

        }

        protected abstract void doAction(Player player) throws InvalidPlayerBetException;
    }
}
