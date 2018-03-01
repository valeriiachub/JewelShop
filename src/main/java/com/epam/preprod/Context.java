package com.epam.preprod;

import java.util.HashMap;
import java.util.Map;

import com.epam.preprod.strategy.captcha.CaptchaStorageStrategy;
import com.epam.preprod.strategy.captcha.impl.CookieStrategy;
import com.epam.preprod.strategy.captcha.impl.HiddenInputStrategy;
import com.epam.preprod.strategy.captcha.impl.SessionStrategy;

public class Context {

    public Map<String, CaptchaStorageStrategy> initializeCaptchaStrategies() {
        Map<String, CaptchaStorageStrategy> strategies = new HashMap<>();

        strategies.put("session", new SessionStrategy());
        strategies.put("cookie", new CookieStrategy());
        strategies.put("hidden", new HiddenInputStrategy());
        return strategies;
    }
}
