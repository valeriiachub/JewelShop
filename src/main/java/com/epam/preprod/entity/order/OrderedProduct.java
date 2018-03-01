package com.epam.preprod.entity.order;

import com.epam.preprod.entity.Product;

import java.math.BigDecimal;
import java.util.Objects;

public final class OrderedProduct {
    private Product product;
    private int quantity;
    private BigDecimal price;

    public OrderedProduct(Product product, int quantity, BigDecimal price) {
        this.product = product;
        this.quantity = quantity;
        this.price = price;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderedProduct that = (OrderedProduct) o;
        return quantity == that.quantity &&
                Objects.equals(product, that.product) &&
                Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, quantity, price);
    }

    @Override
    public String toString() {
        return "OrderedProduct{" +
                "product=" + product +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
