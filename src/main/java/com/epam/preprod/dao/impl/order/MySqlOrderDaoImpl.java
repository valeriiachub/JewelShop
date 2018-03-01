package com.epam.preprod.dao.impl.order;

import com.epam.preprod.dao.OrderDAO;
import com.epam.preprod.entity.enums.Status;
import com.epam.preprod.entity.order.DeliveryMethod;
import com.epam.preprod.entity.order.Order;
import com.epam.preprod.entity.order.OrderedProduct;
import com.epam.preprod.entity.order.PaymentMethod;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class MySqlOrderDaoImpl implements OrderDAO {
    private static final String SQL_GET_ALL_DELIVERY_METHODS = "SELECT id, name FROM delivery_method";
    private static final String SQL_GET_ALL_PAYMENT_METHODS = "SELECT id, name FROM payment_method";
    private static final String SQL_ADD_ORDER = "INSERT INTO webshop.order (status_id, user_id, details, payment_method_id, " +
            "delivery_method_id, delivery_info) VALUES (?,?,?,?,?,?)";
    private static final String SQL_ADD_ORDER_PRODUCT = "INSERT INTO order_product (order_id, product_id, quantity, price) VALUES (?,?,?,?)";
    private static final Logger logger = Logger.getLogger(MySqlOrderDaoImpl.class);

    @Override
    public int makeOrder(Order order, Connection connection) {
        int id = 0;
        try (PreparedStatement statement = connection.prepareStatement(SQL_ADD_ORDER, PreparedStatement.RETURN_GENERATED_KEYS)) {
            fillPrepareStatementForAddOrder(order, statement);
            statement.executeUpdate();
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    id = resultSet.getInt(1);
                    order.setId(id);
                }
                createOrderProduct(connection, order.getOrderedProducts(), order.getId());
            }
        } catch (SQLException e) {
            logger.error("Can`t add order", e);
        }
        return id;
    }


    @Override
    public List<DeliveryMethod> getAllDeliveryMethods(Connection connection) {
        List<DeliveryMethod> deliveryMethods = new LinkedList<>();
        try (PreparedStatement statement = connection.prepareStatement(SQL_GET_ALL_DELIVERY_METHODS);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                DeliveryMethod deliveryMethod = extractDeliveryMethodFromResultSet(resultSet);
                deliveryMethods.add(deliveryMethod);
            }
        } catch (SQLException e) {
            logger.error("Can`t get delivery methods", e);
        }
        return deliveryMethods;
    }

    @Override
    public List<PaymentMethod> getAllPaymentMethods(Connection connection) {
        List<PaymentMethod> paymentMethods = new LinkedList<>();
        try (PreparedStatement statement = connection.prepareStatement(SQL_GET_ALL_PAYMENT_METHODS);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                PaymentMethod paymentMethod = extractPaymentMethodFromResultSet(resultSet);
                paymentMethods.add(paymentMethod);
            }
        } catch (SQLException e) {
            logger.error("Can`t get payment methods", e);
        }
        return paymentMethods;

    }

    private DeliveryMethod extractDeliveryMethodFromResultSet(ResultSet resultSet) {
        DeliveryMethod deliveryMethod = null;
        try {
            deliveryMethod = new DeliveryMethod();
            deliveryMethod.setId(resultSet.getInt("id"));
            deliveryMethod.setName(resultSet.getString("name"));
        } catch (SQLException e) {
            logger.error("Can`t extract delivery method from result set", e);
        }
        return deliveryMethod;
    }

    private PaymentMethod extractPaymentMethodFromResultSet(ResultSet resultSet) {
        PaymentMethod paymentMethod = null;
        try {
            paymentMethod = new PaymentMethod();
            paymentMethod.setId(resultSet.getInt("id"));
            paymentMethod.setName(resultSet.getString("name"));
        } catch (SQLException e) {
            logger.error("Can`t extract payment method from result set", e);
        }
        return paymentMethod;
    }

    private void fillPrepareStatementForAddOrder(Order order, PreparedStatement statement) {
        int k = 0;
        Status status = order.getOrderStatus();
        int status_id = status.getNumericValue();
        try {
            statement.setInt(++k, status_id);
            statement.setInt(++k, order.getUser().getId());
            statement.setString(++k, order.getOrderDetails());
            statement.setInt(++k, order.getPaymentMethodId());
            statement.setInt(++k, order.getDeliveryMethodId());
            statement.setString(++k, order.getDeliveryInfo());
        } catch (SQLException e) {
            logger.error("Can`t fill prepared statement", e);
        }
    }

    private void createOrderProduct(Connection connection, List<OrderedProduct> orderedProducts, int orderId) {
        try (PreparedStatement statement = connection.prepareStatement(SQL_ADD_ORDER_PRODUCT)) {
            for (OrderedProduct orderedProduct : orderedProducts) {
                int i = 0;
                statement.setInt(++i, orderId);
                statement.setInt(++i, orderedProduct.getProduct().getId());
                statement.setInt(++i, orderedProduct.getQuantity());
                statement.setBigDecimal(++i, orderedProduct.getPrice());
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (SQLException e) {
            logger.error("Can`t create order_product", e);
        }
    }
}
