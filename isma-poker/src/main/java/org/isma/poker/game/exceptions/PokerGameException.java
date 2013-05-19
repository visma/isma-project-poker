package org.isma.poker.game.exceptions;

import org.apache.log4j.Logger;

public class PokerGameException extends Exception {
    private static final Logger LOG = Logger.getLogger(PokerGameException.class);

    public PokerGameException(String msg) {
        super(msg);
    }

    public PokerGameException(Exception e) {
        super(e);
        LOG.error("Erreur", e);
    }
}
