package org.isma.poker.jbehave.utils;

import org.isma.poker.game.event.GameEvent;
import org.isma.poker.game.event.GameEventListener;
import org.isma.poker.game.event.ShowEvent;

public class TestShowEventListner extends GameEventListener {
    @Override
    public void add(GameEvent event) {
        if (event instanceof ShowEvent) {
            super.add(event);
        }
    }
}
