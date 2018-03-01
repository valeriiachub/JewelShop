package com.epam.preprod.listener;

import com.epam.preprod.cart.ShoppingCart;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.HashMap;

@WebListener
public class SessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        ShoppingCart shoppingCart = new ShoppingCart(new HashMap<>());
        se.getSession().setAttribute("shoppingCart", shoppingCart);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {

    }
}
