<%@ page language="java" contentType = "text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="profile" />
<!DOCTYPE html>
<html>
<header>
    <jsp:include page="/WEB-INF/fragments/header.jsp"/>
</header>
<body>
<div class="form-group">
    <div class="container">
        <h3 class="user_name">${sessionScope.user_name}</h3>
        <h3><fmt:message key="profile.info.login"/>: ${sessionScope.user_login}</h3>
        <h3><fmt:message key="profile.info.email"/>: ${sessionScope.user_email}</h3>
        <hr>
        <p class="loyalty_points"><fmt:message key="profile.info.loyalty.points"/>: ${sessionScope.loyalty_points}</p>
    </div>
</div>
</body>
</html>