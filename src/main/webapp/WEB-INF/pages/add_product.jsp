<%@ page language="java" contentType = "text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="header" />
<!DOCTYPE html>
<html>
<header>
    <jsp:include page="/WEB-INF/fragments/header.jsp"/>
</header>
<body>
<div class="form-group">
    <h1>New Product</h1>

    <c:if test="${sessionScope.unsuccessful_addition}">
        <h2>Unsuccessful addition</h2>
    </c:if>

    <form action="/controller" method="POST" name="addition_form" data-language="${language}">
        <input type="hidden" name="command" value="add_product">
        <div class="container">
            <label for="name"><b>Name</b></label>
            <input class="form-control" type="text" placeholder="Enter name" id="name" name="name" required/>
            <p id = "name_error"></p>

            <label for="reference"><b>Image Reference</b></label>
            <input class="form-control" type="text" placeholder="Enter ref" id="reference" name="reference" required/>
            <p id = "reference_error"></p>

            <label for="cost"><b>Cost</b></label>
            <input class="form-control" type="text" placeholder="Enter cost" id="cost" name="cost" required/>
            <p id = "cost_error"></p>

            <label for="amount"><b>Amount</b></label>
            <input class="form-control" type="number" min="0" max="500" placeholder="Enter amount" id="amount" name="amount" required/>
            <p id = "amount_error"></p>

            <label for="category"><b>Category</b></label>
            <input class="form-control" type="text" placeholder="Enter category" id="category" name="category" required/>
            <p id = "category_error"></p>

            <label for="description"><b>Description</b></label>
            <input class="form-control" type="text" placeholder="Enter description" id="description" name="description" required/>
            <p id = "description_error"></p>

            <button class="form_submit_btn" type="submit">Add</button>
        </div>
    </form>
</div>
</body>
</html>
