package org.isma.poker.game;

import org.apache.log4j.Logger;
import org.isma.poker.factory.IDeckFactory;
import org.isma.poker.game.actions.PlayerAction;
import org.isma.poker.game.actions.PlayerBetListener;
import org.isma.poker.game.event.*;
import org.isma.poker.game.exceptions.InvalidPlayerBetException;
import org.isma.poker.game.factory.ITableFactory;
import org.isma.poker.game.model.*;
import org.isma.poker.game.results.Results;
import org.isma.poker.game.step.InvalidStepActionException;
import org.isma.poker.game.step.PokerStepGame;
import org.isma.poker.game.step.Step;
import org.isma.poker.game.step.StepEnum;
import org.isma.poker.model.Card;
import org.isma.poker.model.Deck;

import java.util.ArrayList;
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
    private final List<GameEventListener> eventListeners = new ArrayList<GameEventListener>();

    //Bidouille pour tests
    private int firstRoundMinimumPlayers;

    public GameSession(GameConfiguration configuration, IDeckFactory deckFactory, ITableFactory tableFactory) {
        this.configuration = configuration;
        this.deckFactory = deckFactory;
        this.table = tableFactory.buildTable();
        this.tableInfos = new TableInfos(table, configuration);
        this.gameStep = new GameStep(this);
    }

    public void init(int firstRoundMinimumPlayers) {
        this.firstRoundMinimumPlayers = firstRoundMinimumPlayers;
        deck = deckFactory.buildPokerDeck(FIFTY_TWO_CARDS_DECK);
    }


    private void startRoundIfPrerequisites() throws InvalidStepActionException {
        int playersWithCash = 0;
        for (PlayerInfos player : tableInfos.getPlayersInfos()) {
            if (player.getPlayer().hasChips()) {
                playersWithCash++;
            }
        }
        if (playersWithCash >= firstRoundMinimumPlayers && gameStep.getStep() == StepEnum.END) {
            firstRoundMinimumPlayers = 2;
            nextStep();
        }
    }


    public TableInfos getTableInfos() {
        return tableInfos;
    }

    // ********************************************************************************
    // **** Facade avec table
    // ********************************************************************************


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

    private void nextStep() throws InvalidStepActionException {
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
    public void sitIn(Player player) throws InvalidStepActionException {
        notify(new PlayerSitEvent(player));
        table.add(player);
        startRoundIfPrerequisites();
    }

    @Override
    public void paySmallBlind(Player player) {
        notify(new BlindEvent(player, true, configuration.getSmallBlindAmount()));
        table.addSmallBlind(configuration);
        int paid = PlayerAction.payChips(player, configuration.getSmallBlindAmount());
        table.addToPot(player, paid);
    }

    @Override
    public void payBigBlind(Player player) throws InvalidStepActionException {
        notify(new BlindEvent(player, false, configuration.getBigBlindAmount()));
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
    public void check(Player player) throws InvalidPlayerBetException, InvalidPlayerTurnException, InvalidStepActionException {
        new AbstractPlayerAction(this, CHECK) {
            @Override
            protected void doAction(Player player) throws InvalidPlayerBetException {
                GameSession.this.notify(new CheckEvent(player));
                int currentPlayerBet = table.getCurrentStepBet(player);
                int remainingToPay = table.getCurrentBet() - currentPlayerBet;
                if (remainingToPay != 0) {
                    throw new InvalidPlayerBetException(CHECK_FORBIDDEN);
                }
            }
        }.execute(player);
    }

    @Override
    public void call(Player player) throws InvalidPlayerTurnException, InvalidPlayerBetException, InvalidStepActionException {
        new AbstractPlayerAction(this, CALL) {
            @Override
            protected void doAction(Player player) throws InvalidPlayerBetException {
                int currentPlayerBet = table.getCurrentStepBet(player);
                int remainingToPay = table.getCurrentBet() - currentPlayerBet;
                int paid = PlayerAction.payChips(player, remainingToPay);
                table.addToPot(player, paid);
                GameSession.this.notify(new CallEvent(player));
            }
        }.execute(player);
    }


    @Override
    public void bet(Player player, final int chips) throws InvalidPlayerBetException, InvalidPlayerTurnException, InvalidStepActionException {
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
                GameSession.this.notify(new BetEvent(player, chips));
            }
        }.execute(player);
    }

    @Override
    public void raise(Player player, final int chips) throws InvalidPlayerBetException, InvalidPlayerTurnException, InvalidStepActionException {
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
    public void allIn(Player player) throws InvalidPlayerBetException, InvalidPlayerTurnException, InvalidStepActionException {
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
    public void show(Player player) throws InvalidPlayerTurnException, InvalidPlayerBetException, InvalidStepActionException {
        new AbstractPlayerAction(this, SHOW) {
            @Override
            protected void doAction(Player player) throws InvalidPlayerBetException {
                GameSession.this.notify(new ShowEvent(player));
            }
        }.execute(player);
    }


    @Override
    public void fold(Player player) throws InvalidPlayerBetException, InvalidPlayerTurnException, InvalidStepActionException {
        new AbstractPlayerAction(this, FOLD) {
            @Override
            protected void doAction(Player player) throws InvalidPlayerBetException, InvalidStepActionException {
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

    public void executeBlindStep() throws InvalidStepActionException {
        table.prepareNewRound();
        PlayerAction.paySmallBlind(table.getSmallBlindPlayer(), this);
        PlayerAction.payBigBlind(table.getBigBlindPlayer(), this);
    }

    public void executeHandsDealingStep() throws InvalidStepActionException {
        table.prepareDealHandsStep(deck);
        gameStep.finish();
    }

    public void executeCommunityCardsDealingStep(int number) throws InvalidStepActionException {
        List<Card> newCards = table.prepapreDealCommunityCardStep(deck, number);
        for (Card newCard : newCards) {
            notify(new CommunityCardEvent(newCard));
        }
        gameStep.finish();
    }

    public void executeShowDownStep() {
        table.prepareShowDown();
    }

    public void executeEndStep() throws InvalidStepActionException {
        notify(new RoundEndEvent(new Results(table.getPot(), table.getAlivePlayers())));
        for (Winner winner : new Results(table.getPot(), table.getAlivePlayers()).getWinners()) {
            PlayerAction.win(winner.getPlayer(), winner.getPrize());
        }
        gameStep.gotoEnd();
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

    public void addEventListener(GameEventListener eventListener) {
        eventListeners.add(eventListener);
    }

    private void notify(GameEvent gameEvent) {
        LOG.info("gameEvent : " + gameEvent.getDescription());
        for (GameEventListener eventListener : eventListeners) {
            eventListener.add(gameEvent);
        }
    }


    private abstract class AbstractPlayerAction {
        private final Logger LOG = Logger.getLogger(AbstractPlayerAction.class);
        private final GameSession gameSession;
        private final PokerActionEnum action;

        AbstractPlayerAction(GameSession gameSession, PokerActionEnum action) {
            this.gameSession = gameSession;
            this.action = action;
        }

        void checkAvailableAction(Player player) throws InvalidPlayerTurnException {
            if (action != SIT_OUT && table.getCurrentPlayer() != player) {
                throw new InvalidPlayerTurnException(player);
            }
            if (!gameSession.availableActionsEvaluator.evaluate(gameSession, player).contains(action)) {
                throw new IllegalArgumentException("invalid action :" + action);
            }
        }


        void execute(Player player) throws InvalidPlayerTurnException, InvalidPlayerBetException, InvalidStepActionException {
            checkAvailableAction(player);
            doAction(player);
            endPlayerTurn();
        }

        void endPlayerTurn() throws InvalidStepActionException {
            if (table.isRoundOver()) {
                gameSession.executeEndStep();
                table.prepareNewRound();
                startRoundIfPrerequisites();
            } else if (!table.prepareNextPlayer()) {
                gameStep.finish();
                //TODO faire mieux (?)
                if (getStep() == StepEnum.END) {
//                    gameSession.executeEndStep();
//                    table.prepareNewRound();
                    startRoundIfPrerequisites();
                }
            }
        }

        protected abstract void doAction(Player player) throws InvalidPlayerBetException, InvalidStepActionException;
    }
}
