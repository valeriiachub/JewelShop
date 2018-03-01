package com.epam.preprod.servlet;

import com.epam.preprod.captcha.CaptchaCreator;
import com.epam.preprod.entity.Captcha;
import com.epam.preprod.service.impl.CaptchaServiceImpl;
import com.epam.preprod.service.impl.UserServiceImpl;
import com.epam.preprod.strategy.captcha.CaptchaStorageStrategy;
import com.epam.preprod.util.RegistrationBean;
import com.epam.preprod.validator.Validator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

import static org.mockito.Mockito.*;

public class RegistrationServletTest {
    private RegistrationServlet registrationServlet;
    @Mock
    private HttpServletRequest request;
    @Mock
    private ServletConfig servletConfig;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpSession session;
    @Mock
    private RequestDispatcher dispatcher;
    @Mock
    private ServletContext servletContext;
    @Mock
    private RegistrationBean registrationBean;
    @Mock
    private UserServiceImpl userService;
    @Mock
    private CaptchaServiceImpl captchaService;
    @Mock
    private CaptchaStorageStrategy captchaStorageStrategy;
    @Mock
    private Map<Integer, Captcha> captchaMap;
    @Mock
    private Map<String, String> errors;
    @Mock
    private CaptchaCreator captchaCreator;
    @Mock
    private Captcha captcha;
    @Mock
    private Validator validator;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        registrationServlet = new RegistrationServlet();
        when(servletConfig.getServletContext()).thenReturn(servletContext);
        when(request.getSession()).thenReturn(session);
        when(request.getServletContext()).thenReturn(servletContext);

        when(servletContext.getAttribute("captchaStorageStrategy")).thenReturn(captchaStorageStrategy);
        when(servletContext.getAttribute("captchaService")).thenReturn(captchaService);
        when(servletContext.getAttribute("userService")).thenReturn(userService);
        when(servletContext.getAttribute("validator")).thenReturn(validator);

        when(request.getParameter("firstName")).thenReturn("Name");
        when(request.getParameter("lastName")).thenReturn("Surname");
        when(request.getParameter("email")).thenReturn("email@mail.com");
        when(request.getParameter("password")).thenReturn("Vb676732");
        when(request.getParameter("captchaValue")).thenReturn("fr455");
        when(captchaCreator.create()).thenReturn(captcha);
        when(captchaStorageStrategy.getCurrentCaptchaID(request, response)).thenReturn(0);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {

        when(captchaService.getCaptchaMap()).thenReturn(captchaMap);
        when(request.getRequestDispatcher("registrationPage.jsp")).thenReturn(dispatcher);
        when(session.getAttribute("bean")).thenReturn(registrationBean);
        when(session.getAttribute("errors")).thenReturn(errors);

        registrationServlet.init(servletConfig);
        registrationServlet.doGet(request, response);

        verify(session, atLeast(1)).getAttribute("bean");
        verify(session, atLeast(1)).getAttribute("errors");
        verify(dispatcher, atLeast(1)).forward(request, response);
    }

    @Test
    public void testServletDoPostOK() throws Exception {
        when(captchaStorageStrategy.getCurrentCaptchaID(request, response)).thenReturn(0);
        when(captchaService.getCurrentCaptchaValue(0)).thenReturn("hj455");
        when(validator.validateRegistrationBean(registrationBean, "hj455")).thenReturn(errors);

        registrationServlet.init(servletConfig);
        registrationServlet.doPost(request, response);
        verify(response, atLeast(1)).sendRedirect(request.getContextPath() + "/index.jsp");
    }

    @Test
    public void testServletDoPostErrorCaptchaValue() throws Exception {
        when(captchaStorageStrategy.getCurrentCaptchaID(request, response)).thenReturn(0);
        when(captchaService.getCurrentCaptchaValue(0)).thenReturn("455pp");
        when(validator.validateRegistrationBean(registrationBean, "455pp")).thenReturn(errors);
        when(errors.isEmpty()).thenReturn(false);
        when(errors.size()).thenReturn(1);
        registrationServlet.init(servletConfig);
        registrationServlet.doPost(request, response);

        verify(response, atMost(1)).sendRedirect(request.getContextPath() + "/register");
    }
}
