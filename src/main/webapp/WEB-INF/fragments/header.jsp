<%@ page language="java" contentType = "text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="header" />
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8"/>
    <link rel="stylesheet" type="text/css" href="../../css/style.css"/>
</head>
<body>
<div class="navbar">
    <a href="/"><fmt:message key="header.page.home"/></a>
    <div class="dropdown">
        <button class="dropbtn"><fmt:message key="header.button.menu"/></button>
        <div class="dropdown-content">
            <a href="/controller?command=show_category_products&category=drink&page=1"><fmt:message key="header.page.drink"/></a>
            <a href="/controller?command=show_category_products&category=snack&page=1"><fmt:message key="header.page.snack"/></a>
            <a href="/controller?command=show_category_products&category=salad&page=1"><fmt:message key="header.page.salad"/></a>
            <a href="/controller?command=show_category_products&category=hot_meal&page=1"><fmt:message key="header.page.hot"/></a>
        </div>
    </div>

    <c:if test="${sessionScope.user_role eq 'USER'}">
        <a href="/controller?command=show_orders"><fmt:message key="header.page.orders"/></a>
        <a href="/controller?command=show_cart"><fmt:message key="header.page.cart"/></a>
    </c:if>
    <c:if test="${sessionScope.user_role ne 'GUEST'}">
        <a href="/profile"><fmt:message key="header.page.profile"/></a>
    </c:if>
    <c:if test="${sessionScope.user_role eq 'ADMIN'}">
        <a href="/controller?command=show_users"><fmt:message key="header.page.users"/></a>
    </c:if>

    <div class="dropdown">
        <button class="dropbtn"><fmt:message key="header.language.current"/></button>
        <div class="dropdown-content">
            <c:set var="parameters_string" value="${pageContext.request.queryString}"/>
            <c:set var="lang" value="language"/>
            <c:if test="${fn:contains(parameters_string, lang)}">
                <c:set var="parameters_string_after_substring" value="${fn:substringBefore(parameters_string, lang)}"/>
                <c:set var="parameters_string" value="${fn:substring(parameters_string_after_substring, 0, fn:length(parameters_string_after_substring)-1)}"/>
            </c:if>
            <c:choose>
                <c:when test="${fn:length(parameters_string)==0}">
                    <a href="${requestScope.requestURI}?language=<fmt:message key="header.language.parameter"/>">
                        <fmt:message key="header.language.switch"/>
                    </a>
                </c:when>
                <c:otherwise>
                    <a href="${requestScope.requestURI}?${parameters_string}&language=<fmt:message key="header.language.parameter"/>">
                        <fmt:message key="header.language.switch"/>
                    </a>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

    <c:if test="${sessionScope.user_role eq 'GUEST'}">
        <a href="/login"><fmt:message key="header.page.login"/></a>
    </c:if>
    <c:if test="${sessionScope.user_role ne 'GUEST'}">
        <a href="/controller?command=log_out"><fmt:message key="header.page.logout"/></a>
    </c:if>
</div>
</body>
</html>
