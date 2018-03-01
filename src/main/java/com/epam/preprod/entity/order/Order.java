package com.epam.preprod.entity.order;


import com.epam.preprod.entity.User;
import com.epam.preprod.entity.enums.Status;

import java.time.LocalDateTime;
import java.util.List;

public class Order {
    private int id;
    private Status orderStatus;
    private String orderDetails;
    private LocalDateTime orderDate;
    private User user;
    private int deliveryMethodId;
    private int paymentMethodId;
    private String deliveryInfo;
    private List<OrderedProduct> orderedProducts;

    public Order(Status orderStatus, String orderDetails, LocalDateTime orderDate, User user, List<OrderedProduct> orderedProducts) {
        this.orderStatus = orderStatus;
        this.orderDetails = orderDetails;
        this.orderDate = orderDate;
        this.user = user;
        this.orderedProducts = orderedProducts;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Status getOrderStatus() {
        return orderStatus;
    }

    public String getOrderDetails() {
        return orderDetails;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<OrderedProduct> getOrderedProducts() {
        return orderedProducts;
    }

    public int getDeliveryMethodId() {
        return deliveryMethodId;
    }

    public void setDeliveryMethodId(int deliveryMethodId) {
        this.deliveryMethodId = deliveryMethodId;
    }

    public int getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(int paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public String getDeliveryInfo() {
        return deliveryInfo;
    }

    public void setDeliveryInfo(String deliveryInfo) {
        this.deliveryInfo = deliveryInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (id != order.id) return false;
        if (deliveryMethodId != order.deliveryMethodId) return false;
        if (paymentMethodId != order.paymentMethodId) return false;
        if (orderStatus != order.orderStatus) return false;
        if (!orderDetails.equals(order.orderDetails)) return false;
        if (!orderDate.equals(order.orderDate)) return false;
        if (!user.equals(order.user)) return false;
        if (!deliveryInfo.equals(order.deliveryInfo)) return false;
        return orderedProducts.equals(order.orderedProducts);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + orderStatus.hashCode();
        result = 31 * result + orderDetails.hashCode();
        result = 31 * result + orderDate.hashCode();
        result = 31 * result + user.hashCode();
        result = 31 * result + deliveryMethodId;
        result = 31 * result + paymentMethodId;
        result = 31 * result + deliveryInfo.hashCode();
        result = 31 * result + orderedProducts.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderStatus=" + orderStatus +
                ", orderDetails='" + orderDetails + '\'' +
                ", orderDate=" + orderDate +
                ", user=" + user +
                ", deliveryMethodId=" + deliveryMethodId +
                ", paymentMethodId=" + paymentMethodId +
                ", deliveryInfo='" + deliveryInfo + '\'' +
                ", orderedProducts=" + orderedProducts +
                '}';
    }
}
