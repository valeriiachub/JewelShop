package com.epam.preprod.dao;

import com.epam.preprod.entity.Captcha;

import java.util.Map;

public interface CaptchaDAO {
    void createCaptcha(Captcha captcha);

    Captcha getCaptcha(int id);

    Map<Integer, Captcha> getCaptchaMap();
}
