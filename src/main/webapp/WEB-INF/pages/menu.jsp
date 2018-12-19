<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<header>
    <jsp:include page="/WEB-INF/fragments/header.jsp"/>
</header>

<body>



    <c:if test="${sessionScope.user_role eq 'admin'}">
        <form action="/controller" method="post">
            <input type="hidden" name="command" value="add_product">
            <input type="submit" value="Add product">
        </form>
    </c:if>

    <c:set var="products" value="${sessionScope.category_products}"/>




<c:if test="${sessionScope.category_products ne null}">
    <c:forEach items="${products}" var="product">
        <h3>Name: ${product.name}</h3>
        <h3>Cost: ${product.cost}</h3>


        <form action="/menu/product?id=${product.id}" method="get">
            <input type="submit" value="More information">
        </form>

    </c:forEach>
</c:if>




</body>
</html>