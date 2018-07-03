package com.epam.makedon.agency.agencyweb.controller;

import com.epam.makedon.agency.agencyweb.util.Constant;
import com.epam.makedon.agency.agencyweb.util.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlingController {

    private static final String EXCEPTION_MESSAGE = "exceptionMessage";

    @ExceptionHandler(Exception.class)
    public String controllerException(Model model, Exception e) {
        model.addAttribute(EXCEPTION_MESSAGE, e.getMessage());
        return Constant.REDIRECT + Page.ERROR.getUrl();
    }
}