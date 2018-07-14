package com.epam.makedon.agency.agencyweb.controller;

import com.epam.makedon.agency.agencyweb.util.Constant;
import com.epam.makedon.agency.agencyweb.util.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * AdminPageController - SpringMVC controller for managing admin page.
 *
 * @author Yahor Makedon
 * @version 1.0
 */

@Controller

public class AdminPageController {

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String getAdmin(Model model) {

        model.addAttribute(Constant.URL, Page.ADMIN.getUrl());
        return Page.ADMIN.getPage();
    }
}