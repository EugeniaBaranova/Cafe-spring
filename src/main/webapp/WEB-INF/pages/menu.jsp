<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="menu" />
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8"/>
    <title><fmt:message key="menu.title"/></title>
</head>
<header>
    <jsp:include page="/WEB-INF/fragments/header.jsp"/>
</header>
<body>
<div class="cards">

    <c:if test="${sessionScope.user_role eq 'ADMIN'}">
        <form action="/add_product" method="post">
            <button class="add_new_btn" type="submit"><fmt:message key="menu.add.new"/></button>
        </form>
    </c:if>
    <div class="row">
        <c:if test="${requestScope.category_products ne null}">
            <c:set var="products" value="${requestScope.category_products}"/>
            <c:forEach items="${products}" var="product">
                <div class="column">
                    <div class="card">
                        <div class="imgcontainer">
                            <div class="child_imgcontainer">
                                <img src="/image_content?id=${product.id}" class="card-img" alt="Image not found">
                            </div>
                        </div>

                        <h3>${product.name}</h3>
                        <p><fmt:message key="menu.product.cost"/>: ${product.cost} <fmt:message key="menu.product.currency"/>.</p>

                        <form action="/controller" method="get">
                            <input type="hidden" name="command" value="show_product">
                            <input type="hidden" name="id" value="${product.id}">
                            <button class="info_btn" type="submit"><fmt:message key="menu.button.info"/></button>
                        </form>

                        <c:if test="${sessionScope.user_role eq 'USER'}">
                        <form action="/controller" method="post">
                            <input type="hidden" name="command" value="add_to_cart">
                            <input type="hidden" name="id" value="${product.id}">
                            <input type="hidden" name="request_page" value="/controller?command=show_category_products&category=${param.category}&page=${param.page}">
                            <button class="add_btn" type="submit"><fmt:message key="menu.cart.add"/></button>
                        </form>
                        </c:if>
                    </div>
                </div>
            </c:forEach>
        </c:if>
    </div>
    <div class="center">
        <div class="pagination">
            <a href="#">&laquo;</a>

            <c:if test="${requestScope.pages ne null}">
                <c:set var="pages" value="${requestScope.pages}"/>
                <c:forEach items="${pages}" var="page">
                    <c:if test="${page eq param.page}">
                        <c:set var="class_href" value="active"/>
                    </c:if>
                    <c:if test="${page ne param.page}">
                        <c:set var="class_href" value="non_active"/>
                    </c:if>
                    <a class="${class_href}" href="/controller?command=show_category_products&category=${param.category}&page=${page}">${page}</a>
                </c:forEach>
            </c:if>

            <a href="#">&raquo;</a>
        </div>
    </div>
</div>
</body>
</html>