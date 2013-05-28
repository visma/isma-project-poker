package org.isma.poker.rest;

import net.sf.json.JSON;
import org.isma.poker.commons.dto.TableDTO;
import org.isma.poker.commons.mapper.TableMapper;
import org.isma.poker.game.GameSession;
import org.isma.poker.game.event.*;
import org.isma.poker.rest.socket.PokerSocketClient;

import java.io.IOException;

import static net.sf.json.JSONSerializer.toJSON;

public class PokerEventDispatcher extends GameEventListener {
    private final PokerSocketClient socketClient;
    private final GameSession gameSession;

    public PokerEventDispatcher(PokerSocketClient socketClient, GameSession gameSession) throws Exception {
        this.socketClient = socketClient;
        this.gameSession = gameSession;
        this.socketClient.connect();
    }

    @Override
    public void add(GameEvent event) {
        try {
            super.add(event);
            socketClient.send(buildMessage(event));
            if (event instanceof PlayerTurnEvent) {
                socketClient.send(buildTableMessage());
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private String buildTableMessage() {
        TableDTO table = TableMapper.toDTO(gameSession);
        ClientMessage message = new ClientMessage("table", table);
        JSON json = toJSON(message);
        return json.toString();
    }

    private String buildMessage(GameEvent event) {
        ClientMessage msg;
        if (event instanceof PlayerSitInEvent) {
            msg = new ClientMessage("sitin", event);
        } else if (event instanceof BuyEvent) {
            msg = new ClientMessage("buy", event);
        } else if (event instanceof BlindEvent) {
            msg = new ClientMessage("blind", event);
        } else if (event instanceof PlayerTurnEvent) {
            msg = new ClientMessage("playerTurn", event);
        } else if (event instanceof NewStepEvent) {
            msg = new ClientMessage("newStep", event);
        } else if (event instanceof RoundEndEvent) {
            msg = new ClientMessage("results", event);
        } else if (event instanceof HoldeCardEvent) {
            msg = new ClientMessage("holeCards", event);
        } else {
            msg = new ClientMessage("???", event);
        }
        JSON json = toJSON(msg);
        return json.toString();
    }
}
