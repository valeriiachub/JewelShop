<%@ page contentType="text/html; charset=UTF-8" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <html>

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
            <link href="../css/cartIcon.css" rel="stylesheet">
            <link href="../css/orderPage.css" rel="stylesheet">
            <!-- Bootstrap core JavaScript -->
            <script src="../vendor/jquery/jquery.min.js"></script>
            <script src="//code.jquery.com/jquery-1.11.0.min.js"></script>
            <script src="../vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

        </head>

        <body>
            <%@ include file="jspf/navbar.jspf" %>
            </%@>

            <div class="col-md-9 products">
                <div class="form-group">
                    <h1 class="my-3 m-0 ">Orders
                    </h1>
                </div>
                <c:forEach var="item" items="${orderedProducts}">
                    <div class="row">
                        <div class="col-md-5">
                            <a href="#">
                                <img class="img-fluid rounded" src="http://placehold.it/300x200" alt="">
                            </a>
                        </div>
                        <div class="col-md-4">
                            <h3>${item.product.name}</h3>
                            <label><strong>Despription:</strong></label><label> ${item.product.description}</label><br>
                            <label><strong>Price:</strong></label><label> ${item.price}$</label><br>
                            <label><strong>Quantity:</strong></label><label>${item.quantity}</label>
                        </div>
                    </div>
                    <hr>
                </c:forEach>
                <label><strong>Total amount:</strong></label><label>${totalAmount}$</label>
                <label><strong>Number of products:</strong></label><label>${numberOfProducts}</label><br>
                <label><strong>Delivery:</strong></label><label>${totalAmount}$</label>
                <label><strong>Payment:</strong></label><label>${totalAmount}$</label>
                
                <button class="btn btn-primary" type="submit">Confirm order</button>
                        </form>
                    </div>
        </body>

        </html>
