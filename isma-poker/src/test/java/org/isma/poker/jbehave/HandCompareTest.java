package org.isma.poker.jbehave;

import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.io.CodeLocations;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;

import java.util.Arrays;
import java.util.List;

public class HandCompareTest extends JUnitStories {

    public HandCompareTest() {
        Embedder embedder = configuredEmbedder();
        embedder.useMetaFilters(Arrays.asList("-skip"));

        embedder.embedderControls()//
                .doGenerateViewAfterStories(true)
                .doIgnoreFailureInStories(false)
                .doIgnoreFailureInView(true)
                .doVerboseFailures(true)
                .doVerboseFiltering(true)
                .useThreads(1)
                .useStoryTimeoutInSecs(60000);
    }


    protected List<String> storyPaths() {
        return new StoryFinder().findPaths(
                CodeLocations.codeLocationFromClass(getClass()),
                "org/isma/poker/jbehave/handCompare.story",
                "*KO*");
    }

    @Override
    public InjectableStepsFactory stepsFactory() {
        return new InstanceStepsFactory(configuration(), new HandCompareSteps());
    }

}
