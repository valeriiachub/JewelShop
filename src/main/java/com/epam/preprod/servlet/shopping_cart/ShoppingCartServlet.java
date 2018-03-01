package com.epam.preprod.servlet.shopping_cart;

import com.epam.preprod.cart.ShoppingCart;
import com.epam.preprod.entity.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

@WebServlet("/cart")
public class ShoppingCartServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ShoppingCart shoppingCart = (ShoppingCart) request.getSession().getAttribute("shoppingCart");

        BigDecimal totalPrice = shoppingCart.calculateTotalPrice();
        Map<Product, Integer> shoppingCartMap = shoppingCart.getShoppingCart();

        request.setAttribute("shoppingCart", shoppingCartMap);
        request.setAttribute("totalPrice", totalPrice);

        request.getRequestDispatcher("cart.jsp").forward(request, response);
    }
}
