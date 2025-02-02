package com.epam.makedon.agency.agencyweb.controller;

import com.epam.makedon.agency.agencydomain.domain.impl.Hotel;
import com.epam.makedon.agency.agencydomain.service.HotelService;
import com.epam.makedon.agency.agencyweb.util.Constant;
import com.epam.makedon.agency.agencyweb.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

/**
 * Controller for {@link Hotel} class.
 *
 * @author Yahor Makedon
 * @version 1.0
 */

@Controller

public class HotelController {

    @Autowired
    private HotelService hotelService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/hotel", method = RequestMethod.GET)
    public String getHotelPage(Model model) {

        model.addAttribute(Constant.URL, Page.HOTEL.getUrl());
        return Page.HOTEL.getPage();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/hotel/add", method = RequestMethod.POST)
    public String add(Model model, @RequestParam String name, @RequestParam String phone, @RequestParam byte stars) {
        Hotel hotel = new Hotel();
        hotel.setName(name);
        hotel.setPhone(phone);
        hotel.setStars(stars);

        if (hotelService.add(hotel)) {
            model.addAttribute(Constant.RESULT, Constant.ADDED);
        } else {
            model.addAttribute(Constant.RESULT, Constant.NOT_ADDED);
        }

        model.addAttribute(Constant.URL, Page.HOTEL.getUrl());
        return Constant.REDIRECT + Page.HOTEL.getUrl();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/hotel/get", method = RequestMethod.GET)
    public String get(Model model, @RequestParam long id) {
        try {
            Optional<Hotel> opt = hotelService.get(id);

            if (opt.isPresent()) {
                model.addAttribute(Constant.RESULT, opt.get());
            } else {
                model.addAttribute(Constant.RESULT, Constant.NOT_FOUND);
            }
        } catch (Exception e) {
            model.addAttribute(Constant.RESULT, Constant.NOT_FOUND);
        }

        model.addAttribute(Constant.URL, Page.HOTEL.getUrl());
        return Page.HOTEL.getPage();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/hotel/remove", method = RequestMethod.POST)
    public String remove(Model model, @RequestParam long id) {
        try {
            Optional<Hotel> opt = hotelService.get(id);

            if (opt.isPresent()) {
                if (hotelService.remove(opt.get())) {
                    model.addAttribute(Constant.RESULT, Constant.REMOVED);
                } else {
                    model.addAttribute(Constant.RESULT, Constant.NOT_REMOVED);
                }
            } else {
                model.addAttribute(Constant.RESULT, Constant.NOT_FOUND);
            }
        } catch (Exception e) {
            model.addAttribute(Constant.RESULT, Constant.NOT_FOUND);
        }

        model.addAttribute(Constant.URL, Page.HOTEL.getUrl());
        return Constant.REDIRECT + Page.HOTEL.getUrl();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/hotel/update", method = RequestMethod.POST)
    public String update(RedirectAttributes redirectAttributes, @RequestParam long id, @RequestParam String name, @RequestParam String phone, @RequestParam String stars) {
        try {
            Optional<Hotel> opt = hotelService.get(id);

            if (opt.isPresent()) {
                Hotel hotel = opt.get();
                if (!name.isEmpty()) hotel.setName(name);
                if (!phone.isEmpty()) hotel.setPhone(phone);
                if (!stars.isEmpty()) hotel.setStars(Byte.valueOf(stars));
                redirectAttributes.addFlashAttribute(Constant.RESULT, hotelService.update(hotel).orElse(null));
            } else {
                redirectAttributes.addAttribute(Constant.RESULT, Constant.NOT_FOUND);
            }
        } catch (Exception e) {
            redirectAttributes.addAttribute(Constant.RESULT, Constant.NOT_FOUND);
        }

        redirectAttributes.addAttribute(Constant.URL, Page.HOTEL.getUrl());
        return Constant.REDIRECT + Page.HOTEL.getUrl();
    }
}