package org.isma.poker.game.event;

import org.isma.poker.game.model.Winner;
import org.isma.poker.game.results.Results;

public class RoundEndEvent extends GameEvent {
    private Results results;

    public RoundEndEvent(Results results) {
        this.results = results.clone();
        for (Winner winner : results.getWinners()) {
            if (winner.getPlayer().getHand().size() == 0){
                System.out.println("!!");
            }
        }
    }

    public Results getResults() {
        return results;
    }

    @Override
    public String getDescription() {
        return "RoundEndEvent:" + results;
    }
}
