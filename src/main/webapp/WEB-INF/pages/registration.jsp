<%@ page language="java" contentType = "text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>

<header>
    <jsp:include page="/WEB-INF/fragments/header.jsp"/>

</header>
<body>
<c:if test="${sessionScope.unsuccessful_registration}">
    <h2>Unsuccessful Registration</h2>
</c:if>

<form action="/controller" method="POST">

    <input type="hidden" name="command" value="registration">

    <label title="Name">
        <input type="text" name="name"/>
    </label>
    <label title="Login">
        <input type="text" name="login"/>
    </label>
    <label title="Email">
        <input type="email" name="email"/>
    </label>
    <label title="Password">
        <input type="password" name="password"/>
    </label>
    <input type="submit" value="Sing up">
</form>
</body>
