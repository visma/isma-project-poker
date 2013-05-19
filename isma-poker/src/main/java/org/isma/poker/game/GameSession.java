package org.isma.poker.game;

import org.apache.log4j.Logger;
import org.isma.poker.factory.IDeckFactory;
import org.isma.poker.game.actions.*;
import org.isma.poker.game.event.*;
import org.isma.poker.game.exceptions.PokerGameException;
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

public class GameSession implements PlayerBetListener, PokerStepGame {
    private static final Logger LOG = Logger.getLogger(GameSession.class);
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
        //deck = deckFactory.buildPokerDeck(FIFTY_TWO_CARDS_DECK);
    }


    public void beginRoundIfPossible() throws InvalidStepActionException {
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


    // ********************************************************************************
    // **** Facade avec table
    // ********************************************************************************
    public TableInfos getTableInfos() {
        return tableInfos;
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

    public void nextStep() throws InvalidStepActionException {
        if (table.isRoundOver()) {
            gameStep.gotoEnd();
        } else {
            gameStep.nextStep();
        }
    }

    public void finishStep() throws InvalidStepActionException {
        gameStep.finish();
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
    public void sitIn(Player player) throws PokerGameException {
        notify(new PlayerSitEvent(player));
        table.add(player);
        beginRoundIfPossible();
    }


    @Override
    public void paySmallBlind(Player player) throws PokerGameException {
        new SmallBlindAction(this, table, configuration).execute(player);
    }

    @Override
    public void payBigBlind(Player player) throws PokerGameException {
        new BigBlindAction(this, table, configuration).execute(player);
    }


    @Override
    public void check(Player player) throws PokerGameException {
        new CheckAction(this, table).execute(player);
    }

    @Override
    public void call(Player player) throws PokerGameException {
        new CallAction(this, table).execute(player);
    }

    @Override
    public void bet(Player player, final int chips) throws PokerGameException {
        new BetAction(this, table, chips, configuration.getMinimumBetAllowed()).execute(player);
    }

    @Override
    public void raise(Player player, final int additionalChips) throws PokerGameException {
        new RaiseAction(this, table, additionalChips, configuration.getMinimumBetAllowed()).execute(player);
    }

    @Override
    public void allIn(Player player) throws PokerGameException {
        try {
            new AllinAction(this, table).execute(player);
        } catch (Exception e) {
            throw new PokerGameException(e);
        }
    }

    @Override
    public void show(Player player) throws PokerGameException {
        try {
            new ShowAction(this, table).execute(player);
        } catch (PokerGameException e) {
            throw new PokerGameException(e);
        }
    }


    @Override
    public void fold(Player player) throws PokerGameException {
        new FoldAction(this, table).execute(player);
    }


    // ********************************************************************************
    // **** Step executions
    // ********************************************************************************

    public void executeFirstBetStep() throws InvalidStepActionException {
        LOG.debug("executeFirstBetStep");
        if (table.getAliveWithChipsPlayers().size() > 1) {
            table.prepareNextBetStep(true);
        } else {
            nextStep();
        }
    }

    public void executeBetStep() throws InvalidStepActionException {
        LOG.debug("executeBetStep");
        if (table.getAliveWithChipsPlayers().size() > 1) {
            table.prepareNextBetStep(false);
        } else {
            nextStep();
        }
    }

    public void executeBlindStep() {
        table.prepareBlindsStep();
    }

    public void executeHandsDealingStep() throws InvalidStepActionException {
        new AbstractDealerAction() {
            @Override
            protected void doAction() {
                LOG.debug("prepare new deck and shuffle it");
                deck = deckFactory.buildPokerDeck(FIFTY_TWO_CARDS_DECK);
                deck.shuffle();

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
            winner.getPlayer().setChips(winner.getPlayer().getChips() + winner.getPrize());
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

    public void notify(GameEvent event) {
        event.log();
        for (GameEventListener eventListener : eventListeners) {
            eventListener.add(event);
        }
    }


    public abstract class AbstractDealerAction {
        void execute() throws InvalidStepActionException {
            Step cloneStep = GameSession.this.gameStep.getStep();
            LOG.debug(format("execute(%s).begin()", cloneStep));
            doAction();
            GameSession.this.finishStep();
            LOG.debug(format("execute(%s).end()", cloneStep));
        }

        protected abstract void doAction();
    }
}
