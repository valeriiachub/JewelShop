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
            <link href="../css/cart.css" rel="stylesheet">
            <!-- Bootstrap core JavaScript -->
            <script src="../vendor/jquery/jquery.min.js"></script>
            <script src="//code.jquery.com/jquery-1.11.0.min.js"></script>
            <script src="../vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
            <style>


            </style>
            <script>


            </script>
        </head>

        <body>
            <%@ include file="jspf/navbar.jspf" %>
            </%@>

            <div class="col-md-9 products">
                <div class="form-group">
                    <h1 class="my-3 m-0 ">Shopping cart
                    </h1>
                </div>
                <c:choose>
                    <c:when test="${empty shoppingCart}">
                        <h3>Your cart is empty</h3>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="item" items="${shoppingCart}">
                            <div class="row">
                                <div class="col-md-5">
                                    <a href="#">
                                <img class="img-fluid rounded" src="http://placehold.it/300x200" alt="">
                            </a>
                                </div>
                                <div class="col-md-4">
                                    <h3>${item.key.name}</h3>
                                    <label><strong>Description:</strong></label><label> ${item.key.description}</label><br>
                                    <label><strong>Price:</strong></label><label> ${item.key.price}$</label><br>
                                    <label><strong>Quantity:</strong></label>
                                    <form action="/updateCart" method="post">
                                        <input type="hidden" value="${item.key.id}" name="productId">
                                        <input type="number" size="5" name="quantity" min="1" max="5" value="${item.value}" class="form-control quantity input"><br>
                                        <button class="btn btn-primary">Update quantity</button><br>
                                    </form>
                                    <form action="/deleteFromCart" method="post">
                                        <input type="hidden" name="productId" value="${item.key.id}">
                                        <button type="submit" class="btn btn-primary deleteFromCart">Delete product</button>
                                    </form>
                                </div>
                            </div>
                            <hr>
                        </c:forEach>
                        <label><strong>Total price:</strong></label><label>${totalPrice}$</label>
                        <form action="/makeOrder" method="post">
                            <input type="hidden" name="URL" value="/cart">
                            <button class="btn btn-primary">Make order</button>
                        </form>
                        <form action="/clearCart" method="post">
                            <button class="btn btn-primary">Clear cart</button>
                        </form>
                    </c:otherwise>
                </c:choose>
            </div>
        </body>

        </html>
