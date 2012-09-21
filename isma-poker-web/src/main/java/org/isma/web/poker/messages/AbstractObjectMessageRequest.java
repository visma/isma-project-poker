package org.isma.web.poker.messages;

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.String.format;

public abstract class AbstractObjectMessageRequest<O> {
    private static final Logger log = Logger.getLogger(AbstractObjectMessageRequest.class);


    public abstract String getIdentifier();

    public abstract List<String> getAttributesKeys();

    public boolean isValidMessage(String message) {
        return !decodeMessage(message).isEmpty();
    }

    private Map<String, String> decodeMessage(String message) {
        //log.setLevel(Level.TRACE);
        Map<String, String> decodeMap = new HashMap<String, String>();
        Pattern pattern = Pattern.compile(format("%s\\{(\\w+=\\w+)(,\\w+=\\w+)*\\}", getIdentifier()));
        Matcher matcher = pattern.matcher(message);
        boolean matches = matcher.matches();
        if (!matches){
            return decodeMap;
        }
        log.trace("groupes = " + matcher.groupCount());
        List<String> attributesKeys = getAttributesKeys();
        for (int i = 1; i < matcher.groupCount(); i++) {
            String group = matcher.group(i);
            log.trace("groupe = " + group);
            String[] split = group.split("=");
            String key = split[0];
            String value = split[1];
            if (!key.equals(attributesKeys.get(0))) {
                return new HashMap<String, String>();
            }
            decodeMap.put(key, value);
        }
        return decodeMap;
    }

    public Map<String, String> decode(String message) {
        return decodeMessage(message);
    }
}
