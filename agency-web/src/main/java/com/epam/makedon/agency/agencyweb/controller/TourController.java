package com.epam.makedon.agency.agencyweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/tour")
public class TourController {

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public void add() {

    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public void get() {

    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public void remove() {

    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public void update() {

    }
}