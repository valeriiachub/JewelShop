<%@ taglib prefix="sc" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${pageContext.request.locale.language}"/>
<fmt:setBundle basename="navbar"/>

<fmt:message key="language" var="languageText"/>
<fmt:message key="english" var="english"/>
<fmt:message key="russian" var="russian"/>

<div class="btn-group">
  <button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown">
    ${languageText}
  <span class="caret"></span></button>
  <div class="dropdown-menu">
    <a class="dropdown-item" href="${requestScope['javax.servlet.forward.query_string']}?lang=en">${english}</a>
    <a class="dropdown-item" href="${requestScope['javax.servlet.forward.query_string']}?lang=ru">${russian}</a>
</div>