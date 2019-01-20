<%@ page language="java" contentType = "text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="add_product" />
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8"/>
    <title><fmt:message key="addition.title"/></title>
</head>
<header>
    <jsp:include page="/WEB-INF/fragments/header.jsp"/>
</header>
<body>
<div class="form-group">
    <h1><fmt:message key="addition.new.product"/></h1>
    <c:if test="${sessionScope.unsuccessful_addition}">
        <h2><fmt:message key="addition.text.unsuccessful"/></h2>
    </c:if>
    <c:forEach var="error" items="${sessionScope.registration_errors}">
        <p class="error_message"><fmt:message key="${error.getMessage()}"/></p>
    </c:forEach>

    <form action="/image_content" method="POST" name="addition_form" enctype="multipart/form-data" data-language="${language}">
        <input type="hidden" name="command" value="add_product">
        <div class="container">
            <label for="image"><b><fmt:message key="addition.label.image"/></b></label>
            <input class="form-control" type="file" accept=".jpg" id="image" name="image" required/>
            <p id = "image_error"></p>

            <label for="name"><b><fmt:message key="addition.label.name"/></b></label>
            <input class="form-control" type="text" placeholder="<fmt:message key="addition.placeholder.name"/>" id="name" name="name" required/>
            <p id = "name_error"></p>

            <label for="cost"><b><fmt:message key="addition.label.cost"/></b></label>
            <input class="form-control" type="text" placeholder="<fmt:message key="addition.placeholder.cost"/>" id="cost" name="cost" required/>
            <p id = "cost_error"></p>

            <label for="amount"><b><fmt:message key="addition.label.amount"/></b></label>
            <input class="form-control" type="number" min="0" max="500" placeholder="<fmt:message key="addition.placeholder.amount"/>" id="amount" name="amount" required/>
            <p id = "amount_error"></p>

            <label for="category"><b><fmt:message key="addition.label.category"/></b></label>
            <select name="category" id="category" required>
                <option value="drink"><fmt:message key="addition.select.drink"/></option>
                <option value="snack"><fmt:message key="addition.select.snack"/></option>
                <option value="salad"><fmt:message key="addition.select.salad"/></option>
                <option value="hot_meal"><fmt:message key="addition.select.hot.meal"/></option>
            </select>
            <p id = "category_error"></p>

            <label for="description"><b><fmt:message key="addition.label.description"/></b></label>
            <input class="form-control" type="text" placeholder="<fmt:message key="addition.placeholder.description"/>" id="description" name="description" required/>
            <p id = "description_error"></p>

            <button class="form_submit_btn" type="submit"><fmt:message key="addition.button.add"/></button>
        </div>
    </form>
    <hr>
    <form action="/controller" method="POST" name="addition_form" data-language="${language}">
        <input type="hidden" name="command" value="add_product_via_json">
        <div class="container">
            <button class="form_submit_btn" type="submit"><fmt:message key="addition.button.use.json"/></button>

            <label for="json"><b><fmt:message key="addition.label.json"/></b></label>
            <input class="form-control" type="file" accept=".json" id="json" name="json" required/>
            <p id = "json_error"></p>
        </div>
    </form>
</div>
</body>
</html>
