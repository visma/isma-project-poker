package org.isma.poker.jbehave;

import org.isma.poker.jbehave.steps.GameSteps;
import org.jbehave.core.io.CodeLocations;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;

import java.util.List;

public class GameTest extends AbstractStory {


    protected List<String> storyPaths() {
        return new StoryFinder().findPaths(
                CodeLocations.codeLocationFromClass(getClass()),
                "org/isma/poker/jbehave/scenarios/game/*/game_*.story",
//                "org/isma/poker/jbehave/scenarios/game/*/game_2players_2rounds_allins.story",
                //TODO a remettre bien en *
                "*KO*");
    }

    @Override
    public InjectableStepsFactory stepsFactory() {
        return new InstanceStepsFactory(configuration(), new GameSteps());
    }

}
