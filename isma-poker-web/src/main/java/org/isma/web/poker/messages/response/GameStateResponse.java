package org.isma.web.poker.messages.response;

import org.isma.poker.game.GameSession;
import org.isma.poker.game.TableInfos;
import org.isma.web.poker.messages.AbstractObjectMessageResponse;

import java.util.List;

import static java.util.Arrays.asList;

public class GameStateResponse extends AbstractObjectMessageResponse<GameSession> {
    public static final String POT = "pot";
    public static final String STEP = "step";
    public static final String BUTTON = "button";
    public static final String CURRENT_PLAYER = "currentPlayer";
    private static final String SMALL_BLIND = "smallBlind";
    private static final String BIG_BLIND = "bigBlind";


    @Override
    public String getIdentifier() {
        return "GameState";
    }

    @Override
    public List<String> getAttributesKeys(GameSession game) {
        return asList(SMALL_BLIND, BIG_BLIND, POT, STEP, BUTTON, CURRENT_PLAYER);
    }

    @Override
    public String getAttributeValue(GameSession game, String attributeKey) {
        TableInfos tableInfos = game.getTableInfos();
        if (attributeKey.equals(SMALL_BLIND)) {
            return Integer.toString(tableInfos.getSmallBlindAmount());
        } else if (attributeKey.equals(BIG_BLIND)) {
            return Integer.toString(tableInfos.getBigBlindAmount());
        } else if (attributeKey.equals(POT)) {
            return Integer.toString(tableInfos.getTotalPot());
        } else if (attributeKey.equals(STEP)) {
            return game.getStep().name();
        } else if (attributeKey.equals(BUTTON)) {
            return tableInfos.getDealerInfos().getPlayer().getNickname();
        } else if (attributeKey.equals(CURRENT_PLAYER)) {
            return tableInfos.getCurrentPlayerInfos().getPlayer().getNickname();
        }
        return throwUnexpectedAttributeValue(attributeKey);
    }


}
