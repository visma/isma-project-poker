package org.isma.poker.jbehave;

import org.isma.poker.jbehave.steps.HandCompareSteps;
import org.jbehave.core.io.CodeLocations;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;

import java.util.List;

public class HandCompareTest extends AbstractStory {


    protected List<String> storyPaths() {
        return new StoryFinder().findPaths(
                CodeLocations.codeLocationFromClass(getClass()),
                "org/isma/poker/jbehave/scenarios/hand/hand_comparaison_*.story",
                "*KO*");
    }

    @Override
    public InjectableStepsFactory stepsFactory() {
        return new InstanceStepsFactory(configuration(), new HandCompareSteps());
    }

}
