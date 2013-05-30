package org.isma.poker.rest.controller;

import org.apache.log4j.Logger;
import org.isma.poker.commons.dto.ErrorDTO;
import org.isma.poker.rest.ClientMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class AbstractController {
    private static final Logger logger = Logger.getLogger(AbstractController.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object handleException(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        logger.error("handle exception : ", ex);
        response.setStatus(HttpStatus.METHOD_FAILURE.value());
        return new ClientMessage("error", new ErrorDTO(ex.getMessage(), ex.getClass().getSimpleName()));
    }


}
