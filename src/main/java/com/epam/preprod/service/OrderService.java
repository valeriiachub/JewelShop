package com.epam.preprod.service;

import com.epam.preprod.cart.ShoppingCart;
import com.epam.preprod.entity.User;
import com.epam.preprod.entity.order.DeliveryMethod;
import com.epam.preprod.entity.order.Order;
import com.epam.preprod.entity.order.PaymentMethod;

import java.math.BigDecimal;
import java.util.List;

public interface OrderService {
    int makeOrder(Order order);

    List<DeliveryMethod> getAllDeliveryMethods();

    List<PaymentMethod> getAllPaymentMethods();

    BigDecimal calculateTotalPrice(Order order);

    int calculateTotalQuantity(Order order);

    Order transformShoppingCartToOrder(ShoppingCart shoppingCart, User user);
}
