package com.epam.makedon.agency.agencyweb.controller;

import com.epam.makedon.agency.agencyweb.util.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * GetPageController, managing to return page for url.
 *
 * @author Yahor Makedon
 * @version 1.0
 */

@Controller

public class GetPageController {

    @PreAuthorize("permitAll()")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {
        return Page.INDEX.getPage();
    }

    @PreAuthorize("permitAll()")
    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public String error(Model model) {
        return Page.ERROR.getPage();
    }

    @RequestMapping(value = "/hotel", method = RequestMethod.GET)
    public String hotel(Model model) {
        return Page.HOTEL.getPage();
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value = "/tour", method = RequestMethod.GET)
    public String tour(Model model) {
        return Page.TOUR.getPage();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String user(Model model) {
        return Page.USER.getPage();
    }

    @RequestMapping(value = "/review", method = RequestMethod.GET)
    public String review(Model model) {
        return Page.REVIEW.getPage();
    }
}