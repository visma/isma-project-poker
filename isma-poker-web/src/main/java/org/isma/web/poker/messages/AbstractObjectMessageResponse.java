package org.isma.web.poker.messages;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractObjectMessageResponse<O> {
    private O object;
    private static final char SEPARATOR = ',';

    public abstract String getIdentifier();

    public abstract List<String> getAttributesKeys(O object);

    public abstract String getAttributeValue(O object, String attributeKey);

    public String encode() {
        return String.format("%s{%s}", getIdentifier(), buildAttributesMessage(object));
    }


    protected String throwUnexpectedAttributeValue(String attributeKey) {
        throw new IllegalArgumentException("unexpected attributeKey :" + attributeKey);
    }

    private String buildAttributesMessage(O object) {
        List<String> keys = getAttributesKeys(object);
        List<String> attributes = new ArrayList<String>();
        for (String key : keys) {
            attributes.add(key + "=" + getAttributeValue(object, key));
        }
        return String.format(StringUtils.join(attributes, SEPARATOR));
    }

    public void setObject(O object) {
        this.object = object;
    }

}
