package org.isma.poker.rest.manager;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class PlayerSessionManager {
    private Map<String, String> playersByAuthCode = new HashMap<String, String>();

    public String register(String nickname) throws Exception {
        if (playersByAuthCode.containsValue(nickname)){
            throw new Exception(nickname + " already connected");
        }
        String authCode = generateAuthCode(nickname);
        playersByAuthCode.put(authCode, nickname);
        return authCode;
    }

    private String generateAuthCode(String nickname) {
        return nickname + "_authCode";
    }

    public String getPlayerNickname(String authCode) {
        return playersByAuthCode.get(authCode);
    }
}
