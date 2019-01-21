<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="product" />
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8"/>
    <title><fmt:message key="product.title"/></title>
</head>
<header>
    <jsp:include page="/WEB-INF/fragments/header.jsp"/>
</header>
<body>
    <div class="card-wrapper">
        <c:if test="${requestScope.containsKey(product)}">
            <c:set var="product" value="${requestScope.product}"/>
        </c:if>
        <c:if test="${sessionScope.containsKey(product)}">
            <c:set var="product" value="${sessionScope.product}"/>
        </c:if>
        <div class="card">
            <div class="imgcontainer">
                <div class="child_imgcontainer">
                    <img src="/image_content?id=${product.id}" class="card-img" alt="Image not found">
                </div>
            </div>

            <h3>${product.name}</h3>
            <p><fmt:message key="product.cost"/>: ${product.cost} <fmt:message key="product.currency"/>.</p>
            <div class="description">
                <h3><fmt:message key="product.description"/>: ${product.description}.</h3>
            </div>

            <c:if test="${sessionScope.user_role eq 'USER'}">
            <form action="/controller" method="post">
                <input type="hidden" name="command" value="add_to_cart">
                <input type="hidden" name="id" value="${product.id}">
                <input type="hidden" name="request_page" value="/controller?command=show_product&id=${param.id}">
                <button class="add_btn" type="submit"><fmt:message key="product.add.to.cart"/></button>
            </form>
            </c:if>
            <c:if test="${sessionScope.user_role eq 'ADMIN'}">
                <form action="/edit_product" method="post">
                    <input type="hidden" name="id" value="${product.id}">
                    <button class="add_btn" type="submit"><fmt:message key="product.edit.product"/></button>
                </form>
            </c:if>
            <c:if test="${sessionScope.user_role eq 'ADMIN'}">
                <form action="/controller" method="post">
                    <input type="hidden" name="command" value="delete_product">
                    <input type="hidden" name="id" value="${product.id}">
                    <button class="add_btn" type="submit"><fmt:message key="product.delete.product"/></button>
                </form>
            </c:if>
        </div>
    </div>
</body>
</html>
