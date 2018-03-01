package com.epam.preprod.servlet;

import com.epam.preprod.captcha.CaptchaCreator;
import com.epam.preprod.entity.Captcha;
import com.epam.preprod.entity.User;
import com.epam.preprod.service.CaptchaService;
import com.epam.preprod.service.PhotoService;
import com.epam.preprod.service.UserService;
import com.epam.preprod.service.impl.CaptchaServiceImpl;
import com.epam.preprod.service.impl.PhotoServiceImpl;
import com.epam.preprod.service.impl.UserServiceImpl;
import com.epam.preprod.strategy.captcha.CaptchaStorageStrategy;
import com.epam.preprod.util.RegistrationBean;
import com.epam.preprod.validator.Validator;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;


@WebServlet("/register")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 5 * 5
)
public class RegistrationServlet extends HttpServlet {
    private static final String EMAIL_ALREADY_EXIST_MESSAGE = "This email already exist. Please, try another";
    private Validator validator;
    private CaptchaService captchaService;
    private UserService userService;
    private PhotoService photoService;
    private CaptchaStorageStrategy captchaStorageStrategy;

    @Override
    public void init(ServletConfig servletConfig) {

        ServletContext servletContext = servletConfig.getServletContext();
        captchaStorageStrategy = (CaptchaStorageStrategy) servletContext.getAttribute("captchaStorageStrategy");
        captchaService = (CaptchaServiceImpl) servletContext.getAttribute("captchaService");
        userService = (UserServiceImpl) servletContext.getAttribute("userService");
        validator = (Validator) servletContext.getAttribute("validator");
        photoService = new PhotoServiceImpl();

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Captcha captcha = new CaptchaCreator(captchaService).create();
        captchaService.addCaptcha(captcha);
        captchaStorageStrategy.setCurrentCaptchaID(request, response, captcha);

        request.setAttribute("bean", session.getAttribute("bean"));
        request.setAttribute("errors", session.getAttribute("errors"));

        request.getRequestDispatcher("registrationPage.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        User user;
        String correctCaptchaCode = captchaService.getCurrentCaptchaValue(captchaStorageStrategy.getCurrentCaptchaID(request, response));

        RegistrationBean bean = createBean(request);
        Map<String, String> errors = validator.validateRegistrationBean(bean, correctCaptchaCode);

        if (errors.isEmpty()) {
            if (isEmailExist(bean.getEmail(), userService)) {
                errors.put("existedEmail", EMAIL_ALREADY_EXIST_MESSAGE);
                goBack(request, response, errors, bean);
            } else {
                user = transformBeanToUser(bean);
                userService.addUser(user);
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                response.sendRedirect(request.getContextPath() + "/index.jsp");
            }
        } else {
            goBack(request, response, errors, bean);
        }
    }


    private RegistrationBean createBean(HttpServletRequest httpServletRequest) throws IOException, ServletException {
        RegistrationBean bean = new RegistrationBean();

        bean.setFirstName(httpServletRequest.getParameter("firstName"));
        bean.setLastName(httpServletRequest.getParameter("lastName"));
        bean.setEmail(httpServletRequest.getParameter("email"));
        bean.setPassword(httpServletRequest.getParameter("password"));
        bean.setCaptchaValue(httpServletRequest.getParameter("captchaValue"));
        boolean sendNews = httpServletRequest.getParameter("sendNews") != null;
        bean.setSendNews(sendNews);

        String imageName = getImageName(httpServletRequest);
        bean.setImageName(imageName);

        return bean;
    }

    private String getImageName(HttpServletRequest request) throws IOException, ServletException {
        String imageName = "";
        Part filePart = request.getPart("photo");
        if (filePart != null) {
            if (filePart.getSize() > 0) {
                String path = request.getServletContext().getInitParameter("imgPath");
                InputStream fileContent = filePart.getInputStream();
                imageName = photoService.saveImage(path, fileContent);
            }
        } else {
            imageName = "default.png";
        }
        return imageName;
    }

    private User transformBeanToUser(RegistrationBean bean) {
        User user = new User();
        user.setFirstName(bean.getFirstName());
        user.setLastName(bean.getLastName());
        user.setEmail(bean.getEmail());
        user.setPassword(bean.getPassword());
        user.setSendNews(bean.isSendNews());
        user.setImageName(bean.getImageName());

        return user;
    }

    private void goBack(HttpServletRequest request, HttpServletResponse
            response, Map<String, String> errors, RegistrationBean bean) throws IOException {
        HttpSession session = request.getSession();
        bean.setPassword("");
        session.setAttribute("bean", bean);
        session.setAttribute("errors", errors);
        response.sendRedirect(request.getContextPath() + "/register");
    }

    private boolean isEmailExist(String email, UserService userService) {
        return userService.getUserByEmail(email).isPresent();
    }
}
