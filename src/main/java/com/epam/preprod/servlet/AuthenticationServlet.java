package com.epam.preprod.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.epam.preprod.entity.User;
import com.epam.preprod.service.impl.UserServiceImpl;

@WebServlet("/auth")
public class AuthenticationServlet extends HttpServlet {

    private static final String EMAIL_DOESNT_EXIST_MESSAGE = "This email doesn't exist.";
    private static final String INCORRECT_PASSWORD = "Incorrect password";
    private UserServiceImpl userService;
    private String previousUrl;

    @Override
    public void init(ServletConfig servletConfig) {
        ServletContext servletContext = servletConfig.getServletContext();
        userService = (UserServiceImpl) servletContext.getAttribute("userService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("errors", request.getSession().getAttribute("errors"));
        previousUrl = request.getParameter("URL");
        User user = (User) request.getSession().getAttribute("user");
        if (Objects.nonNull(user)) {
            String imgName = user.getImageName();
            String imgPath = request.getServletContext().getInitParameter("imgPath");
            String imgUrl = imgPath + "\\" + imgName;
            request.setAttribute("firstName", user.getFirstName());
            request.setAttribute("imgUrl", imgUrl);
        }
        request.getRequestDispatcher("loginPage.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, String> errors = new HashMap<>();
        HttpSession session = request.getSession();
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        Optional<User> user = userService.getUserByEmail(email);
        if (user.isPresent()) {
            if (!checkCorrectPassword(user.get(), password)) {
                errors.put("password", INCORRECT_PASSWORD);
                goBack(request, response, errors);
            } else {
                session.setAttribute("user", user.get());

                if (previousUrl != null) {
                    response.sendRedirect(previousUrl);
                } else {
                    response.sendRedirect("/home");
                }
            }
        } else {
            errors.put("existedEmail", EMAIL_DOESNT_EXIST_MESSAGE);
            goBack(request, response, errors);
        }
    }

    private boolean checkCorrectPassword(User user, String password) {
        return user.getPassword().equals(password);
    }

    private void goBack(HttpServletRequest request, HttpServletResponse response, Map<String, String> errors)
            throws IOException {
        HttpSession session = request.getSession();
        session.setAttribute("errors", errors);
        response.sendRedirect(request.getContextPath() + "loginPage.jsp");
    }
}
