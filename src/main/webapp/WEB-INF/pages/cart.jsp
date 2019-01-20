<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="cart" />
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8"/>
    <title><fmt:message key="cart.title"/></title>
</head>
<header>
    <jsp:include page="/WEB-INF/fragments/header.jsp"/>
</header>
<body>
    <c:if test="${empty requestScope.cartInfo.products}">
        <h1 class="text_cart"><fmt:message key="cart.empty"/></h1>
    </c:if>
    <c:if test="${!empty requestScope.cartInfo.products}">

        <div class="span_container">
            <ul class="span_ul span-card-4">
            <c:set var="products" value="${requestScope.cartInfo.products}"/>
            <c:forEach items="${products}" var="product">
                <li class="span-bar">
                    <a href="/controller?command=delete_from_cart&id=${product.id}" class="span-bar-item span-button span-white span-xlarge span-right">×</a>
                    <img src="/image_content?id=${product.id}" class="span-bar-item" style="width:85px" alt="Image not found">
                    <div class="span-bar-item">
                        <span class="span-large">${product.name}</span><br>
                        <span><fmt:message key="cart.product.cost"/>: ${product.cost} <fmt:message key="cart.product.currency"/>.</span>
                    </div>
                </li>
            </c:forEach>
            </ul>
        </div>

        <div class="form-group" style="margin-top: auto">
            <form action="/controller" method="post">
                <input type="hidden" name="command" value="make_order">
                <div class="container">
                    <h1 class="text_cart"><fmt:message key="cart.sum"/>: ${requestScope.cartInfo.sum} <fmt:message key="cart.product.currency"/>.</h1>

                    <label for="receiving_date"><b><fmt:message key="cart.receiving.date"/></b></label>
                    <input class="form-control" type="date" placeholder="Enter" id="receiving_date" name="receiving_date" required/>
                    <p id = "receiving_date_error"></p>

                    <label for="payment_method"><b><fmt:message key="cart.payment.method"/></b></label>
                    <select name="payment_method" id="payment_method" required>
                        <option value="CASH"><fmt:message key="cart.method.cash"/></option>
                        <option value="CARD"><fmt:message key="cart.method.card"/></option>
                    </select>
                    <p id = "payment_method_error"></p>

                    <button class="add_btn" type="submit"><fmt:message key="cart.make.order"/></button>
                </div>
            </form>
        </div>

    </c:if>
</body>
</html>