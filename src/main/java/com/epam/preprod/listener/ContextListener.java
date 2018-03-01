package com.epam.preprod.listener;

import com.epam.preprod.Context;
import com.epam.preprod.captcha.CaptchaRemover;
import com.epam.preprod.dao.impl.captcha.CaptchaDAOImpl;
import com.epam.preprod.dao.impl.order.MySqlOrderDaoImpl;
import com.epam.preprod.dao.impl.product.MySqlProductDaoImpl;
import com.epam.preprod.dao.impl.user.MySqlUserDaoImpl;
import com.epam.preprod.manager.DataBaseManager;
import com.epam.preprod.manager.TransactionManager;
import com.epam.preprod.service.CaptchaService;
import com.epam.preprod.service.OrderService;
import com.epam.preprod.service.ProductService;
import com.epam.preprod.service.UserService;
import com.epam.preprod.service.impl.CaptchaServiceImpl;
import com.epam.preprod.service.impl.OrderServiceImpl;
import com.epam.preprod.service.impl.ProductServiceImpl;
import com.epam.preprod.service.impl.UserServiceImpl;
import com.epam.preprod.strategy.captcha.CaptchaStorageStrategy;
import com.epam.preprod.validator.Validator;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@WebListener
public class ContextListener implements ServletContextListener {
    private static final Logger logger = Logger.getLogger(ContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        Context context = new Context();
        CaptchaService captchaService = new CaptchaServiceImpl(new CaptchaDAOImpl(new ConcurrentHashMap<>()));
        long captchaTimeout = Long.parseLong(servletContext.getInitParameter("captchaTimeout"));

        initLog4J();
        startCaptchaRemoverThread(captchaService, captchaTimeout);
        setAttributesInContext(servletContext, context, captchaService);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

    private void initLog4J() {
        log("Log4J initialization started");
        BasicConfigurator.configure();
        logger.info("Log4j has been initialized");
        log("Log4J initialization finished");
    }

    private void log(String message) {
        System.out.println("[ContextListener] " + message);
    }

    private void setAttributesInContext(ServletContext servletContext, Context context, CaptchaService captchaService) {

        Validator validator = new Validator();
        DataBaseManager dataBaseManager = new DataBaseManager();
        String imgPath = servletContext.getInitParameter("imgPath");
        TransactionManager transactionManager = new TransactionManager(dataBaseManager);
        UserService userService = new UserServiceImpl(new MySqlUserDaoImpl(), transactionManager);
        OrderService orderService = new OrderServiceImpl(new MySqlOrderDaoImpl(), transactionManager);
        ProductService productService = new ProductServiceImpl(new MySqlProductDaoImpl(), transactionManager);
        CaptchaStorageStrategy captchaStorageStrategy = initCaptchaStorageStrategy(context, servletContext);

        servletContext.setAttribute("imgPath", imgPath);
        servletContext.setAttribute("validator", validator);
        servletContext.setAttribute("userService", userService);
        servletContext.setAttribute("orderService", orderService);
        servletContext.setAttribute("productService", productService);
        servletContext.setAttribute("captchaService", captchaService);
        servletContext.setAttribute("dataBaseManager", dataBaseManager);
        servletContext.setAttribute("captchaStorageStrategy", captchaStorageStrategy);
    }

    private CaptchaStorageStrategy initCaptchaStorageStrategy(Context context, ServletContext servletContext) {
        Map<String, CaptchaStorageStrategy> captchaStorageStrategyMap = context.initializeCaptchaStrategies();
        String storageStrategy = servletContext.getInitParameter("captchaStorageStrategy");

        return captchaStorageStrategyMap.get(storageStrategy);
    }

    private void startCaptchaRemoverThread(CaptchaService captchaService, long captchaTimeout) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new CaptchaRemover(captchaService.getCaptchaMap(), captchaTimeout));
    }

}
