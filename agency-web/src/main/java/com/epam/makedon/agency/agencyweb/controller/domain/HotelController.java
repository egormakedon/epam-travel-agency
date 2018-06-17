package com.epam.makedon.agency.agencyweb.controller.domain;

import com.epam.makedon.agency.agencydomain.domain.impl.Hotel;
import com.epam.makedon.agency.agencydomain.service.HotelService;
import com.epam.makedon.agency.agencyweb.domain.Constant;
import com.epam.makedon.agency.agencyweb.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("/hotel")
public class HotelController {

    @Autowired
    private HotelService service;

    private static final String RESULT = "result";

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(Model model, @RequestParam String name, @RequestParam String phone, @RequestParam byte stars) {
        Hotel hotel = new Hotel();
        hotel.setName(name);
        hotel.setPhone(phone);
        hotel.setStars(stars);
        service.add(hotel);
        model.addAttribute(RESULT,"hotel added successfully");
        return Constant.REDIRECT + Page.HOTEL.getUrl();
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public String get(Model model, @RequestParam long id) {
        final String NOT_FOUND_EXCEPTION_MESSAGE = "javax.persistence.NoResultException: No entity found for query";
        final String NOT_FOUND = "notFound";
        try {
            Optional<Hotel> opt = service.get(id);
            model.addAttribute(RESULT, opt.orElse(null));
        } catch (Exception e) {
            if (NOT_FOUND_EXCEPTION_MESSAGE.equals(e.getMessage())) {
                model.addAttribute(RESULT, NOT_FOUND);
            } else {
                throw e;
            }
        }
        return Page.HOTEL.getPage();
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public void remove() {

    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public void update() {

    }
}