<%@ page contentType="text/html; charset=UTF-8" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

        <html lang="${pageContext.request.locale.language}">

        <head>

            <meta charset="utf-8">
            <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
            <meta name="description" content="">
            <meta name="author" content="">

            <title>Jewel Shop</title>

            <!-- Bootstrap core CSS -->
            <link href="../vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

            <!-- Custom styles for this template -->
            <link href="../css/shop-homepage.css" rel="stylesheet">
            <link href="../css/cart.css" rel="stylesheet">
             <link href="../css/homePage.css" rel="stylesheet">
            <!-- Bootstrap core JavaScript -->
            <script src="../vendor/jquery/jquery.min.js"></script>
            <script src="//code.jquery.com/jquery-1.11.0.min.js"></script>
            <script src="../vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
            <script src="../js/addToCart.js" type="text/javascript"></script>

           
        </head>


        <body>
             <%@ include file="jspf/navbar.jspf" %>
          
            <div class="filter">
                <div class="row">
                    <div class="col-lg-12" style="margin-top: 25px">
                        <div class="well">

                            <h3 align="center">Search Filter</h3>
                            <form class="form-horizontal" action="/home">
                                <div class="form-group">
                                    <label for="location1" class="control-label">Product Name</label>
                                    <input type="text" name="product_name" class="form-control" id="priceto" aria-describedby="basic-addon1" value="${product_name}">
                                </div>
                                <div class="form-group">
                                    <label for="location1" class="control-label">Manufacturer</label>
                                    <select class="form-control" name="manufacturer_id" id="location1">
                                        <option value="0">Manufacturer</option>
                                        <c:forEach var="manufacturer" items="${manufacturers}">
                                            <option value="${manufacturer.id}" name="manufacturerId" ${manufacturer.id == manufacturer_id ? 'selected="selected"' : ''}>"${manufacturer.name}"</option>
                                            </c:forEach>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label for="type1" class="control-label">Category</label>
                                    <select class="form-control" name="category_id" id="type1">
                                        <option value="0">Category</option>
                                            <c:forEach var="category" items="${categories}">
                                                <option name="categoryId" value="${category.id}" ${category.id == category_id ? 'selected="selected"' : ''}>"${category.name}"</option>
                                            </c:forEach>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label for="pricefrom" class="control-label">Min Price</label>
                                    <div class="input-group">
                                        <div class="input-group-addon" id="basic-addon1">$</div>
                                        <input type="text" class="form-control" name="min_price" id="pricefrom" aria-describedby="basic-addon1" value="${min_price}">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="priceto" class="control-label">Max Price</label>
                                    <div class="input-group">
                                        <div class="input-group-addon" id="basic-addon2">$</div>
                                        <input type="text" class="form-control" name="max_price" id="priceto" aria-describedby="basic-addon1" value="${max_price}">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="priceto" class="control-label">Order Price By</label>
                                    <div class="input-group">
                                        <select class="form-control" name="order_by" id="type1">
                                        <option value="Ascending">Ascending</option>
                                        <option value="Descending">Descending</option>
                                    </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="priceto" class="control-label">Products on page</label>
                                    <select class="form-control" name="products_on_page" id="type1">
                                        <option value="3">3</option>
                                        <option value="5">5</option>
                                        <option value="10">10</option>
                                    </select>
                                </div>
                                <p class="text-center"><button href="/" class="btn btn-primary" role="button">Apply</button></p>
                            </form>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-md-9 products">
                <div class="form-group">
                    <h1 class="my-3 m-0 ">Products
                    </h1>
                </div>
                <c:choose>
                    <c:when test="${empty products}">
                        <h3>Products are not found</h3>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="product" items="${products}">
                            <div class="row">
                                <div class="col-md-7">
                                    <a href="#">
                                <img class="img-fluid rounded" src="http://placehold.it/700x300" alt="">
                            </a>
                                </div>
                                <div class="col-md-5">
                                    <h3>${product.name}</h3>
                                    <label><strong>Despription:</strong></label><label> ${product.description}</label><br>
                                    <label><strong>Price:</strong></label><label> ${product.price}$</label><br>
                                    <input type="hidden" id="productInfo" value="${product.id}">
                                    <button class="btn btn-primary btn-update addToCart">Add to cart</button>
                                </div>
                            </div>
                            <hr>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
                <hr>

                <ul class="pagination justify-content-center">
                    <c:forEach var="i" begin="1" end="${pages}">
                        <li class="page-item">
                            <a class="page-link" href="/home?page_number=${i}&${urlParams}">${i}</a>

                        </li>
                    </c:forEach>
                </ul>

            </div>
        </body>

        </html>
