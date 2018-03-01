package com.epam.preprod.servlet.shopping_cart;


import com.epam.preprod.cart.ShoppingCart;
import org.apache.log4j.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/deleteFromCart")
public class DeleteFromCartServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(DeleteFromCartServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ShoppingCart cart = getCartFromSession(request);
        int productId = Integer.parseInt(request.getParameter("productId"));

        cart.deleteProductFromCart(productId);
        logger.debug("cart --> " + cart.getShoppingCart().entrySet().toString());

        response.sendRedirect("/cart");
    }

    private ShoppingCart getCartFromSession(HttpServletRequest request) {
        return (ShoppingCart) request.getSession().getAttribute("shoppingCart");
    }
}
