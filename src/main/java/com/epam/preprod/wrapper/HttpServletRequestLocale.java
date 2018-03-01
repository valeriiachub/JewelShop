package com.epam.preprod.wrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Locale;

public class HttpServletRequestLocale extends HttpServletRequestWrapper {

    private Locale locale;

    public HttpServletRequestLocale(HttpServletRequest request) {
        super(request);
    }

    @Override
    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }
}

