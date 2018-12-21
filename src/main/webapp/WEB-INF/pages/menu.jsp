<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="header" />
<!DOCTYPE html>
<html>
<header>
    <jsp:include page="/WEB-INF/fragments/header.jsp"/>
</header>
<body>
<div class="cards">

    <c:if test="${sessionScope.user_role eq 'admin'}">
        TODO
        <form action="/controller" method="post">
            <input type="hidden" name="command" value="add_product">
            <input type="submit" value="Add product">
        </form>
    </c:if>
    <div class="row">
        <c:if test="${requestScope.category_products ne null}">
            <c:set var="products" value="${requestScope.category_products}"/>
            <c:forEach items="${products}" var="product">
                <div class="column">
                    <div class="card">
                        <div class="imgcontainer">
                            <img src="${product.imageReference}" class="card-img">
                        </div>

                        <h3>${product.name}</h3>
                        <p>Cost: ${product.cost} rub.</p>

                        <form action="/menu/product?id=${product.id}" method="get">
                            <input type="hidden" name="command" value="show_product">
                            <button class="info_btn" type="submit">More information</button>
                        </form>

                        <c:if test="${sessionScope.user_role ne 'guest'}">
                        <form action="/controller" method="post">
                            <input type="hidden" name="command" value="add_to_cart">
                            <button class="add_btn" type="submit">Add to cart</button>
                        </form>
                        </c:if>
                    </div>
                </div>
            </c:forEach>
        </c:if>
    </div>
</div>
</body>
</html>