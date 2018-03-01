package com.epam.preprod.validator;


import com.epam.preprod.util.RegistrationBean;

import java.util.HashMap;
import java.util.Map;

public class Validator {
    private static final String NAME_PATTERN = "^[a-zA-Z]+$";
    private static final String PASSWORD_PATTERN = "^[A-Za-z]\\w{7,14}$";
    private static final String EMAIL_PATTERN = "^\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,3}$";
    private static final String EMAIL_ERROR_MESSAGE = "Please, enter correct email";
    private static final String PASSWORD_ERROR_MESSAGE = "Please, enter correct password";
    private static final String LAST_NAME_ERROR_MESSAGE = "Please, enter correct last name";
    private static final String FIRST_NAME_ERROR_MESSAGE = "Please, enter correct first name";
    private static final String INCORRECT_CAPTCHA_MESSAGE = "Incorrect code, please, try again";


    public Map<String, String> validateRegistrationBean(RegistrationBean bean, String correctCaptchaValue) {
        Map<String, String> errors = new HashMap<>();
        if (!bean.getFirstName().matches(NAME_PATTERN)) {
            errors.put("firstName", FIRST_NAME_ERROR_MESSAGE);
        }
        if (!bean.getLastName().matches(NAME_PATTERN)) {
            errors.put("lastName", LAST_NAME_ERROR_MESSAGE);
        }
        if (!bean.getEmail().matches(EMAIL_PATTERN)) {
            errors.put("email", EMAIL_ERROR_MESSAGE);
        }
        if (!bean.getPassword().matches(PASSWORD_PATTERN)) {
            errors.put("password", PASSWORD_ERROR_MESSAGE);
        }
        if (!bean.getCaptchaValue().equals(correctCaptchaValue)) {
            errors.put("incorrectCaptcha", INCORRECT_CAPTCHA_MESSAGE);
        }
        return errors;
    }
}
