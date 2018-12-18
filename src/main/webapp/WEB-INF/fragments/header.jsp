<%@ page language="java" contentType = "text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="../../css/style.css"/>
    <%--<style><%@include file="/css/style.css"%></style>--%>
</head>
<body>

<div class="navbar">
    <a href="/">Home</a>
    <div class="dropdown">
        <button class="dropbtn">Menu
            <i class="fa fa-caret-down"></i>
        </button>
        <div class="dropdown-content">
            <a href="/controller?command=show_category_products&category=drink">Drink</a>
            <a href="/controller?command=show_category_products&category=snack">Snack</a>
            <a href="/controller?command=show_category_products&category=salad">Salad</a>
            <a href="/controller?command=show_category_products&category=hot_meal">Hot meal</a>
        </div>
    </div>

    <c:if test="${sessionScope.user_role ne 'guest'}">
        <a href="/controller?command=show_orders">Orders</a>
        <a href="/controller?command=show_cart">Cart</a>
    </c:if>
    <c:if test="${sessionScope.user_role eq 'admin'}">
        <a href="/controller?command=show_users">Users</a>
    </c:if>

    <div class="dropdown">
        <button class="dropbtn">Language
            <i class="fa fa-caret-down"></i>
        </button>
        <div class="dropdown-content">
            <a href="">English</a>
            <a href="">Russian</a>
        </div>
    </div>

    <c:if test="${sessionScope.user_role eq 'guest'}">
        <a href="/login">Log in</a>
    </c:if>
    <c:if test="${sessionScope.user_role ne 'guest'}">
        <a href="/controller?command=log_out">Log out</a>
    </c:if>
</div>

</body>
</html>
