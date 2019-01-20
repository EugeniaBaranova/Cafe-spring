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
    <title>Users</title>
</head>
<header>
    <jsp:include page="/WEB-INF/fragments/header.jsp"/>
</header>
<body>

<table id="customers">
    <tr>
        <th>Id</th>
        <th>Name</th>
        <th>Email</th>
        <th>Login</th>
        <th>Loyalty points</th>
        <th>Blocked</th>
        <th>Role</th>
    </tr>
    <tr>
        <td>23</td>
        <td>Alfreds Futterkiste</td>
        <td>Alfreds Futterkiste</td>
        <td>Alfreds Futterkiste</td>
        <td>0</td>
        <td>false</td>
        <td>USER</td>
    </tr>
    <tr>
        <td>23</td>
        <td>Alfreds Futterkiste</td>
        <td>Alfreds Futterkiste</td>
        <td>Alfreds Futterkiste</td>
        <td>0</td>
        <td>false</td>
        <td>USER</td>
    </tr>
    <tr>
        <td>23</td>
        <td>Alfreds Futterkiste</td>
        <td>Alfreds Futterkiste</td>
        <td>Alfreds Futterkiste</td>
        <td>0</td>
        <td>false</td>
        <td>USER</td>
    </tr>
    <tr>
        <td>23</td>
        <td>Alfreds Futterkiste</td>
        <td>Alfreds Futterkiste</td>
        <td>Alfreds Futterkiste</td>
        <td>0</td>
        <td>false</td>
        <td>USER</td>
    </tr>
</table>
</body>
</html>
