package com.epam.preprod.servlet;

import com.epam.preprod.entity.Category;
import com.epam.preprod.entity.Manufacturer;
import com.epam.preprod.entity.Product;
import com.epam.preprod.products_filter.FilterExtractor;
import com.epam.preprod.products_filter.FilterParameters;
import com.epam.preprod.service.ProductService;
import org.apache.log4j.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/home")
public class ShowProductsServlet extends HttpServlet {
    private ProductService productService;
    private static final Logger logger = Logger.getLogger(ShowProductsServlet.class);
    private FilterExtractor filterExtractor;

    @Override
    public void init(ServletConfig servletConfig) {
        ServletContext servletContext = servletConfig.getServletContext();
        productService = (ProductService) servletContext.getAttribute("productService");
        filterExtractor = new FilterExtractor();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        FilterParameters filterParameters = filterExtractor.extractFilterParametersFromRequest(request);
        String urlParams = request.getQueryString() != null ? request.getQueryString().replaceFirst("&?page_number=\\d*&", "") : "";
        logger.debug(filterParameters);

        setAttributesInRequest(request, filterParameters, urlParams);
        setFilterAttributesInRequest(request, filterParameters);

        request.getRequestDispatcher("homePage.jsp").forward(request, response);
    }

    private void setAttributesInRequest(HttpServletRequest request, FilterParameters filterParameters, String urlParams) {
        int pages = productService.getNumberOfPages(filterParameters);
        List<Category> categories = productService.getAllCategories();
        List<Manufacturer> manufacturers = productService.getAllManufacturers();
        List<Product> products = productService.getFilteredProducts(filterParameters);

        request.setAttribute("pages", pages);
        request.setAttribute("products", products);
        request.setAttribute("categories", categories);
        request.setAttribute("urlParams", urlParams);
        request.setAttribute("manufacturers", manufacturers);
    }

    private void setFilterAttributesInRequest(HttpServletRequest request, FilterParameters filterParameters) {
        request.setAttribute("product_name", filterParameters.getProductName());
        request.setAttribute("manufacturer_id", filterParameters.getManufacturer());
        request.setAttribute("category_id", filterParameters.getCategory());
        request.setAttribute("min_price", filterParameters.getMinPrice());
        request.setAttribute("max_price", filterParameters.getMaxPrice());
        request.setAttribute("order_by", filterParameters.getOrderBy());
        request.setAttribute("products_on_page", filterParameters.getProductsOnPage());
    }
}
