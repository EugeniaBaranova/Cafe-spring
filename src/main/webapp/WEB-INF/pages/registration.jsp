<%@ page language="java" contentType = "text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="registration" />
<!DOCTYPE html>
<html>
<head>
    <script src="js\validator.js" type="text/javascript"></script>
</head>
<header>
    <jsp:include page="/WEB-INF/fragments/header.jsp"/>
</header>
<body>
<div class="form-group">
    <h1><fmt:message key="registration.text.registration"/></h1>
    <c:if test="${sessionScope.unsuccessful_registration}">
        <h2><fmt:message key="registration.text.unsuccessful"/></h2>
    </c:if>
    <form action="/controller" method="POST" name="registration_form" onsubmit="return validateForm()" id="reg_form" data-language="${language}">
        <input type="hidden" name="command" value="registration">
        <div class="container">
            <label for="name"><b><fmt:message key="registration.label.name"/></b></label>
            <input class="form-control" type="text" placeholder="<fmt:message key="registration.placeholder.name"/>" id="name" name="name" required/>
            <p id = "name_error"></p>

            <label for="login"><b><fmt:message key="registration.label.login"/></b></label>
            <input class="form-control" type="text" placeholder="<fmt:message key="registration.placeholder.login"/>" id="login" name="login" required/>
            <p id = "login_error"></p>

            <label for="email"><b><fmt:message key="registration.label.email"/></b></label>
            <input class="form-control" type="email" placeholder="<fmt:message key="registration.placeholder.email"/>" id="email" name="email" required/>
            <p id = "email_error"></p>

            <label for="password"><b><fmt:message key="registration.label.password"/></b></label>
            <input class="form-control" type="password" placeholder="<fmt:message key="registration.placeholder.password"/>" id="password" name="password" required/>
            <p id = "password_error"></p>

            <button class="form_submit_btn" type="submit"><fmt:message key="registration.button.singup"/></button>
        </div>
    </form>
</div>
</body>
