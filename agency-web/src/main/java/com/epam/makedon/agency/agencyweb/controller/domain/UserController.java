package com.epam.makedon.agency.agencyweb.controller.domain;

import com.epam.makedon.agency.agencydomain.domain.impl.User;
import com.epam.makedon.agency.agencydomain.service.UserService;
import com.epam.makedon.agency.agencyweb.util.Constant;
import com.epam.makedon.agency.agencyweb.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

/**
 * Controller for {@link User} class.
 *
 * @author Yahor Makedon
 * @version 1.0
 */

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(Model model, @RequestParam String login, @RequestParam String password) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(passwordEncoder.encode(password));
        try {
            if (userService.add(user)) {
                model.addAttribute(Constant.RESULT, Constant.ADDED);
            } else {
                model.addAttribute(Constant.RESULT, Constant.NOT_ADDED);
            }
        } catch (Exception e) {
            model.addAttribute(Constant.RESULT, Constant.NOT_ADDED);
        }
        return Constant.REDIRECT + Page.USER.getUrl();
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public String get(Model model, @RequestParam long id) {
        final String LOGIN = "login";
        final String ROLE = "role";
        final String REVIEW_LIST = "reviewList";
        final String TOUR_LIST = "tourList";

        try {
            Optional<User> opt = userService.get(id);
            if (opt.isPresent()) {
                model.addAttribute(LOGIN, opt.get().getLogin());
                model.addAttribute(ROLE, opt.get().getRole());
                model.addAttribute(REVIEW_LIST, opt.get().getReviewList());
                model.addAttribute(TOUR_LIST, opt.get().getTourList());
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
        return Page.USER.getPage();
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public String remove(Model model, @RequestParam long id) {
        try {
            Optional<User> opt = userService.get(id);
            if (opt.isPresent()) {
                if (userService.remove(opt.get())) {
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
        return Constant.REDIRECT + Page.USER.getUrl();
    }
}