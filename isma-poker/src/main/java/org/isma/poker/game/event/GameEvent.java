package org.isma.poker.game.event;

import org.apache.log4j.Logger;

//TODO Quand on cr�� des events : penser a cloner l'�tat des param�tres pour avoir un �tat SNAPSHOT
public abstract class GameEvent {
    private static final Logger LOG = Logger.getLogger(GameEvent.class);

    public void log() {
        LOG.debug("gameEvent : " + getDescription());
    }

    public abstract String getDescription();
}
