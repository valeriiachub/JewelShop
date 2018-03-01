package com.epam.preprod.captcha;

import com.epam.preprod.entity.Captcha;
import com.epam.preprod.service.CaptchaService;

import java.util.Date;
import java.util.Map;
import java.util.Random;

public class CaptchaCreator {
    private CaptchaService captchaService;

    public CaptchaCreator(CaptchaService captchaService) {
        this.captchaService = captchaService;
    }

    public Captcha create() {
        String newRandomString = getNewRandomString();
        int captchaID = getNextID(captchaService.getCaptchaMap());

        return new Captcha(captchaID, newRandomString, new Date());

    }

    private String getNewRandomString() {
        int totalChars = 5;
        Random randomChar = new Random();
        return (Long.toString(Math.abs(randomChar.nextLong()), 36)).substring(0, totalChars);
    }

    private int getNextID(Map<Integer, Captcha> captchaMap) {
        int maxID = 0;

        for (Map.Entry<Integer, Captcha> currentProduct : captchaMap.entrySet()) {
            if (currentProduct.getKey() > maxID) {
                maxID = currentProduct.getKey();
            }
        }
        return maxID + 1;
    }

}
