<%@ page language="java" contentType = "text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="login" />
<!DOCTYPE html>
<html>
<header>
    <jsp:include page="/WEB-INF/fragments/header.jsp"/>
</header>
    <body>
            <div class="form-group">
                <c:if test="${sessionScope.unknown_user}">
                    <h2><fmt:message key="login.text.unknown"/></h2>
                </c:if>
                <c:if test="${sessionScope.user_block}">
                    <h2><fmt:message key="login.text.sorry"/></h2>
                </c:if>
                <form action="/controller" method="post">
                    <input type="hidden" name="command" value="log_in">

                    <div class="container">
                        <label for="login"><b><fmt:message key="login.label.login"/></b></label>
                        <input class="form-control" type="text" placeholder="<fmt:message key="login.placeholder.login"/>" id="login" name="login" required>

                        <label for="password"><b><fmt:message key="login.label.password"/></b></label>
                        <input class="form-control" type="password" placeholder="<fmt:message key="login.placeholder.password"/>" id="password" name="password" required>

                        <button class="form_submit_btn" type="submit"><fmt:message key="login.button.submit"/></button>
                    </div>
                </form>
                <hr>
                <form action="/registration" method="post">
                        <button class="form_submit_btn" type="submit"><fmt:message key="login.button.singup"/></button>
                </form>
            </div>
    </body>
</html>