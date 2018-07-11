package com.epam.makedon.agency.agencyweb.controller;

import com.epam.makedon.agency.agencydomain.domain.impl.Review;
import com.epam.makedon.agency.agencydomain.domain.impl.Tour;
import com.epam.makedon.agency.agencydomain.domain.impl.User;
import com.epam.makedon.agency.agencydomain.service.ReviewService;
import com.epam.makedon.agency.agencydomain.service.TourService;
import com.epam.makedon.agency.agencydomain.service.UserService;
import com.epam.makedon.agency.agencyweb.util.Constant;
import com.epam.makedon.agency.agencyweb.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

/**
 * Controller for {@link Review} class.
 *
 * @author Yahor Makedon
 * @version 1.0
 */

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
    public String add(Model model, @RequestParam long tourId, @RequestParam long userId, @RequestParam String content) {
        Tour tour = takeTour(tourId);

        if (tour == null) {
            model.addAttribute(Constant.RESULT, Constant.NOT_FOUND + "Tour");
            return Page.REVIEW.getPage();
        }

        User user = takeUser(userId);
        if (user == null) {
            model.addAttribute(Constant.RESULT, Constant.NOT_FOUND + "User");
            return Page.REVIEW.getPage();
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
    public String get(Model model, @RequestParam long id) {
        try {
            Optional<Review> opt = service.get(id);
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
        return Page.REVIEW.getPage();
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public String remove(Model model, @RequestParam long id) {
        try {
            Optional<Review> opt = service.get(id);
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
        return Constant.REDIRECT + Page.REVIEW.getUrl();
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(RedirectAttributes redirectAttributes, @RequestParam long id, @RequestParam String tourId,
                         @RequestParam String userId, @RequestParam String content) {
        try {
            Optional<Review> opt = service.get(id);
            if (opt.isPresent()) {
                Review review = opt.get();

                if (!tourId.isEmpty()) {
                    Tour tour = takeTour(Long.valueOf(tourId));
                    if (tour == null) {
                        redirectAttributes.addAttribute(Constant.RESULT, Constant.NOT_FOUND + "Tour");
                        return Page.REVIEW.getPage();
                    } else {
                        review.setTour(tour);
                    }
                }

                if (!userId.isEmpty()) {
                    User user = takeUser(Long.valueOf(userId));
                    if (user == null) {
                        redirectAttributes.addAttribute(Constant.RESULT, Constant.NOT_FOUND + "User");
                        return Page.REVIEW.getPage();
                    } else {
                        review.setUser(user);
                    }
                }

                if (!content.isEmpty()) review.setContent(content);
                redirectAttributes.addFlashAttribute(Constant.RESULT, service.update(review).orElse(null));
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
        return Constant.REDIRECT + Page.REVIEW.getUrl();
    }

    private Tour takeTour(long tourId) {
        try {
            Optional<Tour> opt = tourService.get(tourId);
            return opt.orElse(null);
        } catch (Exception e) {
            if (Constant.NOT_FOUND_EXCEPTION_MESSAGE.equals(e.getMessage())) {
                return null;
            } else {
                throw e;
            }
        }
    }
    private User takeUser(long userId) {
        try {
            Optional<User> opt = userService.get(userId);
            return opt.orElse(null);
        } catch (Exception e) {
            if (Constant.NOT_FOUND_EXCEPTION_MESSAGE.equals(e.getMessage())) {
                return null;
            } else {
                throw e;
            }
        }
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String review() {
        return Page.REVIEW.getPage();
    }
}