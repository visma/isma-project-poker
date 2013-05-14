package org.isma.poker.jbehave;

import org.isma.poker.game.GameSession;
import org.isma.poker.game.PokerActionEnum;
import org.isma.poker.game.actions.PlayerAction;
import org.isma.poker.game.event.RoundEndEvent;
import org.isma.poker.game.event.ShowEvent;
import org.isma.poker.game.exceptions.InvalidPlayerBetException;
import org.isma.poker.game.factory.TableFactory;
import org.isma.poker.game.model.*;
import org.isma.poker.game.results.Results;
import org.isma.poker.game.step.InvalidStepActionException;
import org.isma.poker.game.step.StepEnum;
import org.isma.poker.jbehave.utils.TestResultEventListner;
import org.isma.poker.jbehave.utils.TestShowEventListner;
import org.isma.poker.mock.MockDeck;
import org.isma.poker.mock.MockDeckFactory;
import org.isma.poker.model.Card;
import org.isma.poker.model.CommunityCards;
import org.isma.poker.model.Hand;
import org.isma.poker.model.HandEvaluation;
import org.jbehave.core.annotations.BeforeScenario;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import java.util.Arrays;
import java.util.logging.Logger;

import static junit.framework.Assert.*;
import static org.isma.poker.jbehave.GameSteps.GameContext.current;

public class GameSteps extends AbstractPokerSteps {
    private static final Logger LOGGER = Logger.getLogger(GameSteps.class.getName());

    @BeforeScenario
    public void initializeContext() throws InvalidGameConfigurationException {
        GameContext.initialize();
    }

    @Given("la table de jeu est vide")
    public void givenTable() throws Exception {
        assertTrue(current().game.getTableInfos().getPlayersInfos().isEmpty());
        //TODO attention au 2 du init(2)
        current().game.init(2);
        current().showEventListener = new TestShowEventListner();
        current().resultEventListner = new TestResultEventListner();
        current().game.addEventListener(current().showEventListener);
        current().game.addEventListener(current().resultEventListner);
    }

    @Given("les blindes sont � $smallBlind/$bigBlind jetons")
    public void givenBlinds(int smallBlind, int bigBlind) throws Exception {
        assertEquals(smallBlind, current().game.getTableInfos().getSmallBlindAmount());
        assertEquals(bigBlind, current().game.getTableInfos().getBigBlindAmount());
    }

    @Given("les prochaines cartes du deck sont : $cards")
    public void givenDeck(Card... cards) throws Exception {
        ((MockDeck) current().game.getDeck()).prepareCards(Arrays.asList(cards));
    }

    @Given("le joueur $nickname dispose de $chips jetons et rentre dans la partie")
    public void givenPlayer(String nickname, int chips) throws Exception {
        Player player = new Player(nickname);
        player.setChips(chips);
        PlayerAction.sitIn(player, current().game);
    }

    @When("la partie d�marre")
    public void givenStart() throws Exception {
        //TODO supprimer ?
        // current().game.start();
    }

    @When("$nickname call")
    public void whenCall(String nickname) throws InvalidPlayerTurnException, InvalidPlayerBetException, InvalidStepActionException {
        Player player = getPlayer(nickname);
        PlayerAction.call(player, current().game);
    }

    @Then("etape en cours : $step (pot : $pot)")
    public void givenGameStep(StepEnum step, int pot) throws Exception {
        assertEquals(step, current().game.getStep());
        assertEquals(pot, current().game.getPot().getTotal());
    }

    @Then("$nickname est au bouton")
    public void givenButtonPlayer(String nickname) throws Exception {
        assertEquals(nickname, current().game.getTableInfos().getDealerInfos().getPlayer().getNickname());
    }

    @Then("$nickname paie la petite blinde")
    public void givenSmallBlindPaid(String nickname) throws Exception {
        //TODO
    }

    @Then("$nickname paie la grosse blinde")
    public void givenBigBlindPaid(String nickname) throws Exception {
        //TODO
    }

    @Then("$nickname re�oit les cartes cachees : $card1, $card2")
    public void givenHoleCards(String nickname, Card card1, Card card2) throws Exception {
        Player player = getPlayer(nickname);
        assertEquals(2, player.getHand().size());
        assertEquals(card1, player.getHand().get(0));
        assertEquals(card2, player.getHand().get(1));
    }

    @Then("$nickname est le prochain joueur � parler")
    public void givenCurrentPlayer(String nickname) throws Exception {
        assertEquals(nickname, current().game.getTableInfos().getCurrentPlayerInfos().getPlayer().getNickname());
    }

    @When("$nickname effectue l'action : $action($chipsStr)")
    public void givenPlayerAction(String nickname, PokerActionEnum pokerAction, String chipsStr) throws Exception {
        GameSession game = current().game;
        Player player = getPlayer(nickname);;
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
            default:
                throw new Exception("action non gere dans le test :" + pokerAction);
        }
    }

    @Then("le croupier distribue le flop : $card1, $card2, $card3")
    public void givenFlop(Card card1, Card card2, Card card3) throws Exception {
        CommunityCards communityCards = current().game.getTableInfos().getCommunityCards();
        assertEquals(3, communityCards.size());
        assertEquals(card1, communityCards.get(0));
        assertEquals(card2, communityCards.get(1));
        assertEquals(card3, communityCards.get(2));
    }

    @Then("le croupier distribue le turn : $card1, $card2, $card3, $card4")
    public void givenTurn(Card card1, Card card2, Card card3, Card card4) throws Exception {
        CommunityCards communityCards = current().game.getTableInfos().getCommunityCards();
        assertEquals(4, communityCards.size());
        assertEquals(card1, communityCards.get(0));
        assertEquals(card2, communityCards.get(1));
        assertEquals(card3, communityCards.get(2));
        assertEquals(card4, communityCards.get(3));
    }

    @Then("le croupier distribue la river : $card1, $card2, $card3, $card4, $card5")
    public void givenRiver(Card card1, Card card2, Card card3, Card card4, Card card5) throws Exception {
        CommunityCards communityCards = current().game.getTableInfos().getCommunityCards();
        assertEquals(5, communityCards.size());
        assertEquals(card1, communityCards.get(0));
        assertEquals(card2, communityCards.get(1));
        assertEquals(card3, communityCards.get(2));
        assertEquals(card4, communityCards.get(3));
        assertEquals(card5, communityCards.get(4));
    }

    @When("$nickname montre $handEvaluation : $card1, $card2, $card3, $card4, $card5")
    public void givenShow(String nickname, HandEvaluation handEvaluation, Card card1, Card card2, Card card3, Card card4, Card card5) throws InvalidPlayerTurnException, InvalidPlayerBetException, InvalidStepActionException {
        GameSession game = current().game;
        Player player = getPlayer(nickname);

        PlayerAction.show(player, game);

        ShowEvent showEvent = (ShowEvent) current().showEventListener.poll();
        Hand hand = showEvent.getHand();
        assertEquals(nickname, showEvent.getPlayer());
        assertEquals(handEvaluation, showEvent.getHandEvaluation());
        assertEquals(card1, hand.get(0));
        assertEquals(card2, hand.get(1));
        assertEquals(card3, hand.get(2));
        assertEquals(card4, hand.get(3));
        assertEquals(card5, hand.get(4));
    }

    @Then("$nickname est vainqueur avec $handEvaluation et gagne $prize jetons")
    public void thenWinner(String nickname, HandEvaluation handEvaluation, int prize){
        RoundEndEvent event = (RoundEndEvent) current().resultEventListner.peek();
        Results results = event.getResults();
        for (Winner winner : results.getWinners()) {
            if (winner.getPlayer().getNickname().equals(nickname)){
                assertEquals(handEvaluation, winner.getHandEvaluation());
                assertEquals(prize, winner.getPrize());
                return;
            }
        }
        fail(nickname + " not win");
    }

    @Then("$nickname a perdu avec $handEvaluation")
    public void thenLoser(String nickname, HandEvaluation handEvaluation){
        RoundEndEvent event = (RoundEndEvent) current().resultEventListner.peek();
        Results results = event.getResults();
        for (Loser loser : results.getLosers()) {
            if (loser.getPlayer().getNickname().equals(nickname)){
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
        public GameSession game;
        public TestShowEventListner showEventListener;
        public TestResultEventListner resultEventListner;

        public static GameContext current() {
            return current.get();
        }

        public static GameContext initialize() throws InvalidGameConfigurationException {
            GameContext value = new GameContext();
            current.set(value);
            current().game = new GameSession(new GameConfiguration(5, 10, false, true), new MockDeckFactory(), new TableFactory());
            return value;
        }

        public static void reset() {
            current.remove();
        }
    }

    private Player getPlayer(String nickname){
        return current().game.getTableInfos().getPlayerInfos(nickname).getPlayer();
    }
}
