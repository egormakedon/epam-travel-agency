package com.epam.makedon.agency.agencyweb.controller;

import com.epam.makedon.agency.agencyweb.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RedirectController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return Page.INDEX.getPage();
    }

    @RequestMapping(value = "/hotel", method = RequestMethod.GET)
    public String hotel() {
        return Page.HOTEL.getPage();
    }

    @RequestMapping(value = "/tour", method = RequestMethod.GET)
    public String tour() {
        return Page.TOUR.getPage();
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String user() {
        return Page.USER.getPage();
    }

    @RequestMapping(value = "/review", method = RequestMethod.GET)
    public String review() {
        return Page.REVIEW.getPage();
    }
}