package com.epam.makedon.agency.agencyweb.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlingController {

    @ExceptionHandler(Exception.class)
    public String controllerException(Model model, Exception e) {
        model.addAttribute("exceptionMessage", e.getMessage());
        return "error";
    }
}