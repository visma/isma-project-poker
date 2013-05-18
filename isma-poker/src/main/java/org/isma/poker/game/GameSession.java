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

import static java.lang.String.format;
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

    public void nextStep() throws InvalidStepActionException {
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
    public boolean buy(Player player, int chips) {
        player.setChips(player.getChips() + chips);
        return true;
    }

    @Override
    public void sitIn(Player player) throws InvalidStepActionException {
        notify(new PlayerSitEvent(player));
        table.add(player);
        startRoundIfPrerequisites();
    }


    @Override
    public void paySmallBlind(Player player) throws InvalidPlayerTurnException, InvalidPlayerBetException, InvalidStepActionException {
        new AbstractPlayerAction(PAY_SMALL_BLIND) {
            @Override
            protected void doAction(Player player) throws InvalidPlayerBetException {
                GameSession.this.notify(new BlindEvent(player, true, configuration.getSmallBlindAmount()));
                table.addSmallBlind(configuration);
                int paid = PlayerAction.payChips(player, configuration.getSmallBlindAmount());
                table.addToPot(player, paid);
            }
        }.execute(player);
    }

    @Override
    public void payBigBlind(Player player) throws InvalidStepActionException, InvalidPlayerBetException, InvalidPlayerTurnException {
        new AbstractPlayerAction(PAY_BIG_BLIND) {
            @Override
            protected void doAction(Player player) throws InvalidPlayerBetException, InvalidStepActionException {
                GameSession.this.notify(new BlindEvent(player, false, configuration.getBigBlindAmount()));
                table.addBigBlind(configuration);
                int paid = PlayerAction.payChips(player, configuration.getBigBlindAmount());
                table.addToPot(player, paid);
            }
        }.execute(player);
    }


    @Override
    public void check(Player player) throws InvalidPlayerBetException, InvalidPlayerTurnException, InvalidStepActionException {
        new AbstractPlayerAction(CHECK) {
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
        new AbstractPlayerAction(CALL) {
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
        new AbstractPlayerAction(BET) {
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
    public void raise(Player player, final int additionalChips) throws InvalidPlayerBetException, InvalidPlayerTurnException, InvalidStepActionException {
        new AbstractPlayerAction(RAISE) {
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
                if (additionalChips < configuration.getMinimumBetAllowed()) {
                    throw new InvalidPlayerBetException(TO_CHEAP_BET);
                }
                int raiseCost = (currentBet - currentPaid) + additionalChips;
                if (player.getChips() < raiseCost) {
                    throw new InvalidPlayerBetException(NOT_ENOUGH_CHIPS);
                }
                table.decreaseRaiseRemainings();
                PlayerAction.payChips(player, raiseCost);
                table.addToPot(player, raiseCost);
                table.addBet(additionalChips);
            }
        }.execute(player);
    }

    @Override
    public void allIn(Player player) throws InvalidPlayerBetException, InvalidPlayerTurnException, InvalidStepActionException {
        new AbstractPlayerAction(ALLIN) {
            @Override
            protected void doAction(Player player) throws InvalidPlayerBetException {
                int currentBet = table.getCurrentBet();
                int currentPaid = table.getCurrentStepBet(player);
                int additionalChips = player.getChips() - currentBet;
                int toPay = player.getChips();
                //additionalChips = additionalChips < 0 ? 0 : additionalChips;

                LOG.debug(format("currentBet : %s", currentBet));
                LOG.debug(format("currentPaid : %s", currentPaid));
                LOG.debug(format("additionalChips : %s", additionalChips));
                LOG.debug(format("toPay : %s", toPay));

                if (additionalChips > 0 && table.getRaisesRemaining() == 0) {
                    throw new InvalidPlayerBetException(RAISE_FORBIDDEN);
                }
                int raiseCost = (currentBet - currentPaid) + additionalChips;
                LOG.debug(format("raiseCost : %s", raiseCost));
                if (raiseCost > 0) {
                    table.decreaseRaiseRemainings();
                }
                PlayerAction.payChips(player, toPay);
                table.addToPot(player, toPay);
                if (additionalChips > 0) {
                    table.addBet(additionalChips);
                }
            }
        }.execute(player);
    }

    @Override
    public void show(Player player) throws
            InvalidPlayerTurnException, InvalidPlayerBetException, InvalidStepActionException {
        new AbstractPlayerAction(SHOW) {
            @Override
            protected void doAction(Player player) throws InvalidPlayerBetException {
                GameSession.this.notify(new ShowEvent(player));
            }
        }.execute(player);
    }


    @Override
    public void fold(Player player) throws
            InvalidPlayerBetException, InvalidPlayerTurnException, InvalidStepActionException {
        new AbstractPlayerAction(FOLD) {
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
        deck = deckFactory.buildPokerDeck(FIFTY_TWO_CARDS_DECK);
        deck.shuffle();
        LOG.debug("prepare new deck and shuffle it");
        table.prepareBlindsStep();
    }

    public void executeHandsDealingStep() throws InvalidStepActionException {
        new AbstractDealerAction() {
            @Override
            protected void doAction() {
                table.prepareDealHoleCardsStep(deck);
                for (Player player : table.getAlivePlayers()) {
                    GameSession.this.notify(new HoldeCardEvent(player));
                }
            }
        }.execute();
    }

    public void executeCommunityCardsDealingStep(final int number) throws InvalidStepActionException {
        new AbstractDealerAction() {
            @Override
            protected void doAction() {
                List<Card> newCards = table.prepapreDealCommunityCardStep(deck, number);
                for (Card newCard : newCards) {
                    GameSession.this.notify(new CommunityCardEvent(newCard));
                }
            }
        }.execute();
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

    public void addEventListener(GameEventListener eventListener) {
        eventListeners.add(eventListener);
    }

    private void notify(GameEvent gameEvent) {
        gameEvent.log();
        for (GameEventListener eventListener : eventListeners) {
            eventListener.add(gameEvent);
        }
    }


    public abstract class AbstractPlayerAction {
        private final GameSession gameSession;
        private final PokerActionEnum action;

        AbstractPlayerAction(PokerActionEnum action) {
            this.gameSession = GameSession.this;
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

        void endPlayerTurn() throws InvalidStepActionException {
            if (table.isRoundOver()) {
                gameSession.executeEndStep();
                table.prepareBlindsStep();
            } else if (!table.prepareNextPlayer(gameStep.getStep())) {
                gameStep.finish();
                if (getStep() == StepEnum.END) {
                    startRoundIfPrerequisites();
                }
            }
        }

        protected abstract void doAction(Player player) throws InvalidPlayerBetException, InvalidStepActionException;
    }

    public abstract class AbstractDealerAction {
        void execute() throws InvalidStepActionException {
            Step cloneStep = GameSession.this.gameStep.getStep();
            LOG.debug(format("execute(%s).begin()", cloneStep));
            doAction();
            GameSession.this.gameStep.finish();
            LOG.debug(format("execute(%s).end()", cloneStep));
        }

        protected abstract void doAction();
    }
}
