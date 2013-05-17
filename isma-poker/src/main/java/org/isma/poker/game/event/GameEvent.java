package org.isma.poker.game.event;

import org.apache.log4j.Logger;

//TODO Quand on créé des events : penser a cloner l'état des paramètres pour avoir un état SNAPSHOT
public abstract class GameEvent {
    private static final Logger LOG = Logger.getLogger(GameEvent.class);

    public void log() {
        LOG.debug("gameEvent : " + getDescription());
    }

    public abstract String getDescription();
}
