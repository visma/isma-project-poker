package org.isma.poker.rest.service;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;

// sert a rien pour le moment
public class PokerArgumentResolver implements WebArgumentResolver{
    @Override
    public Object resolveArgument(MethodParameter methodParameter, NativeWebRequest webRequest) throws Exception {
        return WebArgumentResolver.UNRESOLVED;
    }
}
