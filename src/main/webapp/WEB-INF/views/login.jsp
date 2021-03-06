<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>

<c:set var="user" value='<%=session.getAttribute("user")%>'/>
<c:set var="message" value='<%=request.getAttribute("message")%>'/>


<!doctype html>
<html lang="en" class="no-js">

<!-- Mirrored from codyhouse.co/demo/animated-sign-up-flow/ by HTTrack Website Copier/3.x [XR&CO'2014], Fri, 16 Jun 2017 12:52:09 GMT -->
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link href='https://fonts.googleapis.com/css?family=Open+Sans:400,300,700,600' rel='stylesheet' type='text/css'>

    <link rel="stylesheet" href="../../resources/css/signUp-reset.css"> <!-- CSS reset -->
    <link rel="stylesheet" href="../../resources/css/logIn-style.css"> <!-- Resource style -->
    <script src="../../resources/js/signUp-modernizr.js"></script> <!-- Modernizr -->

    <title>Welcome!</title>
</head>
<body>
<header class="cd-main-header">
    <c:choose>
        <c:when test="${message!=null}">
            <h2 style="color:red;">${message}</h2>
        </c:when>
        <c:otherwise>
            <h1>Log In</h1>
        </c:otherwise>
    </c:choose>
</header>
<!--
    you can substitue the span of reauth email for a input with the email and
    include the remember me checkbox
    -->
<div class="container">
    <div class="card card-container">
        <!-- <img class="profile-img-card" src="//lh3.googleusercontent.com/-6V8xOA6M7BA/AAAAAAAAAAI/AAAAAAAAAAA/rzlHcD0KYwo/photo.jpg?sz=120" alt="" /> -->
        <img id="profile-img" class="profile-img-card" src="//ssl.gstatic.com/accounts/ui/avatar_2x.png"/>
        <p id="profile-name" class="profile-name-card"></p>
        <form class="form-signin" action="${pageContext.request.contextPath}/loginProcess" method="post">
            <span id="reauth-email" class="reauth-email"></span>
            <input type="email" id="inputEmail" class="form-control" placeholder="Email address" name="email" required
                   autofocus>
            <input type="password" id="inputPassword" class="form-control" placeholder="Password" name="password"
                   required>
            <div id="remember" class="checkbox">
                <label>
                    <input type="checkbox" value="remember-me"> Remember me
                </label>
            </div>
            <button class="btn btn-lg btn-primary btn-block btn-signin" type="submit">Sign in</button>
        </form><!-- /form -->
        <%--<a href="#" class="forgot-password">--%>
            <%--Forgot the password?--%>
        <%--</a>--%>
    </div><!-- /card-container -->
</div><!-- /container -->

<script src="../../resources/js/jquery-2.1.4.js"></script>
<script src="../../resources/js/signUp-velocity.min.js"></script>
<script src="../../resources/js/signUp-main-min.js"></script> <!-- Resource jQuery -->
<script>
    (function (i, s, o, g, r, a, m) {
        i['GoogleAnalyticsObject'] = r;
        i[r] = i[r] || function () {
                (i[r].q = i[r].q || []).push(arguments)
            }, i[r].l = 1 * new Date();
        a = s.createElement(o),
            m = s.getElementsByTagName(o)[0];
        a.async = 1;
        a.src = g;
        m.parentNode.insertBefore(a, m)
    })(window, document, 'script', '../../../www.google-analytics.com/analytics.js', 'ga');

    ga('create', 'UA-48014931-1', 'codyhouse.co');
    ga('send', 'pageview');

    jQuery(document).ready(function ($) {
        $('.close-carbon-adv').on('click', function (event) {
            event.preventDefault();
            $('#carbonads-container').hide();
        });
    });
</script>
</body>

<!-- Mirrored from codyhouse.co/demo/animated-sign-up-flow/ by HTTrack Website Copier/3.x [XR&CO'2014], Fri, 16 Jun 2017 12:52:12 GMT -->
</html>