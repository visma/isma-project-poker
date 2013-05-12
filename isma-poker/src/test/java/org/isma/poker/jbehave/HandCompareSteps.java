package org.isma.poker.jbehave;

import org.isma.poker.HandEvaluator;
import org.isma.poker.helper.CardHelper;
import org.isma.poker.model.Card;
import org.isma.poker.model.Hand;
import org.isma.poker.model.HandEvaluation;
import org.jbehave.core.annotations.AsParameterConverter;
import org.jbehave.core.annotations.BeforeScenario;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.steps.Steps;

import java.util.*;
import java.util.logging.Logger;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;
import static org.isma.poker.jbehave.HandCompareSteps.HandCompareContext.current;

public class HandCompareSteps extends Steps {
    private static final Logger LOGGER = Logger.getLogger(HandCompareSteps.class.getName());

    @BeforeScenario
    public void initializeContext() {
        HandCompareContext.initialize();
    }

    @AsParameterConverter
    public Card createCard(String name) {
        return CardHelper.parse(name);
    }

    @AsParameterConverter
    public HandEvaluation createEvaluation(String name) {
        return HandEvaluation.valueOf(name);
    }

    @Given("la main $handKey dispose des cartes suivantes : $card1, $card2, $card3, $card4, $card5")
    public void givenHand(String handKey, Card card1, Card card2, Card card3, Card card4, Card card5) throws Exception {
        populateHand(handKey, card1, card2, card3, card4, card5);
    }

    @Given("la main $handKey dispose des cartes suivantes : $card1, $card2, $card3, $card4, $card5, $card6, $card7")
    public void givenHand(String handKey, Card card1, Card card2, Card card3, Card card4, Card card5, Card card6, Card card7) throws Exception {
        populateHand(handKey, card1, card2, card3, card4, card5, card6, card7);
    }

    private void populateHand(String handKey, Card... cards) {
        Hand hand = new Hand();
        Collections.addAll(hand, cards);
        current().hands.put(handKey, hand);
    }

    @Then("la combinaison de la main $handKey est : $combination")
    public void thenCombination(String handKey, HandEvaluation expected) throws Exception {
        HandEvaluation actual = new HandEvaluator().evaluate(current().hands.get(handKey));
        assertEquals(expected, actual);
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
    }
}
