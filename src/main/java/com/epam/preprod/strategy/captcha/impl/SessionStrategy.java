package com.epam.preprod.strategy.captcha.impl;

import com.epam.preprod.entity.Captcha;
import com.epam.preprod.strategy.captcha.CaptchaStorageStrategy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SessionStrategy implements CaptchaStorageStrategy {

    @Override
    public Integer getCurrentCaptchaID(HttpServletRequest request, HttpServletResponse response) {
        return (Integer) request.getSession().getAttribute("captchaID");

    }

    @Override
    public void setCurrentCaptchaID(HttpServletRequest request, HttpServletResponse response, Captcha captcha) {
        request.getSession().setAttribute("captchaID", captcha.getCaptchaId());
    }
}
