package com.epam.preprod.dao.impl.captcha;

import com.epam.preprod.dao.CaptchaDAO;
import com.epam.preprod.entity.Captcha;

import java.util.Map;

public class CaptchaDAOImpl implements CaptchaDAO {
    private Map<Integer, Captcha> captchaMap;

    public CaptchaDAOImpl(Map<Integer, Captcha> captchaMap) {
        this.captchaMap = captchaMap;
    }

    @Override
    public void createCaptcha(Captcha captcha) {
        captchaMap.put(captcha.getCaptchaId(), captcha);
    }

    @Override
    public Captcha getCaptcha(int id) {
        return captchaMap.get(id);
    }

    @Override
    public Map<Integer, Captcha> getCaptchaMap() {
        return captchaMap;
    }
}
