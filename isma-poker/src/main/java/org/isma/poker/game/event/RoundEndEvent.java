package org.isma.poker.game.event;

import org.isma.poker.game.results.Results;

public class RoundEndEvent extends GameEvent {
    private Results results;

    public RoundEndEvent(Results results) {
        this.results = results;
    }

    public Results getResults() {
        return results;
    }

    @Override
    public String getDescription() {
        return "RoundEndEvent:"+results;
    }
}
