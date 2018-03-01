package com.epam.preprod.dao.impl.product;

import com.epam.preprod.builder.SqlFilterBuilder;
import com.epam.preprod.dao.ProductDAO;
import com.epam.preprod.entity.Category;
import com.epam.preprod.entity.Manufacturer;
import com.epam.preprod.entity.Product;
import com.epam.preprod.products_filter.FilterParameters;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class MySqlProductDaoImpl implements ProductDAO {
    private static final Logger logger = Logger.getLogger(MySqlProductDaoImpl.class);
    private static final String SQL_GET_ALL_CATEGORIES = "SELECT id, name FROM category";
    private static final String SQL_GET_ALL_MANUFACTURERS = "SELECT id, name FROM manufacturer";
    private static final String SQL_GET_PRODUCT_BY_ID = "SELECT id, name, category_id, manufacturer_id, price, description FROM webshop.product WHERE id = ?;";
    private SqlFilterBuilder sqlFilterBuilder;

    public MySqlProductDaoImpl() {
        sqlFilterBuilder = new SqlFilterBuilder();
    }

    @Override
    public Optional<Product> getProductById(int id, Connection connection) {
        try (PreparedStatement statement = connection.prepareStatement(SQL_GET_PRODUCT_BY_ID)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Product product = extractProductFromResultSet(resultSet);
                    if (Objects.nonNull(product)) {
                        return Optional.of(product);
                    }
                }
            }
        } catch (SQLException e) {
            logger.error("Can`t get product by this id", e);
        }
        return Optional.empty();
    }


    @Override
    public List<Category> getAllCategories(Connection connection) {
        List<Category> categories = new LinkedList<>();
        try (PreparedStatement statement = connection.prepareStatement(SQL_GET_ALL_CATEGORIES);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Category category = extractCategoryFromResultSet(resultSet);
                categories.add(category);
            }
        } catch (SQLException e) {
            logger.error("Can`t get categories", e);
        }
        return categories;
    }

    @Override
    public List<Manufacturer> getAllManufacturers(Connection connection) {
        List<Manufacturer> manufacturers = new LinkedList<>();
        try (PreparedStatement statement = connection.prepareStatement(SQL_GET_ALL_MANUFACTURERS);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Manufacturer manufacturer = extractManufacturerFromResultSet(resultSet);
                manufacturers.add(manufacturer);
            }
        } catch (SQLException e) {
            logger.error("Can`t get manufacturers", e);
        }
        return manufacturers;
    }

    @Override
    public List<Product> getFilteredProducts(Connection connection, FilterParameters filterParameters) {
        List<Product> products = new LinkedList<>();
        String filter = sqlFilterBuilder.buildSqlFilterForProduct(filterParameters);
        try (PreparedStatement statement = connection.prepareStatement(filter)) {

            int productsOnPage = filterParameters.getProductsOnPage();
            int positionFrom = filterParameters.getPageNumber() * productsOnPage - productsOnPage;

            statement.setInt(1, positionFrom);
            statement.setInt(2, productsOnPage);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Product product = extractProductFromResultSet(resultSet);
                    products.add(product);
                }
            }
        } catch (SQLException e) {
            logger.error("Can`t get products", e);
        }
        return products;
    }

    @Override
    public int getNumberOfPages(Connection connection, FilterParameters filterParameters) {
        int numberOfPages = 0;

        String filter = sqlFilterBuilder.buildSqlForCount(filterParameters);
        try (PreparedStatement statement = connection.prepareStatement(filter);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                int numberOfFilteredProducts = resultSet.getInt(1);
                int productsOnPage = filterParameters.getProductsOnPage();
                if (numberOfFilteredProducts % productsOnPage == 0) {
                    numberOfPages = numberOfFilteredProducts / productsOnPage;
                } else {
                    numberOfPages = numberOfFilteredProducts / productsOnPage + 1;
                }
            }
        } catch (SQLException e) {
            logger.error("Can`t get number of pages", e);
        }
        return numberOfPages;
    }

    private Product extractProductFromResultSet(ResultSet resultSet) {
        Product product = null;
        try {
            product = new Product();
            product.setId(resultSet.getInt("id"));
            product.setName(resultSet.getString("name"));
            product.setCategoryId(resultSet.getInt("category_id"));
            product.setManufacturerId(resultSet.getInt("manufacturer_id"));
            product.setPrice(resultSet.getBigDecimal("price"));
            product.setDescription(resultSet.getString("description"));
        } catch (SQLException e) {
            logger.error("Can`t extract product from result set", e);
        }
        return product;
    }

    private Category extractCategoryFromResultSet(ResultSet resultSet) {
        Category category = null;
        try {
            category = new Category();
            category.setId(resultSet.getInt("id"));
            category.setName(resultSet.getString("name"));
        } catch (SQLException e) {
            logger.error("Can`t extract category from result set", e);
        }
        return category;
    }

    private Manufacturer extractManufacturerFromResultSet(ResultSet resultSet) {
        Manufacturer manufacturer = null;
        try {
            manufacturer = new Manufacturer();
            manufacturer.setId(resultSet.getInt("id"));
            manufacturer.setName(resultSet.getString("name"));
        } catch (SQLException e) {
            logger.error("Can`t extract manufacturer from result set", e);
        }
        return manufacturer;
    }
}
