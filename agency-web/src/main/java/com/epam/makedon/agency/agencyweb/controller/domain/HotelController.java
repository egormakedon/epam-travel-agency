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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/hotel")
public class HotelController {

    @Autowired
    private HotelService service;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(Model model, @RequestParam String name, @RequestParam String phone, @RequestParam byte stars) {
        Hotel hotel = new Hotel();
        hotel.setName(name);
        hotel.setPhone(phone);
        hotel.setStars(stars);
        if (service.add(hotel)) {
            model.addAttribute(Constant.RESULT, Constant.ADDED);
        } else {
            model.addAttribute(Constant.RESULT, Constant.NOT_ADDED);
        }
        return Constant.REDIRECT + Page.HOTEL.getUrl();
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public String get(Model model, @RequestParam long id) {
        try {
            Optional<Hotel> opt = service.get(id);
            if (opt.isPresent()) {
                model.addAttribute(Constant.RESULT, opt.get());
            } else {
                model.addAttribute(Constant.RESULT, Constant.NOT_FOUND);
            }
        } catch (Exception e) {
            if (Constant.NOT_FOUND_EXCEPTION_MESSAGE.equals(e.getMessage())) {
                model.addAttribute(Constant.RESULT, Constant.NOT_FOUND);
            } else {
                throw e;
            }
        }
        return Page.HOTEL.getPage();
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public String remove(Model model, @RequestParam long id) {
        try {
            Optional<Hotel> opt = service.get(id);
            if (opt.isPresent()) {
                if (service.remove(opt.get())) {
                    model.addAttribute(Constant.RESULT, Constant.REMOVED);
                } else {
                    model.addAttribute(Constant.RESULT, Constant.NOT_REMOVED);
                }
            } else {
                model.addAttribute(Constant.RESULT, Constant.NOT_FOUND);
            }
        } catch (Exception e) {
            if (Constant.NOT_FOUND_EXCEPTION_MESSAGE.equals(e.getMessage())) {
                model.addAttribute(Constant.RESULT, Constant.NOT_FOUND);
            } else {
                throw e;
            }
        }
        return Constant.REDIRECT + Page.HOTEL.getUrl();
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(RedirectAttributes redirectAttributes, @RequestParam long id, @RequestParam String name, @RequestParam String phone, @RequestParam String stars) {
        try {
            Optional<Hotel> opt = service.get(id);
            if (opt.isPresent()) {
                Hotel hotel = opt.get();
                if (!name.isEmpty()) hotel.setName(name);
                if (!phone.isEmpty()) hotel.setPhone(phone);
                if (!stars.isEmpty()) hotel.setStars(Byte.valueOf(stars));
                redirectAttributes.addFlashAttribute(Constant.RESULT, service.update(hotel).orElse(null));
            } else {
                redirectAttributes.addAttribute(Constant.RESULT, Constant.NOT_FOUND);
            }
        } catch (Exception e) {
            if (Constant.NOT_FOUND_EXCEPTION_MESSAGE.equals(e.getMessage())) {
                redirectAttributes.addAttribute(Constant.RESULT, Constant.NOT_FOUND);
            } else {
                throw e;
            }
        }
        return Constant.REDIRECT + Page.HOTEL.getUrl();
    }
}