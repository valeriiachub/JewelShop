package com.epam.preprod.service.impl;

import com.epam.preprod.dao.CaptchaDAO;
import com.epam.preprod.entity.Captcha;
import com.epam.preprod.service.CaptchaService;

import java.util.Map;
import java.util.Objects;

public class CaptchaServiceImpl implements CaptchaService {
    private CaptchaDAO captchaDAO;

    public CaptchaServiceImpl(CaptchaDAO captchaDAO) {
        this.captchaDAO = captchaDAO;
    }

    @Override
    public void addCaptcha(Captcha captcha) {
        captchaDAO.createCaptcha(captcha);
    }

    @Override
    public Captcha getCaptcha(int id) {
        return captchaDAO.getCaptcha(id);
    }

    @Override
    public String getCurrentCaptchaValue(int id) {
        Captcha captcha = captchaDAO.getCaptcha(id);
        if (Objects.nonNull(captcha)) {
            return captchaDAO.getCaptcha(id).getCaptchaValue();
        }
        return new String();
    }

    @Override
    public Map<Integer, Captcha> getCaptchaMap() {
        return captchaDAO.getCaptchaMap();
    }
}
