package org.isma.poker.jbehave.steps;

import junit.framework.Assert;
import org.isma.poker.HandEvaluator;
import org.isma.poker.game.model.Player;
import org.isma.poker.model.Card;
import org.isma.poker.model.Hand;
import org.isma.poker.model.HandEvaluation;
import org.jbehave.core.annotations.BeforeScenario;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;

import java.util.*;
import java.util.logging.Logger;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;
import static org.isma.poker.jbehave.steps.HandCompareSteps.HandCompareContext.current;

public class HandCompareSteps extends AbstractPokerSteps {
    private static final Logger LOGGER = Logger.getLogger(HandCompareSteps.class.getName());

    @BeforeScenario
    public void initializeContext() {
        HandCompareContext.initialize();
    }

    @Given("la main $handKey dispose des cartes suivantes ($handEvaluation) : $card1, $card2, $card3, $card4, $card5, $card6, $card7")
    public void givenHand(String handKey, HandEvaluation handEvaluation, Card card1, Card card2, Card card3, Card card4, Card card5, Card card6, Card card7) throws Exception {
        populateHand(handKey, handEvaluation, card1, card2, card3, card4, card5, card6, card7);
    }

    @Given("la main $handKey dispose des cartes suivantes ($handEvaluation) : $card1, $card2, $card3, $card4, $card5")
    public void givenHand(String handKey, HandEvaluation handEvaluation, Card card1, Card card2, Card card3, Card card4, Card card5) throws Exception {
        populateHand(handKey, handEvaluation, card1, card2, card3, card4, card5);
    }


    private void populateHand(String handKey, HandEvaluation expectedHandEval, Card... cards) {
        Hand hand = new Hand();
        Collections.addAll(hand, cards);
        Assert.assertEquals(expectedHandEval, current().handEvaluator.evaluate(hand));
        current().hands.put(handKey, hand);

        Player player = new Player(handKey);
        player.getHand().addAll(hand);
        current().players.put(handKey, player);
    }


    @Then("la meilleure main est la main $expectedWinnerHandKey")
    public void thenWinner(String expectedWinnerHandKey) throws Exception {
        List<Hand> values = new ArrayList<Hand>(current().hands.values());
        Collections.sort(values, new HandEvaluator().getHandComparator());
        Hand bestHand = values.get(values.size() - 1);
        for (String key : current().hands.keySet()) {
            if (bestHand.equals(current().hands.get(key))) {
                assertEquals(expectedWinnerHandKey, key);
                return;
            }
        }
        fail("best hand evaluation failed");
    }

    public static class HandCompareContext {
        private static ThreadLocal<HandCompareContext> current = new ThreadLocal<HandCompareContext>();

        public static HandCompareContext current() {
            return current.get();
        }

        public static HandCompareContext initialize() {
            HandCompareContext value = new HandCompareContext();
            current.set(value);
            return value;
        }

        public static void reset() {
            current.remove();
        }

        public Map<String, Hand> hands = new HashMap<String, Hand>();
        public Map<String, Player> players = new HashMap<String, Player>();
        public HandEvaluator handEvaluator = new HandEvaluator();
    }
}
