package com.epam.preprod.filter;

import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Locale;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.epam.preprod.strategy.locale.LocaleStorageStrategy;

public class LocalizationServletTest {

    @Mock
    private HttpSession httpSession;
    @Mock
    private FilterConfig filterConfig;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private FilterChain filterChain;
    @Mock
    private LocaleStorageStrategy localeStorageStrategy;
    @Mock
    private Enumeration<Locale> enumLocales;

    private Filter filter;
    private Locale defaultLocale = new Locale("en");

    @Before
    public void init() throws ServletException {
        MockitoAnnotations.initMocks(this);
        filter = new LocalizationFilter();
        when(request.getSession()).thenReturn(httpSession);
        when(filterConfig.getInitParameter("defaultLocale")).thenReturn("en");
        when(filterConfig.getInitParameter("supportedLocales")).thenReturn("en ru");
        when(filterConfig.getInitParameter("localeStorageStrategy")).thenReturn("session");

        filter.init(filterConfig);
    }

    @Test
    public void assertChoosingDefaultLocale() throws IOException, ServletException {
        when(httpSession.getAttribute("locale")).thenReturn(null);
        when(enumLocales.hasMoreElements()).thenReturn(true);
        when(request.getLocales()).thenReturn(enumLocales);
        when(enumLocales.hasMoreElements()).thenReturn(false);

        filter.doFilter(request, response, filterChain);
        verify(httpSession, atLeast(1)).setAttribute("locale", defaultLocale);
    }

    @Test
    public void assertChoosingRuLocale() throws IOException, ServletException {
        Locale locale = new Locale("ru");
        when(request.getLocale()).thenReturn(defaultLocale);
        when(request.getParameter("lang")).thenReturn("ru");
        when(httpSession.getAttribute("locale")).thenReturn(locale);
        when(localeStorageStrategy.getLocale(request)).thenReturn(locale);

        filter.doFilter(request, response, filterChain);
        verify(httpSession, atLeast(1)).setAttribute("locale", locale);
    }

    @Test
    public void verifyAbsentLocaleInSession() throws IOException, ServletException {
        when(httpSession.getAttribute("locale")).thenReturn(null);
        when(request.getLocales()).thenReturn(enumLocales);
        when(enumLocales.hasMoreElements()).thenReturn(true);
        when(enumLocales.nextElement()).thenReturn(defaultLocale);

        filter.doFilter(request, response, filterChain);
        verify(httpSession, atLeast(1)).setAttribute("locale", defaultLocale);
    }
}