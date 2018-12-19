<%@ page language="java" contentType = "text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="header" />
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8"/>
    <link rel="stylesheet" type="text/css" href="../../css/style.css"/>
    <%--<style><%@include file="/css/style.css"%></style>--%>
</head>
<body>

<div class="navbar">
    <a href="/"><fmt:message key="header.page.home"/></a>
    <div class="dropdown">
        <button class="dropbtn"><fmt:message key="header.button.menu"/></button>
        <div class="dropdown-content">
            <a href="/controller?command=show_category_products&category=drink"><fmt:message key="header.page.drink"/></a>
            <a href="/controller?command=show_category_products&category=snack"><fmt:message key="header.page.snack"/></a>
            <a href="/controller?command=show_category_products&category=salad"><fmt:message key="header.page.salad"/></a>
            <a href="/controller?command=show_category_products&category=hot_meal"><fmt:message key="header.page.hot"/></a>
        </div>
    </div>

    <c:if test="${sessionScope.user_role ne 'guest'}">
        <a href="/controller?command=show_orders"><fmt:message key="header.page.orders"/></a>
        <a href="/controller?command=show_cart"><fmt:message key="header.page.cart"/></a>
    </c:if>
    <c:if test="${sessionScope.user_role eq 'admin'}">
        <a href="/controller?command=show_users"><fmt:message key="header.page.users"/></a>
    </c:if>

    <div class="dropdown">
        <button class="dropbtn">
            <c:if test="${language eq 'en' or 'en_US'}">English</c:if>
            <c:if test="${language eq 'ru' or 'ru_RU'}">Русский</c:if>
        </button>
        <div class="dropdown-content">
            <c:if test="${language eq 'ru' or 'ru_RU'}">
            <a href="${requestScope.requestURI}?language=en">English</a>
            </c:if>
            <c:if test="${language eq 'en' or 'en_US'}">
            <a href="${requestScope.requestURI}?language=ru">Русский</a>
            </c:if>
        </div>
    </div>

    <c:if test="${sessionScope.user_role eq 'guest'}">
        <a href="/login"><fmt:message key="header.page.login"/></a>
    </c:if>
    <c:if test="${sessionScope.user_role ne 'guest'}">
        <a href="/controller?command=log_out"><fmt:message key="header.page.logout"/></a>
    </c:if>
</div>

</body>
</html>
