package com.epam.preprod.strategy.captcha;

import com.epam.preprod.entity.Captcha;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface CaptchaStorageStrategy {

    Integer getCurrentCaptchaID(HttpServletRequest request, HttpServletResponse response);

    void setCurrentCaptchaID(HttpServletRequest request, HttpServletResponse response, Captcha captcha);
}
