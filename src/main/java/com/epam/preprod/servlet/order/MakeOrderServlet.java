package com.epam.preprod.servlet.order;

import com.epam.preprod.cart.ShoppingCart;
import com.epam.preprod.entity.User;
import com.epam.preprod.entity.order.DeliveryMethod;
import com.epam.preprod.entity.order.Order;
import com.epam.preprod.entity.order.PaymentMethod;
import com.epam.preprod.service.OrderService;
import org.apache.log4j.Logger;

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
import java.util.List;
import java.util.Objects;

@WebServlet("/makeOrder")
public class MakeOrderServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(MakeOrderServlet.class);
    private OrderService orderService;

    @Override
    public void init(ServletConfig servletConfig) {
        ServletContext servletContext = servletConfig.getServletContext();
        orderService = (OrderService) servletContext.getAttribute("orderService");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (Objects.isNull(user)) {
            goToLoginPage(request, response);
        } else {
            ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("shoppingCart");
            List<DeliveryMethod> deliveryMethods = orderService.getAllDeliveryMethods();
            List<PaymentMethod> paymentMethods = orderService.getAllPaymentMethods();
            BigDecimal totalPrice = shoppingCart.calculateTotalPrice();
            int totalNumberOfProducts = shoppingCart.getNumberOfProductsInShoppingCart();
            Order order = orderService.transformShoppingCartToOrder(shoppingCart, user);

            setAttributesInRequest(request, order, totalPrice, totalNumberOfProducts, deliveryMethods, paymentMethods);
            session.setAttribute("order", order);
            request.getRequestDispatcher("order.jsp").forward(request, response);
        }
    }

    private void setAttributesInRequest(HttpServletRequest request, Order order, BigDecimal totalPrice, int totalNumberOfProducts,
                                        List<DeliveryMethod> deliveryMethods, List<PaymentMethod> paymentMethods) {
        request.setAttribute("totalAmount", totalPrice);
        request.setAttribute("numberOfProducts", totalNumberOfProducts);
        request.setAttribute("paymentMethods", paymentMethods);
        request.setAttribute("deliveryMethods", deliveryMethods);
        request.setAttribute("orderedProducts", order.getOrderedProducts());
    }

    private void goToLoginPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String previousUrl = request.getParameter("previousUrl");
        logger.debug(previousUrl);
        request.getSession().setAttribute("previousUrl", previousUrl);
        response.sendRedirect("/auth");
    }
}
