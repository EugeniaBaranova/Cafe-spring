<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="users" />
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8"/>
    <title><fmt:message key="users.title"/></title>
</head>
<header>
    <jsp:include page="/WEB-INF/fragments/header.jsp"/>
</header>
<body>
<table id="table">
    <tr>
        <th><fmt:message key="users.th.id"/></th>
        <th><fmt:message key="users.th.name"/></th>
        <th><fmt:message key="users.th.email"/></th>
        <th><fmt:message key="users.th.login"/></th>
        <th><fmt:message key="users.th.loyalty.points"/></th>
        <th><fmt:message key="users.th.blocked"/></th>
        <th><fmt:message key="users.th.role"/></th>
    </tr>
        <c:set var="users" value="${requestScope.users}"/>
        <c:forEach items="${users}" var="user">
            <tr>
                <td>${user.id}</td>
                <td>${user.name}</td>
                <td>${user.email}</td>
                <td>${user.login}</td>
                <td>${user.loyaltyPoints}</td>
                <td>${user.blocked}</td>
                <td>${user.role}</td>
            </tr>
        </c:forEach>
</table>
</body>
</html>
