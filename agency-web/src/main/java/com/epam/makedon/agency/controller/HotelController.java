package com.epam.makedon.agency.controller;

import com.epam.makedon.agency.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/hotel")
public class HotelController {

    @Autowired
    private HotelService service;

    @RequestMapping(method = RequestMethod.GET, value = "/hello")
    public String kek(Model model) {
        model.addAttribute("user", "kekeke");
        return "hello";
    }
}