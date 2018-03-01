package com.epam.preprod.servlet.shopping_cart;

import com.epam.preprod.cart.ShoppingCart;
import com.epam.preprod.entity.Product;
import com.epam.preprod.service.ProductService;
import org.apache.log4j.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/addToCart")
public class AddToCartServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(AddToCartServlet.class);
    private ProductService productService;

    @Override
    public void init(ServletConfig servletConfig) {
        ServletContext servletContext = servletConfig.getServletContext();
        productService = (ProductService) servletContext.getAttribute("productService");
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ShoppingCart cart = getCartFromSession(request);

        int productId = Integer.parseInt(request.getParameter("productInfo"));
        logger.debug(productId);
        Optional<Product> optionalProduct = productService.getProductById(productId);
        optionalProduct.ifPresent(product -> cart.addProductToCart(product, 1));
        optionalProduct.ifPresent(logger::debug);

        response.setContentType("text/plain");
        response.getWriter().write("" + cart.getNumberOfProductsInShoppingCart() + "");
    }

    private ShoppingCart getCartFromSession(HttpServletRequest request) {
        return (ShoppingCart) request.getSession().getAttribute("shoppingCart");
    }
}
