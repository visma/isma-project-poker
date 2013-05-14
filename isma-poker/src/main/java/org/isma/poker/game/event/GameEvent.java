package org.isma.poker.game.event;

//TODO Quand on créé des events : penser a cloner l'état des paramètres pour avoir un état SNAPSHOT
public abstract class GameEvent {
    public abstract String getDescription();
}
