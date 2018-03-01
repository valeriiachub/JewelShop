package com.epam.preprod.captcha;

import com.epam.preprod.entity.Captcha;

import java.util.Date;
import java.util.Map;

public class CaptchaRemover implements Runnable {
    private final Object MONITOR = new Object();
    private Map<Integer, Captcha> captchaMap;
    private long timeout;


    public CaptchaRemover(Map<Integer, Captcha> captchaMap, long timeout) {
        this.captchaMap = captchaMap;
        this.timeout = timeout;
    }

    @Override
    public void run() {
        synchronized (MONITOR) {
            while (true) {
                for (Map.Entry<Integer, Captcha> captcha : captchaMap.entrySet()) {
                    if (!isCaptchaValid(captcha.getValue())) {
                        captchaMap.remove(captcha.getKey());
                    }
                }
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private boolean isCaptchaValid(Captcha captcha) {
        long delta = new Date().getTime() - captcha.getCreationDate().getTime();
        return delta < timeout;
    }
}
