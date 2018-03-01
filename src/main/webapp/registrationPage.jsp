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

    <form class="well form-horizontal" action="register" method="post" enctype="multipart/form-data" id="contact_form"
          name="regForm">
        <fieldset>
            <legend>
                <center>
                    <h2>
                        <b>Sign up</b>
                    </h2>
                </center>
            </legend>
            <br>

            <div class="form-group">
                <c:if test="${!empty errors['existedEmail']}"><label
                        id="firstNameError">${errors['existedEmail']}</label></c:if>
                <label class="col-md-4 control-label">First Name</label>
                <div class="col-md-4 inputGroupContainer">
                    <div class="input-group">
                            <span class="input-group-addon">
                                <i class="glyphicon glyphicon-user"></i>
                            </span>
                        <input id="first_name" name="firstName" placeholder="First Name" class="form-control"
                               type="text" value=${bean.firstName}>
                    </div>
                    <c:if test="${!empty errors['firstName']}"><label
                            id="firstNameError">${errors['firstName']}</label></c:if>
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-4 control-label">Last Name</label>
                <div class="col-md-4 inputGroupContainer">
                    <div class="input-group">
                            <span class="input-group-addon">
                                <i class="glyphicon glyphicon-user"></i>
                            </span>
                        <input id="last_name" name="lastName" placeholder="Last Name" class="form-control" type="text"
                               value=${bean.lastName}>
                    </div>
                    <c:if test="${!empty errors['lastName']}"><label
                            id="lastNameError">${errors['lastName']}</label></c:if>
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-4 control-label">E-Mail</label>
                <div class="col-md-4 inputGroupContainer">
                    <div class="input-group">
                            <span class="input-group-addon">
                                <i class="glyphicon glyphicon-envelope"></i>
                            </span>
                        <input id="email" name="email" placeholder="E-Mail Address" class="form-control" type="text"
                               value=${bean.email}>
                    </div>
                    <c:if test="${!empty errors['email']}"><label id="emailError">${errors['email']}</label></c:if>
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-4 control-label">Password</label>
                <div class="col-md-4 inputGroupContainer">
                    <div class="input-group">
                            <span class="input-group-addon">
                                <i class="glyphicon glyphicon-user"></i>
                            </span>
                        <input id="password" name="password" placeholder="Password" class="form-control"
                               type="password">
                    </div>
                    <c:if test="${!empty errors['password']}"><label
                            id="passwordError">${errors['password']}</label></c:if>
                </div>
            </div>
            <div class="form-group">
                <label class="col-md-4 control-label">Photo</label>
                <div class="col-md-4 inputGroupContainer">
                    <div class="input-group">
                            <span class="input-group-addon">
                                <i class="glyphicon glyphicon-picture"></i>
                            </span>
                        <input id="photo" name="photo" placeholder="Upload your photo" class="form-control"
                               type="file">
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label class="col-md-4 control-label">Send news</label>
                <div class="col-md-4 inputGroupContainer">
                    <div class="input-group">
                        <input id="spam" name="sendNews" type="checkbox" class="form-check-input">
                    </div>
                </div>
            </div>
            <center>
                <div class="form-group">
                    <label class="col-md-4 control-label"></label>
                    <div class="col-md-4 inputGroupContainer">
                        <c:if test="${!empty errors['incorrectCaptcha']}"><label
                                id="captchaError">${errors['incorrectCaptcha']}</label></c:if>
                        <div class="input-group">
                            <custom:captcha url="/captcha-image.jpg"
                                            captchaID="${requestScope.captchaID}"></custom:captcha>
                        </div>
                    </div>
                </div>
            </center>
            <div class="form-group">
                <label class="col-md-4 control-label"></label>
                <div class="col-md-4">
                    <br>
                    <button id="submit" name="submit" type="submit" class="btn btn-warning" onclick="validate(document.regForm.first_name, document.regForm.last_name,
                        document.regForm.email, document.regForm.password)">Sign up
                        <span class="glyphicon glyphicon-send"></span>
                    </button>
                </div>
            </div>
        </fieldset>
    </form>
</div>
</body>

</html>