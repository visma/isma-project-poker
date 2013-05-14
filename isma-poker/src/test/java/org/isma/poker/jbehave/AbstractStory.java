package org.isma.poker.jbehave;

import org.apache.log4j.Logger;
import org.jbehave.core.Embeddable;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.failures.FailingUponPendingStep;
import org.jbehave.core.io.CodeLocations;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.reporters.ConsoleOutput;
import org.jbehave.core.reporters.CrossReference;
import org.jbehave.core.reporters.IdeOnlyConsoleOutput;
import org.jbehave.core.reporters.StoryReporterBuilder;

import java.net.URL;
import java.util.Arrays;

public abstract class AbstractStory extends JUnitStories {
    private final CrossReference xref = new CrossReference();

    public class MyStoryReporter extends ConsoleOutput {
        private final Logger log = Logger.getLogger(AbstractStory.class);

        @Override
        public void successful(String step) {
            log.info(">>successStep:" + step);
        }

        @Override
        public void failed(String step, Throwable cause) {
            log.error(">>error:" + step + ", reason:" + cause);
        }

    }

    public AbstractStory() {
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
}
