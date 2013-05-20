package org.isma.web.poker.messages.response;

import org.isma.poker.game.GameSession;
import org.isma.poker.game.model.TableFacade;
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
        TableFacade tableFacade = game.getTableFacade();
        if (attributeKey.equals(SMALL_BLIND)) {
            return Integer.toString(tableFacade.getSmallBlindAmount());
        } else if (attributeKey.equals(BIG_BLIND)) {
            return Integer.toString(tableFacade.getBigBlindAmount());
        } else if (attributeKey.equals(POT)) {
            return Integer.toString(tableFacade.getTotalPot());
        } else if (attributeKey.equals(STEP)) {
            return game.getStep().name();
        } else if (attributeKey.equals(BUTTON)) {
            return tableFacade.getDealerInfos().getPlayer().getNickname();
        } else if (attributeKey.equals(CURRENT_PLAYER)) {
            return tableFacade.getCurrentPlayerInfos().getPlayer().getNickname();
        }
        return throwUnexpectedAttributeValue(attributeKey);
    }


}
