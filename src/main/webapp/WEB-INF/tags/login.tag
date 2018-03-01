<%@ taglib prefix="sc" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ attribute name="firstName" required="true" %>
<%@ attribute name="imgUrl" required="true" %>

<c:choose>
    <c:when test="${empty sessionScope.user}">
        <form class="well form-horizontal" action="auth" method="post" id="contact_form" name="logForm">
            <fieldset>
                <legend>
                    <center>
                        <h2>
                            <b>Sign in</b>
                        </h2>
                    </center>
                </legend>
                <br>
                <div class="form-group">
                    <label class="col-md-4 control-label">E-Mail</label>
                    <div class="col-md-4 inputGroupContainer">
                        <div class="input-group">
                                   <span class="input-group-addon">
                                       <i class="glyphicon glyphicon-envelope"></i>
                                   </span>
                            <input id="email" name="email" placeholder="E-Mail Address" class="form-control"
                                   type="text">
                        </div>
                        <c:if test="${!empty errors['existedEmail']}"><label
                                id="emailError">${errors['existedEmail']}</label></c:if>
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
                    <label class="col-md-4 control-label"></label>
                    <div class="col-md-4">
                        <br>
                        <button id="logIn" name="logIn" type="submit" class="btn btn-warning">Log in
                            <span class="glyphicon glyphicon-send"></span>
                        </button>
                    </div>
                </div>
            </fieldset>
        </form>
    </c:when>
    <c:otherwise>
        <form class="well form-horizontal" action="logout" method="post" id="contact_form" name="greetingForm">
            <div class="form-group">
                <label class="col-md-4 control-label"></label>
                <div class="col-md-4 inputGroupContainer">
                    <div class="input-group">
                        <img src="${imgUrl}" width="150" height="150" class="img-rounded">
                    </div>
                    <label id="firstName">${firstName}</label>
                </div>
            </div>
            <div class="form-group">
                <label class="col-md-4 control-label"></label>
                <div class="col-md-4">
                    <br>
                    <button id="logOut" name="logOut" type="submit" class="btn btn-warning">Log out
                        <span class="glyphicon glyphicon-send"></span>
                    </button>
                </div>
            </div>
        </form>
    </c:otherwise>
</c:choose>