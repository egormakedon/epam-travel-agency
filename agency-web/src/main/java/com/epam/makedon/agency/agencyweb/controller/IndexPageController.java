package com.epam.makedon.agency.agencyweb.controller;

import com.epam.makedon.agency.agencyweb.util.Constant;
import com.epam.makedon.agency.agencyweb.util.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * IndexPageController, managing index page.
 *
 * @author Yahor Makedon
 * @version 1.0
 */

@Controller

public class IndexPageController {

    @PreAuthorize("permitAll()")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute(Constant.URL, Page.INDEX.getUrl());
        return Page.INDEX.getPage();
    }
}