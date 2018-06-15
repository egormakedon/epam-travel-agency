package com.epam.makedon.agency.agencyweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/hotel")
public class HotelController {

//    @Autowired
//    @Setter
//    private HotelService service;

    @RequestMapping(method = RequestMethod.GET, value = "/hello")
    public String kek(Model model) {
        model.addAttribute("user", "kekeke");
        return "hello";
    }
}