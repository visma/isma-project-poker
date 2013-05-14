package org.isma.poker.jbehave.utils;

import org.isma.poker.game.event.GameEvent;
import org.isma.poker.game.event.GameEventListener;
import org.isma.poker.game.event.RoundEndEvent;

public class TestResultEventListner extends GameEventListener {
    @Override
    public void add(GameEvent event) {
        if (event instanceof RoundEndEvent) {
            super.add(event);
        }
    }
}
