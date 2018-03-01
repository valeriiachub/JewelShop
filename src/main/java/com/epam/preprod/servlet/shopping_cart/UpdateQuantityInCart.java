package com.epam.preprod.servlet.shopping_cart;

import com.epam.preprod.cart.ShoppingCart;
import org.apache.log4j.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/updateCart")
public class UpdateQuantityInCart extends HttpServlet {

    private static final Logger logger = Logger.getLogger(UpdateQuantityInCart.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ShoppingCart cart = getCartFromSession(request);
        int productId = Integer.parseInt(request.getParameter("productId"));
        logger.debug(productId);
        int newQuantity = Integer.parseInt(request.getParameter("quantity"));
        cart.changeProductQuantity(productId, newQuantity);
        logger.debug("cart --> " + cart.getShoppingCart().entrySet().toString());

        response.sendRedirect("/cart");
    }

    private ShoppingCart getCartFromSession(HttpServletRequest request) {
        return (ShoppingCart) request.getSession().getAttribute("shoppingCart");
    }
}

