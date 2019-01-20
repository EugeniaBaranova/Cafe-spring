<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="orders" />
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8"/>
    <title><fmt:message key="orders.title"/></title>
</head>
<header>
    <jsp:include page="/WEB-INF/fragments/header.jsp"/>
</header>
<body>
<table id="table">
    <tr>
        <th><fmt:message key="orders.th.date"/></th>
        <th><fmt:message key="orders.th.receiving.date"/></th>
        <th><fmt:message key="orders.th.sum"/></th>
        <th><fmt:message key="orders.th.payment.method"/></th>
        <th><fmt:message key="orders.th.state"/></th>
        <th><fmt:message key="orders.th.paid"/></th>
    </tr>
    <c:set var="orders" value="${requestScope.orders}"/>
    <c:forEach items="${orders}" var="order">
        <tr>
            <td>${order.orderDate}</td>
            <td>${order.receivingDate}</td>
            <td>${order.sum}</td>
            <td>${order.paymentMethod}</td>
            <td>${order.orderState}</td>
            <td>${order.paid}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
