<%@ page language="java" contentType = "text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<header>
    <jsp:include page="/WEB-INF/fragments/header.jsp"/>
</header>
    <body>



            <div class="login_form">
                <c:if test="${sessionScope.unknown_user}">
                    <h2>Unknown user</h2>
                </c:if>
                <c:if test="${sessionScope.user_block}">
                    <h2>Sorry, you are blocked</h2>
                </c:if>
                <form action="/controller" method="post">
                    <input type="hidden" name="command" value="log_in">

                    <div class="container">
                        <label for="login"><b>Login</b></label>
                        <input class="form-control" type="text" placeholder="Enter Login" id="login" name="login" required>

                        <label for="psw"><b>Password</b></label>
                        <input class="form-control" type="password" placeholder="Enter Password" id="psw" name="password" required>

                        <button class="login_btn" type="submit">Login</button>
                    </div>
                </form>

                <hr>

                <form action="/registration" method="post">
                        <button class="login_btn" type="submit">Sing up</button>
                </form>
            </div>

<%--<form action="/registration" method="post">
    <input type="submit" value="Sing up">
</form>--%>




    </body>
</html>