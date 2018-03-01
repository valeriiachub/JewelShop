package com.epam.preprod.strategy.locale.impl;

import com.epam.preprod.strategy.locale.LocaleStorageStrategy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

public class SessionStorageStrategy implements LocaleStorageStrategy {

    @Override
    public Locale getLocale(HttpServletRequest request) {
        return (Locale) request.getSession().getAttribute("locale");
    }

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
        request.getSession().setAttribute("locale", locale);
    }
}
