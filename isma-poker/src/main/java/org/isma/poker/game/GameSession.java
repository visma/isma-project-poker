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
import org.isma.poker.game.step.PokerActionStepGame;
import org.isma.poker.game.step.PokerStepRunner;
import org.isma.poker.game.step.Step;
import org.isma.poker.model.Card;
import org.isma.poker.model.Deck;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;
import static org.isma.poker.factory.IDeckFactory.DeckTypeEnum.FIFTY_TWO_CARDS_DECK;
import static org.isma.poker.game.step.StepEnum.END;

public class GameSession implements PlayerActionListener, PokerActionStepGame, PokerStepRunner {
    private static final Logger LOG = Logger.getLogger(GameSession.class);
    private final GameConfiguration configuration;
    private final IDeckFactory deckFactory;

    private final List<GameEventListener> eventListeners = new ArrayList<GameEventListener>();

    private final GameStepRunner stepRunner;
    private final Table table;
    private final TableFacade tableFacade;
    private Deck deck;

    private int firstRoundMinimumPlayers;

    public GameSession(GameConfiguration configuration, IDeckFactory deckFactory, ITableFactory tableFactory) {
        this.configuration = configuration;
        this.deckFactory = deckFactory;
        this.table = tableFactory.buildTable();
        this.tableFacade = new TableFacade(table, configuration);
        this.stepRunner = new GameStepRunner(this);
    }

    public GameSession(GameConfiguration configuration,
                       IDeckFactory deckFactory,
                       ITableFactory tableFactory,
                       IGameStepRunnerFactory stepRunnerFactory) {
        this.configuration = configuration;
        this.deckFactory = deckFactory;
        this.table = tableFactory.buildTable();
        this.tableFacade = new TableFacade(table, configuration);
        this.stepRunner = stepRunnerFactory.buildStepRunner(this);
    }

    /**
     * Game doesn't begin until a minimum players is reached
     *
     * @param firstRoundMinimumPlayers
     */
    public void init(int firstRoundMinimumPlayers) {
        this.firstRoundMinimumPlayers = firstRoundMinimumPlayers;
    }


    public void beginRoundIfPossible() throws InvalidStepActionException {
        int playersWithCash = 0;
        for (PlayerInfos player : tableFacade.getPlayersInfos()) {
            if (player.getPlayer().hasChips()) {
                playersWithCash++;
            }
        }
        if (playersWithCash >= firstRoundMinimumPlayers && stepRunner.getStep() == END) {
            firstRoundMinimumPlayers = 2;
            nextStep();
            notifyEvent(new PlayerTurnEvent(tableFacade.getCurrentPlayer()));
        }
    }


    // ********************************************************************************
    // **** Facade avec table
    // ********************************************************************************
    public TableFacade getTableFacade() {
        return tableFacade;
    }


    // ********************************************************************************
    // **** Facade avec step
    // ********************************************************************************
    public boolean isStepOver() {
        return stepRunner.isOver();
    }

    public Step getStep() {
        return stepRunner.getStep();
    }

    public boolean isRoundOver() {
        return stepRunner.isOver() && stepRunner.getStep() == END;
    }

    private void nextStep() throws InvalidStepActionException {
        stepRunner.nextStep();
        notifyEvent(new NewStepEvent());
    }

    public void gotoEndStep() throws InvalidStepActionException {
        stepRunner.gotoEnd();
        notifyEvent(new NewStepEvent());
    }

    public void finishStep() throws InvalidStepActionException {
        stepRunner.finish();
        notifyEvent(new NewStepEvent());
    }

    @Override
    public void freeze() {
        stepRunner.freeze();
    }

    // ********************************************************************************
    // **** PlayerActionListener implementation
    // ********************************************************************************

    @Override
    public boolean buy(Player player, int chips) throws InvalidStepActionException {
        player.setChips(player.getChips() + chips);
        beginRoundIfPossible();

        notifyEvent(new BuyEvent(player, chips));
        return true;
    }

    @Override
    public void sitIn(Player player) throws PokerGameException {
        table.add(player);
        beginRoundIfPossible();

        notifyEvent(new PlayerSitInEvent(player));
    }

    @Override
    public void sitOut(Player player) throws PokerGameException {
        try {
            new SitOutAction(this, table).execute(player);
        } catch (Exception e) {
            throw new PokerGameException(e);
        }
    }

    @Override
    public void fold(Player player) throws PokerGameException {
        new FoldAction(this, table).execute(player);
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


    // ********************************************************************************
    // **** Step executions
    // ********************************************************************************

    public void executeShowDownStep() {
        table.prepareShowDown();
    }

    public void executeEndStep() throws InvalidStepActionException {
        LOG.debug("executeEndStep");
        Results results = new Results(table.getPot(), table.getAlivePlayers());
        notifyEvent(new RoundEndEvent(results));
        for (Winner winner : results.getWinners()) {
            winner.getPlayer().setChips(winner.getPlayer().getChips() + winner.getPrize());
        }
        table.endRound();
        beginRoundIfPossible();
    }

    public void executeFirstBetStep() throws InvalidStepActionException {
        LOG.debug("executeFirstBetStep");
        if (table.getAliveWithChipsPlayers().size() > 1) {
            table.prepareBetStep(true);
        } else {
            nextStep();
        }
    }

    public void executeBetStep() throws InvalidStepActionException {
        LOG.debug("executeBetStep");
        if (table.getAliveWithChipsPlayers().size() > 1) {
            table.prepareBetStep(false);
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
                    GameSession.this.notifyEvent(new HoldeCardEvent(player));
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
                    GameSession.this.notifyEvent(new CommunityCardEvent(newCard));
                }
            }
        }.execute();
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

    public void removeEventListener(GameEventListener eventListener) {
        eventListeners.remove(eventListener);
    }

    public void notifyEvent(GameEvent event) {
        event.log();
        for (GameEventListener eventListener : eventListeners) {
            eventListener.add(event);
        }
    }

    //TODO Test unitaire
    public boolean isFreeze() {
        return getStep() == END/* && tableFacade.getCurrentPlayer() == null*/;
    }

    public List<PokerActionEnum> getAvailableActions(Player player) {
        AvailableActionsEvaluator actionsEvaluator = new AvailableActionsEvaluator();
        return actionsEvaluator.evaluate(this, player);
    }

    public PlayerStatus getStatus(Player player) {
        if (isFreeze()){
            return PlayerStatus.WAITING;
        }
        if (player.isFold()){
            return PlayerStatus.FOLDED;
        }
        if (player == table.getCurrentPlayer() && table.getAlivePlayers().contains(player)){
            return PlayerStatus.PLAYING;
        }
        if (table.getAlivePlayers().contains(player)){
            return PlayerStatus.INGAME;
        }
        if (table.getAllPlayers().contains(player) && player.hasChips()){
            return PlayerStatus.WAITING;
        }
        if (table.getAllPlayers().contains(player) && !player.hasChips()){
            return PlayerStatus.OUT_OF_CASH;
        }
        throw new RuntimeException("TableFacade.getStatus : unable to determine status");
    }

    //TODO a mettre dans action comme les action joueurs
    public abstract class AbstractDealerAction {
        void execute() throws InvalidStepActionException {
            Step cloneStep = GameSession.this.stepRunner.getStep();
            LOG.debug(format("execute(%s).begin()", cloneStep));
            doAction();
            GameSession.this.finishStep();
            LOG.debug(format("execute(%s).end()", cloneStep));
        }

        protected abstract void doAction();
    }
}
