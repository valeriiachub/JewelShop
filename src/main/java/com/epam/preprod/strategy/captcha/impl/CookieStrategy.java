package com.epam.preprod.strategy.captcha.impl;

import com.epam.preprod.entity.Captcha;
import com.epam.preprod.strategy.captcha.CaptchaStorageStrategy;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

public class CookieStrategy implements CaptchaStorageStrategy {
    @Override
    public Integer getCurrentCaptchaID(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        return Integer.parseInt(Arrays.stream(cookies)
                .filter(cookie -> cookie.getName()
                        .equals("captchaID"))
                .findFirst()
                .get()
                .getValue());
    }

    @Override
    public void setCurrentCaptchaID(HttpServletRequest request, HttpServletResponse response, Captcha captcha) {
        response.addCookie(new Cookie("captchaID", String.valueOf(captcha.getCaptchaId())));
    }
}
