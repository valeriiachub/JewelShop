package com.epam.preprod.service;

import com.epam.preprod.entity.Captcha;

import java.util.Map;

public interface CaptchaService {

    void addCaptcha(Captcha captcha);

    Captcha getCaptcha(int id);

    String getCurrentCaptchaValue(int id);

    Map<Integer, Captcha> getCaptchaMap();
}
