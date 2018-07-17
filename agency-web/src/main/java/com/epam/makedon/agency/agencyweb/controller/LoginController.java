package com.epam.makedon.agency.agencyweb.controller;

import com.epam.makedon.agency.agencyweb.util.Constant;
import com.epam.makedon.agency.agencyweb.util.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * LoginController - SpringMVC controller for managing login.
 *
 * @author Yahor Makedon
 * @version 1.0
 */

@Controller

public class LoginController {

    private static final String USERNAME_OR_PASSWORD = "usernameOrPassword";

    @PreAuthorize("permitAll()")
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error) {
        if (error != null) {
            model.addAttribute(Constant.RESULT, USERNAME_OR_PASSWORD);
        }

        model.addAttribute(Constant.URL, Page.LOGIN.getUrl());
        return Page.LOGIN.getPage();
    }
}