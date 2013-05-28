package org.isma.poker.rest.controller;

import org.apache.log4j.Logger;
import org.isma.poker.commons.dto.ErrrorDTO;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class AbstractController {
    private static final Logger logger = Logger.getLogger(AbstractController.class);

    //TODO ça a pas l'air de marcher tout seul ...
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object handleException(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        logger.debug("handle exception : " + ex);
        return new ErrrorDTO(ex.getMessage(), ex.getClass().getSimpleName());
    }
}
