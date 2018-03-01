package com.epam.preprod.products_filter;

import java.math.BigDecimal;
import java.util.Objects;

public class FilterParameters {
    private String productName;
    private int manufacturer;
    private int category;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private String orderBy;
    private int productsOnPage;
    private int pageNumber;

    public FilterParameters(String productName, int manufacturer, int category,
                            BigDecimal minPrice, BigDecimal maxPrice, String orderBy, int productsOnPage, int pageNumber) {
        this.productName = productName;
        this.manufacturer = manufacturer;
        this.category = category;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.orderBy = orderBy;
        this.productsOnPage = productsOnPage;
        this.pageNumber = pageNumber;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(int manufacturer) {
        this.manufacturer = manufacturer;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(BigDecimal minPrice) {
        this.minPrice = minPrice;
    }

    public BigDecimal getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(BigDecimal maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getProductsOnPage() {
        return productsOnPage;
    }

    public void setProductsOnPage(int productsOnPage) {
        this.productsOnPage = productsOnPage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FilterParameters that = (FilterParameters) o;
        return manufacturer == that.manufacturer &&
                category == that.category &&
                productsOnPage == that.productsOnPage &&
                pageNumber == that.pageNumber &&
                Objects.equals(productName, that.productName) &&
                Objects.equals(minPrice, that.minPrice) &&
                Objects.equals(maxPrice, that.maxPrice) &&
                Objects.equals(orderBy, that.orderBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productName, manufacturer, category, minPrice, maxPrice, orderBy,
                productsOnPage, pageNumber);
    }

    @Override
    public String toString() {
        return "FilterParameters{" +
                "productName='" + productName + '\'' +
                ", manufacturer=" + manufacturer +
                ", category=" + category +
                ", minPrice=" + minPrice +
                ", maxPrice=" + maxPrice +
                ", orderBy='" + orderBy + '\'' +
                ", productsOnPage=" + productsOnPage +
                ", pageNumber=" + pageNumber +
                '}';
    }
}
