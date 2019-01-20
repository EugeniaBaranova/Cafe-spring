<%@ page language="java" contentType = "text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="edit_product" />
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8"/>
    <title><fmt:message key="edition.title"/></title>
</head>
<header>
    <jsp:include page="/WEB-INF/fragments/header.jsp"/>
</header>
<body>
<div class="form-group">
    <h1><fmt:message key="edition.product"/></h1>
    <c:if test="${sessionScope.unsuccessful_edition}">
        <h2><fmt:message key="edition.text.unsuccessful"/></h2>
    </c:if>
    <c:forEach var="error" items="${sessionScope.edition_errors}">
        <p class="error_message"><fmt:message key="${error.getMessage()}"/></p>
    </c:forEach>

    <form action="/image_content" method="POST" name="addition_form" enctype="multipart/form-data" data-language="${language}">
        <input type="hidden" name="command" value="edit_product">
        <input type="hidden" name="id" value="${param.id}">
        <div class="container">
            <label for="image"><b><fmt:message key="edition.label.image"/></b></label>
            <input class="form-control" type="file" accept=".jpg" id="image" name="image"/>
            <p id = "image_error"></p>

            <label for="name"><b><fmt:message key="edition.label.name"/></b></label>
            <input class="form-control" type="text" placeholder="<fmt:message key="edition.placeholder.name"/>" id="name" name="name"/>
            <p id = "name_error"></p>

            <label for="cost"><b><fmt:message key="edition.label.cost"/></b></label>
            <input class="form-control" type="text" placeholder="<fmt:message key="edition.placeholder.cost"/>" id="cost" name="cost"/>
            <p id = "cost_error"></p>

            <label for="amount"><b><fmt:message key="edition.label.amount"/></b></label>
            <input class="form-control" type="number" min="0" max="500" placeholder="<fmt:message key="edition.placeholder.amount"/>" id="amount" name="amount"/>
            <p id = "amount_error"></p>

            <label for="category"><b><fmt:message key="edition.label.category"/></b></label>
            <select name="category" id="category">
                <option value="drink"><fmt:message key="edition.select.drink"/></option>
                <option value="snack"><fmt:message key="edition.select.snack"/></option>
                <option value="salad"><fmt:message key="edition.select.salad"/></option>
                <option value="hot_meal"><fmt:message key="edition.select.hot.meal"/></option>
            </select>
            <p id = "category_error"></p>

            <label for="description"><b><fmt:message key="edition.label.description"/></b></label>
            <input class="form-control" type="text" placeholder="<fmt:message key="edition.placeholder.description"/>" id="description" name="description"/>
            <p id = "description_error"></p>

            <button class="form_submit_btn" type="submit"><fmt:message key="edition.button.edit"/></button>
        </div>
    </form>
</div>
</body>
</html>
