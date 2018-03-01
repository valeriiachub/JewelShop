<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="custom" tagdir="/WEB-INF/tags" %>

<html>

<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <link rel="stylesheet" href="../css/regForm.css">

    <!--    <script src="../js/validationJS.js"></script> -->

</head>
<body>
<div class="container">
    <custom:login firstName="${requestScope.firstName}"
                  imgUrl="/photo.jpg?imgUrl=${requestScope.imgUrl}"></custom:login>
</div>
</div>
</body>

</html>