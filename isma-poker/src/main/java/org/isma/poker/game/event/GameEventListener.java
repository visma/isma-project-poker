package org.isma.poker.game.event;

import java.util.LinkedList;
import java.util.Queue;

public class GameEventListener {
    protected Queue<GameEvent> queue = new LinkedList<GameEvent>();

    public void add(GameEvent event) {
        queue.add(event);
    }

    public GameEvent poll() {
        return queue.poll();
    }

    public GameEvent peek() {
        return queue.peek();
    }

    public boolean hasEvents(){
        return !queue.isEmpty();
    }
}
