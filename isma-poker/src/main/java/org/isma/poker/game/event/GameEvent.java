package org.isma.poker.game.event;

//TODO Quand on cr�� des events : penser a cloner l'�tat des param�tres pour avoir un �tat SNAPSHOT
public abstract class GameEvent {
    public abstract String getDescription();
}
