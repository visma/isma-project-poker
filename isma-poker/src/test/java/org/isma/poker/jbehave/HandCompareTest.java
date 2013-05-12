package org.isma.poker.jbehave;

import org.jbehave.core.Embeddable;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.failures.FailingUponPendingStep;
import org.jbehave.core.io.CodeLocations;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.reporters.CrossReference;
import org.jbehave.core.reporters.IdeOnlyConsoleOutput;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;

import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class HandCompareTest extends JUnitStories {
    private final CrossReference xref = new CrossReference();
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

    @Override
    public Configuration configuration() {
        Class<? extends Embeddable> embeddableClass = this.getClass();
        URL codeLocation = CodeLocations.codeLocationFromClass(embeddableClass);
        StoryReporterBuilder storyReporter = new StoryReporterBuilder()
                .withCodeLocation(codeLocation)
                .withReporters(new IdeOnlyConsoleOutput())
                .withFailureTrace(true)
                .withFailureTraceCompression(true)
                .withCrossReference(xref);
        return new MostUsefulConfiguration()
                .useStoryLoader(new LoadFromClasspath())
                .useStoryReporterBuilder(storyReporter)
                .useStepMonitor(xref.getStepMonitor())
                .usePendingStepStrategy(new FailingUponPendingStep());
    }

    protected List<String> storyPaths() {
        return new StoryFinder().findPaths(
                CodeLocations.codeLocationFromClass(getClass()),
                "org/isma/poker/jbehave/*CardsHandCompare.story",
                "*KO*");
    }

    @Override
    public InjectableStepsFactory stepsFactory() {
        return new InstanceStepsFactory(configuration(), new HandCompareSteps());
    }

}
