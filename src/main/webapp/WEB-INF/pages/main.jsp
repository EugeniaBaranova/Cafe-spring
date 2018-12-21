<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="header" />
<!DOCTYPE html>
<html>
<head>
    <link href='https://fonts.googleapis.com/css?family=Sofia' rel='stylesheet'>
</head>
<header>
    <jsp:include page="/WEB-INF/fragments/header.jsp"/>
</header>
<body>
    <div class="welcome_title">
        <h1><b><fmt:message key="welcome.title"/>!</b></h1>
    </div>
</body>
</html>
