package com.epam.preprod.cart;

import com.epam.preprod.entity.Product;

import java.math.BigDecimal;
import java.util.Map;


public class ShoppingCart {
    private Map<Product, Integer> shoppingCart;

    public ShoppingCart(Map<Product, Integer> shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public Map<Product, Integer> getShoppingCart() {
        return shoppingCart;
    }

    public void addProductToCart(Product product, Integer count) {
        if (shoppingCart.containsKey(product)) {
            int quantity = shoppingCart.get(product);
            shoppingCart.put(product, ++quantity);
        } else {
            shoppingCart.put(product, count);
        }
    }

    public void deleteProductFromCart(int productId) {
        for (Product product : shoppingCart.keySet()) {
            if (productId == product.getId()) {
                shoppingCart.remove(product);
            }
        }
    }

    public void changeProductQuantity(int productId, int newQuantity) {
        for (Product product : shoppingCart.keySet()) {
            if (product.getId() == productId) {
                shoppingCart.put(product, newQuantity);
            }
        }
    }

    public int getNumberOfProductsInShoppingCart() {
        int numberOfProducts = 0;
        for (Integer productQuantity : shoppingCart.values()) {
            numberOfProducts += productQuantity;
        }
        return numberOfProducts;
    }

    public BigDecimal calculateTotalPrice() {
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (Product product : shoppingCart.keySet()) {
            totalAmount = totalAmount.add(product.getPrice().multiply(BigDecimal.valueOf(shoppingCart.get(product))));
        }
        return totalAmount;
    }

    public void clear() {
        shoppingCart.clear();
    }
}
