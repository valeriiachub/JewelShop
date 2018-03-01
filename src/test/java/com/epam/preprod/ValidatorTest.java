package com.epam.preprod;

import com.epam.preprod.util.RegistrationBean;
import com.epam.preprod.validator.Validator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Map;

import static org.mockito.Mockito.when;

public class ValidatorTest {
    @Mock
    private RegistrationBean registrationBean;
    private String correctCaptchaValue = "1jy55";
    private Validator validator;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        validator = new Validator();
        when(registrationBean.getFirstName()).thenReturn("Valeriia");
        when(registrationBean.getLastName()).thenReturn("Chub");
    }

    @Test
    public void testValidateOK() {

        when(registrationBean.getEmail()).thenReturn("qwerty@gmail.com");
        when(registrationBean.getPassword()).thenReturn("Nhn67332");
        when(registrationBean.getCaptchaValue()).thenReturn("1jy55");
        when(registrationBean.isSendNews()).thenReturn(true);

        Map<String, String> validateErrors = validator.validateRegistrationBean(registrationBean, correctCaptchaValue);

        Assert.assertEquals(true, validateErrors.isEmpty());
    }

    @Test
    public void testValidateWrongEmail() {
        when(registrationBean.getEmail()).thenReturn("nbnbnb");
        when(registrationBean.getPassword()).thenReturn("Nhn67332");
        when(registrationBean.getCaptchaValue()).thenReturn("1jy55");
        when(registrationBean.isSendNews()).thenReturn(true);

        Map<String, String> validateErrors = validator.validateRegistrationBean(registrationBean, correctCaptchaValue);

        Assert.assertEquals("Please, enter correct email", validateErrors.get("email"));
    }

    @Test
    public void testValidateWrongFirstName() {
        when(registrationBean.getFirstName()).thenReturn("Valeriia5699");
        when(registrationBean.getEmail()).thenReturn("qwerty@gmail.com");
        when(registrationBean.getPassword()).thenReturn("Nhn67332");
        when(registrationBean.getCaptchaValue()).thenReturn("1jy55");
        when(registrationBean.isSendNews()).thenReturn(true);

        Map<String, String> validateErrors = validator.validateRegistrationBean(registrationBean, correctCaptchaValue);

        Assert.assertEquals("Please, enter correct first name", validateErrors.get("firstName"));
    }


}
