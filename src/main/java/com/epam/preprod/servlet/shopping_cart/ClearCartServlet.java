package com.epam.preprod.servlet.shopping_cart;

import com.epam.preprod.cart.ShoppingCart;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/clearCart")
public class ClearCartServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ShoppingCart cart = (ShoppingCart) request.getSession().getAttribute("shoppingCart");
        cart.clear();
        response.sendRedirect("/cart");
    }
}
