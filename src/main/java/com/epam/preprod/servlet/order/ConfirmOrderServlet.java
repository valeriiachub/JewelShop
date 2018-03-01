package com.epam.preprod.servlet.order;

import com.epam.preprod.cart.ShoppingCart;
import com.epam.preprod.entity.order.Order;
import com.epam.preprod.service.OrderService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/confirmOrder")
public class ConfirmOrderServlet extends HttpServlet {
    private OrderService orderService;

    @Override
    public void init(ServletConfig servletConfig) {
        ServletContext servletContext = servletConfig.getServletContext();
        orderService = (OrderService) servletContext.getAttribute("orderService");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession();
        ShoppingCart shoppingCart = (ShoppingCart) httpSession.getAttribute("shoppingCart");
        Order order = (Order) httpSession.getAttribute("order");

        int deliveryMethodId = Integer.parseInt(request.getParameter("deliveryMethod"));
        int paymentMethodId = Integer.parseInt(request.getParameter("paymentMethod"));
        String paymentRequisites = request.getParameter("deliveryInfo");

        order.setDeliveryMethodId(deliveryMethodId);
        order.setPaymentMethodId(paymentMethodId);
        order.setDeliveryInfo(paymentRequisites);

        BigDecimal totalOrderPrice = orderService.calculateTotalPrice(order);
        int totalQuantity = orderService.calculateTotalQuantity(order);
        orderService.makeOrder(order);

        setAttributesInReq(request, order, totalOrderPrice, totalQuantity);
        shoppingCart.clear();
        request.getRequestDispatcher("confirmedOrder.jsp").forward(request, response);
    }

    private void setAttributesInReq(HttpServletRequest request, Order order, BigDecimal totalOrderPrice, int totalQuantity) {
        request.setAttribute("order", order);
        request.setAttribute("orderedProducts", order.getOrderedProducts());
        request.setAttribute("totalOrderPrice", totalOrderPrice);
        request.setAttribute("totalQuantity", totalQuantity);
    }
}
