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

    private static final String NOT_FOUND_EXCEPTION_MESSAGE = "javax.persistence.NoResultException: No entity found for query";
    private static final String NOT_FOUND = "notFound";
    private static final String RESULT = "result";

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(Model model, @RequestParam String name, @RequestParam String phone, @RequestParam byte stars) {
        final String ADDED = "added";
        final String NOT_ADDED = "notAdded";

        Hotel hotel = new Hotel();
        hotel.setName(name);
        hotel.setPhone(phone);
        hotel.setStars(stars);
        if (service.add(hotel)) {
            model.addAttribute(RESULT, ADDED);
        } else {
            model.addAttribute(RESULT, NOT_ADDED);
        }
        return Constant.REDIRECT + Page.HOTEL.getUrl();
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public String get(Model model, @RequestParam long id) {
        try {
            Optional<Hotel> opt = service.get(id);
            if (opt.isPresent()) {
                model.addAttribute(RESULT, opt.get());
            } else {
                model.addAttribute(RESULT, NOT_FOUND);
            }
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
    public String remove(Model model, @RequestParam long id) {
        final String REMOVED = "removed";
        final String NOT_REMOVED = "notRemoved";

        try {
            Optional<Hotel> opt = service.get(id);
            if (opt.isPresent()) {
                if (service.remove(opt.get())) {
                    model.addAttribute(RESULT, REMOVED);
                } else {
                    model.addAttribute(RESULT, NOT_REMOVED);
                }
            } else {
                model.addAttribute(RESULT, NOT_FOUND);
            }
        } catch (Exception e) {
            if (NOT_FOUND_EXCEPTION_MESSAGE.equals(e.getMessage())) {
                model.addAttribute(RESULT, NOT_FOUND);
            } else {
                throw e;
            }
        }
        return Page.HOTEL.getPage();
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(Model model, @RequestParam long id, @RequestParam String name, @RequestParam String phone, @RequestParam Byte stars) {
        try {
            Optional<Hotel> opt = service.get(id);
            if (opt.isPresent()) {
                Hotel hotel = opt.get();
                if (name != null) hotel.setName(name);
                if (phone != null) hotel.setPhone(phone);
                if (stars != null) hotel.setStars(stars);
                model.addAttribute(RESULT, service.update(hotel).orElse(null));
            } else {
                model.addAttribute(RESULT, NOT_FOUND);
            }
        } catch (Exception e) {
            if (NOT_FOUND_EXCEPTION_MESSAGE.equals(e.getMessage())) {
                model.addAttribute(RESULT, NOT_FOUND);
            } else {
                throw e;
            }
        }
        return Page.HOTEL.getPage();
    }
}