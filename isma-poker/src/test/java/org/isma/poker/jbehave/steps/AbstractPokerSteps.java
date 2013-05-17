package org.isma.poker.jbehave.steps;

import org.isma.poker.game.PokerActionEnum;
import org.isma.poker.game.step.StepEnum;
import org.isma.poker.helper.CardHelper;
import org.isma.poker.model.Card;
import org.isma.poker.model.HandEvaluation;
import org.jbehave.core.annotations.AsParameterConverter;
import org.jbehave.core.steps.Steps;

import java.util.Arrays;
import java.util.List;

public class AbstractPokerSteps extends Steps {
    @AsParameterConverter
    public Card[] createCards(String name) {
        List<String> cards = Arrays.asList(name.split(", "));
        Card[] cardArray = new Card[cards.size()];
        int i = 0;
        for (String card : cards) {
            cardArray[i++] = CardHelper.toCard(card);
        }
        return cardArray;
    }


    @AsParameterConverter
    public Card createCard(String name) {
        return CardHelper.toCard(name);
    }

    @AsParameterConverter
    public HandEvaluation createEvaluation(String name) {
        return HandEvaluation.valueOf(name);
    }

    @AsParameterConverter
    public PokerActionEnum createPokerAction(String name) {
        return PokerActionEnum.valueOf(name);
    }

    @AsParameterConverter
    public StepEnum createStep(String name) {
        return StepEnum.valueOf(name);
    }

}
