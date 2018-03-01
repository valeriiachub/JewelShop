<%@ taglib prefix="sc" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ attribute name="url" required="true" %>
<%@ attribute name="captchaID"%>

<c:choose>
    <c:when test="${!empty requestScope.captchaID}">
        <input type="hidden" name="captchaID" value="${captchaID}">
        <img src="${url}?captchaID=${captchaID}" class="img-rounded">
    </c:when>
    <c:otherwise>
        <img src="${url}" class="img-rounded">
    </c:otherwise>
</c:choose>
<br><br>
<input type="text" name="captchaValue" placeholder="Enter captcha value" class="form-check-input">