package com.epam.makedon.agency.agencyweb.controller.domain;

import com.epam.makedon.agency.agencydomain.domain.impl.Review;
import com.epam.makedon.agency.agencydomain.domain.impl.Tour;
import com.epam.makedon.agency.agencydomain.domain.impl.User;
import com.epam.makedon.agency.agencydomain.service.ReviewService;
import com.epam.makedon.agency.agencydomain.service.TourService;
import com.epam.makedon.agency.agencydomain.service.UserService;
import com.epam.makedon.agency.agencyweb.domain.Constant;
import com.epam.makedon.agency.agencyweb.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Optional;

@Controller
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    private ReviewService service;

    @Autowired
    private TourService tourService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(Model model, long tourId, long userId, String content) {
        Tour tour;
        try {
            Optional<Tour> opt = tourService.get(tourId);
            if (opt.isPresent()) {
                tour = opt.get();
            } else {
                model.addAttribute(Constant.RESULT, Constant.NOT_FOUND + "Tour");
                return Page.REVIEW.getPage();
            }
        } catch (Exception e) {
            if (Constant.NOT_FOUND_EXCEPTION_MESSAGE.equals(e.getMessage())) {
                model.addAttribute(Constant.RESULT, Constant.NOT_FOUND + "Tour");
                return Page.REVIEW.getPage();
            } else {
                throw e;
            }
        }

        User user;
        try {
            Optional<User> opt = userService.get(userId);
            if (opt.isPresent()) {
                user = opt.get();
            } else {
                model.addAttribute(Constant.RESULT, Constant.NOT_FOUND + "User");
                return Page.REVIEW.getPage();
            }
        } catch (Exception e) {
            if (Constant.NOT_FOUND_EXCEPTION_MESSAGE.equals(e.getMessage())) {
                model.addAttribute(Constant.RESULT, Constant.NOT_FOUND + "User");
                return Page.REVIEW.getPage();
            } else {
                throw e;
            }
        }

        Review review = new Review();
        review.setTour(tour);
        review.setUser(user);
        review.setContent(content);

        if (service.add(review)) {
            model.addAttribute(Constant.RESULT, Constant.ADDED);
        } else {
            model.addAttribute(Constant.RESULT, Constant.NOT_ADDED);
        }
        return Constant.REDIRECT + Page.REVIEW.getUrl();
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