package com.epam.makedon.agency.agencyweb.controller;

import com.epam.makedon.agency.agencyweb.util.Constant;
import com.epam.makedon.agency.agencyweb.util.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * ErrorPageController, managing error page.
 *
 * @author Yahor Makedon
 * @version 1.0
 */

@Controller

public class ErrorPageController {

    @PreAuthorize("permitAll()")
    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public String error(Model model) {
        model.addAttribute(Constant.URL, Page.ERROR.getUrl());
        return Page.ERROR.getPage();
    }
}