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

    private static final String VALIDATION_REQUIRED_FIELD = "validation.required.field";
    private static final String VALIDATION_LOGIN_SIZE = "validation.login.size";
    private static final String VALIDATION_LOGIN_DUPLICATED = "validation.login.duplicated";
    private static final String VALIDATION_PASSWORD_SIZE = "validation.password.size";
    private static final String VALIDATION_PASSWORD_DONT_MATCH = "validation.password.dont.match";

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, LOGIN, VALIDATION_REQUIRED_FIELD);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, PASSWORD, VALIDATION_REQUIRED_FIELD);

        User user = (User) o;
        if (user.getLogin().isEmpty() || user.getLogin().length() > 255) {
            errors.rejectValue(LOGIN, VALIDATION_LOGIN_SIZE);
        }

        if (userService.findByUsername(user.getLogin()).isPresent()) {
            errors.rejectValue(LOGIN, VALIDATION_LOGIN_DUPLICATED);
        }

        if (user.getPassword().isEmpty() || user.getLogin().length() > 255) {
            errors.rejectValue(PASSWORD, VALIDATION_PASSWORD_SIZE);
        }

        if (!user.getPassword().equals(user.getConfirmPassword())) {
            errors.rejectValue(CONFIRM_PASSWORD, VALIDATION_PASSWORD_DONT_MATCH);
        }
    }
}