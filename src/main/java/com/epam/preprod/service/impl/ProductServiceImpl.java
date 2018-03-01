package com.epam.preprod.service.impl;

import com.epam.preprod.dao.ProductDAO;
import com.epam.preprod.dao.impl.product.MySqlProductDaoImpl;
import com.epam.preprod.entity.Category;
import com.epam.preprod.entity.Manufacturer;
import com.epam.preprod.entity.Product;
import com.epam.preprod.manager.TransactionManager;
import com.epam.preprod.products_filter.FilterParameters;
import com.epam.preprod.service.ProductService;

import java.util.List;
import java.util.Optional;

public class ProductServiceImpl implements ProductService {
    private ProductDAO productDAO;
    private TransactionManager transactionManager;

    public ProductServiceImpl(MySqlProductDaoImpl mySqlProductDao, TransactionManager transactionManager) {
        this.productDAO = mySqlProductDao;
        this.transactionManager = transactionManager;
    }

    @Override
    public List<Category> getAllCategories() {
        return transactionManager.doInTransaction((connection) -> productDAO.getAllCategories(connection));
    }

    @Override
    public List<Manufacturer> getAllManufacturers() {
        return transactionManager.doInTransaction((connection) -> productDAO.getAllManufacturers(connection));
    }

    public List<Product> getFilteredProducts(FilterParameters filterParameters) {
        return transactionManager.doInTransaction((connection -> productDAO.getFilteredProducts(connection, filterParameters)));
    }

    @Override
    public int getNumberOfPages(FilterParameters filterParameters) {
        return transactionManager.doInTransaction((connection -> productDAO.getNumberOfPages(connection, filterParameters)));
    }

    @Override
    public Optional<Product> getProductById(int id) {
        return transactionManager.doInTransaction((connection) -> productDAO.getProductById(id, connection));
    }

}
