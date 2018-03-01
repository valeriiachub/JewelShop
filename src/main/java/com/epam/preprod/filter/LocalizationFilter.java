package com.epam.preprod.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.epam.preprod.strategy.locale.LocaleStorageStrategy;
import com.epam.preprod.strategy.locale.impl.CookieStorageStrategy;
import com.epam.preprod.strategy.locale.impl.SessionStorageStrategy;
import com.epam.preprod.wrapper.HttpServletRequestLocale;

public class LocalizationFilter implements Filter {

    private static final Logger logger = Logger.getLogger(LocalizationFilter.class);

    private String defaultLocale;
    private List<String> supportedLocales;
    private LocaleStorageStrategy strategy;

    @Override
    public void init(FilterConfig filterConfig) {
        defaultLocale = filterConfig.getInitParameter("defaultLocale");
        supportedLocales = Arrays.asList(filterConfig.getInitParameter("supportedLocales").split(" "));
        String localeStorageStrategy = filterConfig.getInitParameter("localeStorageStrategy");
        if ("session".equals(localeStorageStrategy)) {
            strategy = new SessionStorageStrategy();
        } else {
            strategy = new CookieStorageStrategy();
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        Locale locale;

        String lang = req.getParameter("lang");
        logger.debug("Locale -->" + lang);

        if (lang != null && !lang.isEmpty() && supportedLocales.contains(lang)) {
            locale = new Locale(lang);
            strategy.setLocale(req, resp, locale);
        } else {
            locale = strategy.getLocale(req);
            if (locale == null) {
                Enumeration<Locale> localeEnumeration = request.getLocales();
                locale = findLocale(localeEnumeration);
                strategy.setLocale(req, resp, locale);
            }
        }

        HttpServletRequestLocale requestWrapper = new HttpServletRequestLocale(req);
        requestWrapper.setLocale(locale);

        chain.doFilter(requestWrapper, response);
    }

    @Override
    public void destroy() {

    }

    private Locale findLocale(Enumeration<Locale> locales) {
        Locale foundLocale = new Locale(defaultLocale);
        while (locales.hasMoreElements()) {
            Locale tempLocale = locales.nextElement();
            if (supportedLocales.contains(tempLocale.getLanguage())) {
                foundLocale = tempLocale;
                break;
            }
        }

        return foundLocale;
    }
}
