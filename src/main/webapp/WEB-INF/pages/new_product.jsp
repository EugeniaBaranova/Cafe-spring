<%@ page language="java" contentType = "text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="login" />
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8"/>
</head>
<header>
    <jsp:include page="/WEB-INF/fragments/header.jsp"/>
</header>
<body>
<div class="form-group">
    <form action="/controller" method="POST" name="addition_form" data-language="${language}">
        <input type="hidden" name="command" value="add_product_via_json">
        <div class="container">
            <button class="form_submit_btn" type="submit">Add using JSON (recommended for multiple addition)</button>

            <label for="json"><b>JSON</b></label>
            <input class="form-control" type="file" accept=".json" id="json" name="json" required/>
            <p id = "reference_error"></p>
        </div>
    </form>
    <hr>
    <form action="/add_product" method="post">
        <button class="form_submit_btn" type="submit">Add using form</button>
    </form>
</div>
</body>
</html>