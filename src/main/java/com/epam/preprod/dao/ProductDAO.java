package com.epam.preprod.dao;

import com.epam.preprod.entity.Category;
import com.epam.preprod.entity.Manufacturer;
import com.epam.preprod.entity.Product;
import com.epam.preprod.products_filter.FilterParameters;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface ProductDAO {
    Optional<Product> getProductById(int id, Connection connection);

    List<Category> getAllCategories(Connection connection);

    List<Manufacturer> getAllManufacturers(Connection connection);

    List<Product> getFilteredProducts(Connection connection, FilterParameters filterParameters);

    int getNumberOfPages(Connection connection, FilterParameters filterParameters);
}

