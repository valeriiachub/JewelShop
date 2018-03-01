package com.epam.preprod.products_filter;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Objects;

public class FilterExtractor {

    public FilterParameters extractFilterParametersFromRequest(HttpServletRequest request) {
        String name = getCorrectStringValue(request.getParameter("product_name"));
        int manufacturerId = getCorrectIntValue(request.getParameter("manufacturer_id"), 0);
        int categoryId = getCorrectIntValue(request.getParameter("category_id"), 0);
        BigDecimal minPrice = getCorrectBigDecimalValue(request.getParameter("min_price"));
        BigDecimal maxPrice = getCorrectBigDecimalValue(request.getParameter("max_price"));
        String orderBy = getCorrectStringValue(request.getParameter("order_by"));
        int productsOnPage = getCorrectIntValue(request.getParameter("products_on_page"), 3);
        int pageNumber = getCorrectIntValue(request.getParameter("page_number"), 1);

        return new FilterParameters(name, manufacturerId, categoryId, minPrice, maxPrice,
                orderBy, productsOnPage, pageNumber);
    }

    private String getCorrectStringValue(String string) {
        if (Objects.isNull(string) || "".equals(string)) {
            return "";
        } else {
            return string;
        }
    }

    private BigDecimal getCorrectBigDecimalValue(String value) {
        if (Objects.isNull(value) || "".equals(value)) {
            return BigDecimal.ZERO;
        } else {
            return new BigDecimal(value);
        }
    }

    private int getCorrectIntValue(String value, int defaultValue) {
        int correctValue;
        try {
            correctValue = Integer.parseInt(value);
        } catch (NumberFormatException e) {
            correctValue = defaultValue;
        }
        return correctValue;
    }
}
