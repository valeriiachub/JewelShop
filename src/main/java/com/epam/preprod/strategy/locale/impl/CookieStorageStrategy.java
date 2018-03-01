package com.epam.preprod.strategy.locale.impl;

import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.preprod.strategy.locale.LocaleStorageStrategy;

public class CookieStorageStrategy implements LocaleStorageStrategy {

    @Override
    public Locale getLocale(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (Objects.nonNull(cookies)) {
            return new Locale(Arrays.stream(cookies)
                    .filter(cookie -> cookie.getName()
                            .equals("locale"))
                    .findFirst()
                    .orElse(new Cookie("locale", ""))
                    .getValue());
        }
        return new Locale("");
    }

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
        response.addCookie(new Cookie("locale", locale.toString()));
    }
}
