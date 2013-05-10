package org.isma.web.poker.messages.response;

import org.isma.poker.game.model.Player;
import org.isma.poker.game.TableInfos;
import org.isma.web.poker.messages.AbstractObjectMessageResponse;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

public class PlayerStateResponse extends AbstractObjectMessageResponse<TableInfos> {
    public static final String NICKNAME = "nickname";
    public static final String CHIPS = "chips";
    public static final String STATUS = "status";

    @Override
    public String getIdentifier() {
        return "Player";
    }

    @Override
    public List<String> getAttributesKeys(TableInfos tableInfos) {
        List<String> keys = new ArrayList<String>();
        for (int index = 0; index < tableInfos.getPlayersInfos().size(); index++) {
            keys.add(format("%s_%s", NICKNAME, index));
            keys.add(format("%s_%s", CHIPS, index));
            keys.add(format("%s_%s", STATUS, index));
        }
        return keys;
    }

    @Override
    public String getAttributeValue(TableInfos tableInfos, String attributeKey) {
        String key = attributeKey.substring(0, attributeKey.lastIndexOf('_'));
        int position = Integer.valueOf(attributeKey.substring(attributeKey.lastIndexOf('_')));
        Player player = tableInfos.getPlayersInfos().get(position).getPlayer();
        if (key.equals(NICKNAME)) {
            return player.getNickname();
        } else if (attributeKey.equals(CHIPS)) {
            return Integer.toString(player.getChips());
        } else if (attributeKey.equals(STATUS)) {
            return (player.isFold() ? "fold" : "ingame");
        }
        return throwUnexpectedAttributeValue(attributeKey);
    }


}
