package com.epam.preprod.service;

import com.epam.preprod.entity.Category;
import com.epam.preprod.entity.Manufacturer;
import com.epam.preprod.entity.Product;
import com.epam.preprod.products_filter.FilterParameters;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<Category> getAllCategories();

    List<Manufacturer> getAllManufacturers();

    List<Product> getFilteredProducts(FilterParameters filterParameters);

    int getNumberOfPages(FilterParameters filterParameters);

    Optional<Product> getProductById(int id);
}
