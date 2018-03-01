package com.epam.preprod.service.impl;

import com.epam.preprod.cart.ShoppingCart;
import com.epam.preprod.dao.OrderDAO;
import com.epam.preprod.dao.impl.order.MySqlOrderDaoImpl;
import com.epam.preprod.entity.Product;
import com.epam.preprod.entity.User;
import com.epam.preprod.entity.enums.Status;
import com.epam.preprod.entity.order.DeliveryMethod;
import com.epam.preprod.entity.order.Order;
import com.epam.preprod.entity.order.OrderedProduct;
import com.epam.preprod.entity.order.PaymentMethod;
import com.epam.preprod.manager.TransactionManager;
import com.epam.preprod.service.OrderService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class OrderServiceImpl implements OrderService {
    private OrderDAO orderDAO;
    private TransactionManager transactionManager;

    public OrderServiceImpl(MySqlOrderDaoImpl mySqlOrderDao, TransactionManager transactionManager) {
        this.orderDAO = mySqlOrderDao;
        this.transactionManager = transactionManager;
    }

    @Override
    public int makeOrder(Order order) {
        return transactionManager.doInTransaction((connection) -> orderDAO.makeOrder(order, connection));
    }

    @Override
    public List<DeliveryMethod> getAllDeliveryMethods() {
        return transactionManager.doInTransaction((connection) -> orderDAO.getAllDeliveryMethods(connection));
    }

    @Override
    public List<PaymentMethod> getAllPaymentMethods() {
        return transactionManager.doInTransaction((connection) -> orderDAO.getAllPaymentMethods(connection));
    }

    @Override
    public BigDecimal calculateTotalPrice(Order order) {
        BigDecimal totalOrderPrice = BigDecimal.ZERO;
        for (OrderedProduct orderedProduct : order.getOrderedProducts()) {
            totalOrderPrice = totalOrderPrice.add(orderedProduct.getPrice().multiply(BigDecimal.valueOf(orderedProduct.getQuantity())));
        }
        return totalOrderPrice;
    }

    @Override
    public int calculateTotalQuantity(Order order) {
        int totalQuantity = 0;
        for (OrderedProduct orderedProduct : order.getOrderedProducts()) {
            totalQuantity += orderedProduct.getQuantity();
        }
        return totalQuantity;
    }

    @Override
    public Order transformShoppingCartToOrder(ShoppingCart shoppingCart, User user) {
        List<OrderedProduct> orderedProducts = getOrderedProductsFromCart(shoppingCart);
        LocalDateTime localDateTime = LocalDateTime.now();
        return new Order(Status.ACCEPTED, "Order was accepted", localDateTime, user, orderedProducts);
    }

    private List<OrderedProduct> getOrderedProductsFromCart(ShoppingCart shoppingCart) {
        List<OrderedProduct> orderedProducts = new LinkedList<>();
        for (Map.Entry<Product, Integer> cartRow : shoppingCart.getShoppingCart().entrySet()) {
            Product product = cartRow.getKey();
            orderedProducts.add(new OrderedProduct(product, cartRow.getValue(), product.getPrice()));
        }
        return orderedProducts;
    }
}
