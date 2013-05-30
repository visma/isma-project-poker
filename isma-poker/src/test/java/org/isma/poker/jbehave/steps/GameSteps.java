package org.isma.poker.jbehave.steps;

import junit.framework.Assert;
import org.isma.poker.game.GameSession;
import org.isma.poker.game.actions.PlayerAction;
import org.isma.poker.game.actions.PokerActionEnum;
import org.isma.poker.game.event.RoundEndEvent;
import org.isma.poker.game.event.ShowEvent;
import org.isma.poker.game.exceptions.PokerGameException;
import org.isma.poker.game.factory.TableFactory;
import org.isma.poker.game.model.*;
import org.isma.poker.game.results.Results;
import org.isma.poker.game.step.StepEnum;
import org.isma.poker.game.test.TestResultEventListener;
import org.isma.poker.game.test.TestShowEventListner;
import org.isma.poker.mock.MockDeckFactory;
import org.isma.poker.model.Card;
import org.isma.poker.model.CommunityCards;
import org.isma.poker.model.Hand;
import org.isma.poker.model.HandEvaluation;
import org.jbehave.core.annotations.BeforeScenario;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import java.util.logging.Logger;

import static junit.framework.Assert.*;
import static org.isma.poker.jbehave.steps.GameSteps.GameContext.current;

public class GameSteps extends AbstractPokerSteps {
    private static final Logger LOGGER = Logger.getLogger(GameSteps.class.getName());

    @BeforeScenario
    public void initializeContext() throws InvalidGameConfigurationException {
        GameContext.initialize();
    }

    @Given("la table de jeu est vide, la partie commencera lorsque $minPlayers joueurs sont presents")
    public void givenTable(int minPlayers) throws Exception {
        assertTrue(current().game.getTableFacade().getPlayersInfos().isEmpty());
        current().game.init(minPlayers);
        current().showEventListener = new TestShowEventListner();
        current().resultEventListner = new TestResultEventListener();
        current().game.addEventListener(current().showEventListener);
        current().game.addEventListener(current().resultEventListner);
    }

    @Given("les blindes sont à $smallBlind/$bigBlind jetons")
    public void givenBlinds(int smallBlind, int bigBlind) throws Exception {
        Assert.assertEquals(smallBlind, current().game.getTableFacade().getSmallBlindAmount());
        Assert.assertEquals(bigBlind, current().game.getTableFacade().getBigBlindAmount());
    }

    @Given("les prochaines cartes du deck sont : $cards")
    public void givenDeck(Card... cards) throws Exception {
//        System.out.println("isma les prochaines cartes du deck sont : " + Arrays.asList(cards));
        current().deckFactory.clearForceHand();
        current().deckFactory.forceHands(cards);
    }

    @Given("le joueur $nickname dispose de $chips jetons et rentre dans la partie")
    public void givenPlayer(String nickname, int chips) throws Exception {
        Player player = new Player(nickname);
        player.setChips(chips);
        PlayerAction.sitIn(player, current().game);
    }

    @When("la partie démarre")
    public void givenStart() throws Exception {
        current().resultEventListner.clear();
    }

    @Given("un nouveau round va démarrer")
    public void givenRound() throws Exception {
        current().resultEventListner.clear();
        assertFalse(current().game.isFreeze());
    }

    @Then("la partie est en standby")
    public void thenFreeze() throws Exception {
        current().resultEventListner.clear();
        assertTrue(current().game.isFreeze());
        assertNull(current().game.getTableFacade().getCurrentPlayer());
    }


    @When("$nickname call")
    public void whenCall(String nickname) throws PokerGameException {
        Player player = getPlayer(nickname);
        PlayerAction.call(player, current().game);
    }

    @Then("etape en cours : $step (pot : $pot)")
    public void givenGameStep(StepEnum step, int pot) throws Exception {
        Assert.assertEquals(step, current().game.getStep());
        Assert.assertEquals(pot, current().game.getPot().getTotal());
    }

    @Then("$nickname est au bouton")
    public void givenButtonPlayer(String nickname) throws Exception {
        Assert.assertEquals(nickname, current().game.getTableFacade().getDealerInfos().getPlayer().getNickname());
    }

    @When("$nickname paie la petite blinde")
    public void givenSmallBlindPaid(String nickname) throws Exception {
        PlayerAction.paySmallBlind(getPlayer(nickname), current().game);
    }

    @When("$nickname paie la grosse blinde")
    public void whenBigBlindPaid(String nickname) throws Exception {
        PlayerAction.payBigBlind(getPlayer(nickname), current().game);
    }

    @Then("la mise en cours de $nickname est de $chips jetons")
    public void thenCurrentBet(String nickname, Integer chips) {
        Player player = getPlayer(nickname);
        assertEquals(chips.intValue(), current().game.getTableFacade().getCurrentStepBet(player));
    }
    @Then("le montant des encheres en cours sur ce tour de mise est de $chips jetons")
    public void thenCurrentTableBet(Integer chips){
        assertEquals(chips.intValue(), current().game.getTableFacade().getCurrentBet());
    }

    @Then("$nickname reçoit les cartes cachees : $card1, $card2")
    public void thenHoleCards(String nickname, Card card1, Card card2) throws Exception {
        Player player = getPlayer(nickname);
        assertEquals(2, player.getHand().size());
        assertEquals(card1, player.getHand().get(0));
        assertEquals(card2, player.getHand().get(1));
    }

    @Then("le montant de l'enchere en cours est de $chips jetons")
    public void thenCurrentBet(int chips) {
        assertEquals(chips, current().game.getTableFacade().getCurrentBet());
    }

    @Then("$nickname est le prochain joueur à parler")
    public void givenCurrentPlayer(String nickname) throws Exception {
        Assert.assertEquals(nickname, current().game.getTableFacade().getCurrentPlayerInfos().getPlayer().getNickname());
    }

    @When("$nickname effectue l'action : $action($chipsStr)")
    public void givenPlayerAction(String nickname, PokerActionEnum pokerAction, String chipsStr) throws Exception {
        GameSession game = current().game;
        Player player = getPlayer(nickname);
        Integer chips = chipsStr.trim().length() > 0 ? Integer.parseInt(chipsStr) : null;
        switch (pokerAction) {
            case CHECK:
                PlayerAction.check(player, game);
                break;
            case BET:
                PlayerAction.bet(player, game, chips);
                break;
            case CALL:
                PlayerAction.call(player, game);
                break;
            case RAISE:
                PlayerAction.raise(player, game, chips);
                break;
            case ALLIN:
                PlayerAction.allIn(player, game);
                break;
            case FOLD:
                PlayerAction.fold(player, game);
                break;
            case SIT_OUT:
                PlayerAction.sitOut(player, game);
                break;
            default:
                throw new Exception("action non gere dans le test :" + pokerAction);
        }
    }

    @Then("le croupier distribue le flop : $card1, $card2, $card3")
    public void givenFlop(Card card1, Card card2, Card card3) throws Exception {
        CommunityCards communityCards = current().game.getTableFacade().getCommunityCards();
        assertEquals(3, communityCards.size());
        assertEquals(card1, communityCards.get(0));
        assertEquals(card2, communityCards.get(1));
        assertEquals(card3, communityCards.get(2));
    }

    @Then("le croupier distribue le turn : $card1, $card2, $card3, $card4")
    public void givenTurn(Card card1, Card card2, Card card3, Card card4) throws Exception {
        CommunityCards communityCards = current().game.getTableFacade().getCommunityCards();
        assertEquals(4, communityCards.size());
        assertEquals(card1, communityCards.get(0));
        assertEquals(card2, communityCards.get(1));
        assertEquals(card3, communityCards.get(2));
        assertEquals(card4, communityCards.get(3));
    }

    @Then("le croupier distribue la river : $card1, $card2, $card3, $card4, $card5")
    public void givenRiver(Card card1, Card card2, Card card3, Card card4, Card card5) throws Exception {
        CommunityCards communityCards = current().game.getTableFacade().getCommunityCards();
        assertEquals(5, communityCards.size());
        assertEquals(card1, communityCards.get(0));
        assertEquals(card2, communityCards.get(1));
        assertEquals(card3, communityCards.get(2));
        assertEquals(card4, communityCards.get(3));
        assertEquals(card5, communityCards.get(4));
    }

    @When("$nickname montre $handEvaluation : $card1, $card2, $card3, $card4, $card5")
    public void givenShow(String nickname, HandEvaluation handEvaluation, Card card1, Card card2, Card card3, Card card4, Card card5) throws PokerGameException {
        GameSession game = current().game;
        Player player = getPlayer(nickname);

        PlayerAction.show(player, game);

        ShowEvent showEvent = (ShowEvent) current().showEventListener.poll();
        Hand hand = showEvent.getHand();
        assertEquals(nickname, showEvent.getPlayer());
        assertEquals(7, hand.size());
        assertEquals(handEvaluation, showEvent.getHandEvaluation());
        assertEquals(card1, hand.get(0));
        assertEquals(card2, hand.get(1));
        assertEquals(card3, hand.get(2));
        assertEquals(card4, hand.get(3));
        assertEquals(card5, hand.get(4));
    }

    @Then("$nickname est vainqueur sans rien montrer gagne $prize jetons")
    public void thenWinnerBeforeShowdown(String nickname, int prize) {
        RoundEndEvent event = (RoundEndEvent) current().resultEventListner.peek();
        Results results = event.getResults();
        for (Winner winner : results.getWinners()) {
            if (winner.getPlayer().getNickname().equals(nickname)) {
                //assertEquals(handEvaluation, winner.getHandEvaluation());
                assertEquals(prize, winner.getPrize());
                return;
            }
        }
        fail(nickname + " not win");
    }

    @Then("$nickname est vainqueur avec $handEvaluation et gagne $prize jetons")
    public void thenWinner(String nickname, HandEvaluation handEvaluation, int prize) {
        RoundEndEvent event = (RoundEndEvent) current().resultEventListner.peek();
        Results results = event.getResults();
        for (Winner winner : results.getWinners()) {
            if (winner.getPlayer().getNickname().equals(nickname)) {
                assertEquals(handEvaluation, winner.getHandEvaluation());
                assertEquals(prize, winner.getPrize());
                return;
            }
        }
        fail(nickname + " not win");
    }

    @Then("$nickname a perdu avec $handEvaluation")
    public void thenLoser(String nickname, HandEvaluation handEvaluation) {
        RoundEndEvent event = (RoundEndEvent) current().resultEventListner.peek();
        Results results = event.getResults();
        for (Loser loser : results.getLosers()) {
            if (loser.getPlayer().getNickname().equals(nickname)) {
                assertEquals(handEvaluation, loser.getHandEvaluation());
                return;
            }
        }
        fail(nickname + " not lose");
    }

    @Then("$nickname a $chips jetons")
    public void givenChips(String nickname, int chips) {
        Player player = getPlayer(nickname);
        assertEquals(chips, player.getChips());
    }

    public static class GameContext {
        private static ThreadLocal<GameContext> current = new ThreadLocal<GameContext>();
        private MockDeckFactory deckFactory;
        public GameSession game;
        public TestShowEventListner showEventListener;
        public TestResultEventListener resultEventListner;

        public static GameContext current() {
            return current.get();
        }

        public static GameContext initialize() throws InvalidGameConfigurationException {
            GameContext value = new GameContext();
            current.set(value);
            current().deckFactory = new MockDeckFactory();
            current().game = new GameSession(new GameConfiguration(5, 10, false, true), current().deckFactory, new TableFactory());
            return value;
        }

        public static void reset() {
            current.remove();
        }
    }

    private Player getPlayer(String nickname) {
        return current().game.getTableFacade().getPlayerInfos(nickname).getPlayer();
    }
}
