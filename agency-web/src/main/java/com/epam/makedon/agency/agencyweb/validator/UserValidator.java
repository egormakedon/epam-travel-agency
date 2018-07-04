package com.epam.makedon.agency.agencyweb.validator;

import com.epam.makedon.agency.agencydomain.domain.impl.User;
import com.epam.makedon.agency.agencydomain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Validator for {@link com.epam.makedon.agency.agencydomain.domain.impl.User} class,
 * implements {@link org.springframework.validation.Validator} interface.
 *
 * @author Yahor Makedon
 * @version 1.0
 */
@Component
public class UserValidator implements Validator {

    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final String CONFIRM_PASSWORD = "confirmPassword";

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, LOGIN, "validation.required.field");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, PASSWORD, "validation.required.field");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, CONFIRM_PASSWORD, "validation.required.field");

        User user = (User) o;
        if (user.getLogin().isEmpty() || user.getLogin().length() > 255) {
            errors.rejectValue(LOGIN, "validation.username.size");
        }

        if (userService.findByUsername(user.getLogin()).isPresent()) {
            errors.rejectValue(LOGIN, "validation.username.duplicated");
        }

        if (user.getPassword().isEmpty() || user.getLogin().length() > 255) {
            errors.rejectValue(PASSWORD, "validation.password.size");
        }

        if (!user.getPassword().equals(user.getConfirmPassword())) {
            errors.rejectValue(CONFIRM_PASSWORD, "validation.password.dont.match");
        }
    }
}