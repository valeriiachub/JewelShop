package com.epam.preprod.filter;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import com.epam.preprod.entity.User;
import com.epam.preprod.entity.enums.Role;
import com.epam.preprod.parser.DOMParser;

public class SecurityFilter implements Filter {

    private Map<String, List<Role>> roleListMap;
    private static final Logger logger = Logger.getLogger(SecurityFilter.class);

    @Override
    public void init(FilterConfig filterConfig) {
        File currentDir = new File(System.getProperty("user.dir"));
        String filePath = currentDir + filterConfig.getInitParameter("filePath");
        DOMParser domParser = new DOMParser(filePath);
        try {
            roleListMap = domParser.parseFile();
        } catch (ParserConfigurationException | IOException | SAXException e) {
            logger.error("Can`t parse file with constraints", e);
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.debug("SecurityFilter filter");

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();

        String reqUrl = request.getRequestURI();
        logger.debug("Requested url --> " + reqUrl);

        User user = (User) session.getAttribute("user");

        if (roleListMap.entrySet().stream().anyMatch(url -> reqUrl.matches(url.getKey()))) {
            if (Objects.isNull(user)) {
                response.sendRedirect(request.getContextPath() + "/auth?URL=" + reqUrl);
            } else {
                if (acceptAccess(reqUrl, user.getRole())) {
                    filterChain.doFilter(request, response);
                } else {
                    response.sendError(403);
                }
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }

    private boolean acceptAccess(String reqUrl, Role role) {
        List<Role> rolesForUrl = new LinkedList<>();
        for (Map.Entry<String, List<Role>> entry : roleListMap.entrySet()) {
            if (reqUrl.matches(entry.getKey())) {
                rolesForUrl = entry.getValue();
            }
        }

        return rolesForUrl.stream().anyMatch(r -> r.equals(role));
    }
}
