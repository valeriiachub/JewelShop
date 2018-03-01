package com.epam.preprod.strategy.captcha.impl;

import com.epam.preprod.entity.Captcha;
import com.epam.preprod.strategy.captcha.CaptchaStorageStrategy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HiddenInputStrategy implements CaptchaStorageStrategy {
    @Override
    public Integer getCurrentCaptchaID(HttpServletRequest request, HttpServletResponse response) {
        return Integer.valueOf(request.getParameter("captchaID"));
    }

    @Override
    public void setCurrentCaptchaID(HttpServletRequest request, HttpServletResponse response, Captcha captcha) {
        request.setAttribute("captchaID", captcha.getCaptchaId());
    }
}
