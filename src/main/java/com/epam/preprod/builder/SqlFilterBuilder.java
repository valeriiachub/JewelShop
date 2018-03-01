package com.epam.preprod.builder;

import com.epam.preprod.products_filter.FilterParameters;

import java.math.BigDecimal;

public class SqlFilterBuilder {

    public String buildSqlFilterForProduct(FilterParameters parameters) {
        StringBuilder sql = new StringBuilder("SELECT * FROM product WHERE id >= 0");
        appendConditions(parameters, sql);
        sql.append(" LIMIT ?,?");
        return sql.toString();
    }

    public String buildSqlForCount(FilterParameters parameters) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM product  WHERE id >= 0");
        appendConditions(parameters, sql);
        return sql.toString();
    }

    private void appendConditions(FilterParameters parameters, StringBuilder sql) {
        if (!parameters.getProductName().equals("")) {
            sql.append(" AND name LIKE '%").append(parameters.getProductName()).append("%'");
        }
        if (parameters.getCategory() != 0) {
            sql.append(" AND category_id = ").append(parameters.getCategory());
        }
        if (parameters.getManufacturer() != 0) {
            sql.append(" AND manufacturer_id = ").append(parameters.getManufacturer());
        }
        if (!(parameters.getMaxPrice().compareTo(BigDecimal.valueOf(0)) == 0)) {
            sql.append(" AND price <= ").append(parameters.getMaxPrice());
        }
        sql.append(" AND price >= ").append(parameters.getMinPrice());
        if (parameters.getOrderBy().equals("Descending")) {
            sql.append(" ORDER BY price DESC");
        }
    }
}
