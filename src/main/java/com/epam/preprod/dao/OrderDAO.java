package com.epam.preprod.dao;


import com.epam.preprod.entity.order.DeliveryMethod;
import com.epam.preprod.entity.order.Order;
import com.epam.preprod.entity.order.PaymentMethod;

import java.sql.Connection;
import java.util.List;

public interface OrderDAO {
    int makeOrder(Order Order, Connection connection);

    List<DeliveryMethod> getAllDeliveryMethods(Connection connection);

    List<PaymentMethod> getAllPaymentMethods(Connection connection);
}
