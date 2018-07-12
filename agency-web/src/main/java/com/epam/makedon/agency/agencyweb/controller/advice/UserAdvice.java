package com.epam.makedon.agency.agencyweb.controller.advice;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * UserAdvice.
 *
 * @author Yahor Makedon
 * @version 1.0
 */

@ControllerAdvice

public class UserAdvice {

    @ModelAttribute("curuser")
    public User getUser(Authentication authentication) {
        return (authentication != null) ? (User) authentication.getPrincipal() : null;
    }
}