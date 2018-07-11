package com.epam.makedon.agency.agencyweb.controller;

import com.epam.makedon.agency.agencyweb.util.Constant;
import com.epam.makedon.agency.agencyweb.util.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RegistrationController {

//    @Autowired
//    private UserService userService;

//    @Autowired
//    private UserValidator userValidator;

//    @Autowired
//    private SecurityService securityService;

//    @Autowired
//    private BCryptPasswordEncoder passwordEncoder;

    // Registration

//    @RequestMapping(value = "/registration", method = RequestMethod.GET)
//    public String registration(Model model) {
//        model.addAttribute("user", new User());
//        return Page.REGISTRATION.getPage();
//    }
//
//    @RequestMapping(value = "/registration", method = RequestMethod.POST)
//    public String registration(@ModelAttribute("user") User user, BindingResult bindingResult, Model model) {
//        userValidator.validate(user, bindingResult);
//
//        if (bindingResult.hasErrors()) {
//            return Page.REGISTRATION.getPage();
//        }
//
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        userService.add(user);
//        securityService.autoLogin(user.getLogin(), user.getConfirmPassword());
//        return Constant.REDIRECT + Page.INDEX.getUrl();
//    }

    // Log in

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {
        if (error != null) {
            model.addAttribute(Constant.ERROR, Constant.USER_OR_PASSWORD_IS_INCORRECT);
        }

        if (logout != null) {
            model.addAttribute(Constant.RESULT, Constant.LOGGED_OUT_SUCCESSFULLY);
        }

        return Page.LOGIN.getPage();
    }
}