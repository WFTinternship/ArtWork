<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>

<c:set var="user" value='<%=request.getAttribute("user")%>'/>
<c:set var="errorMessage" value='<%=request.getAttribute("errorMessage")%>'/>


<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Sign-Up/Login Form</title>
    <link href='https://fonts.googleapis.com/css?family=Titillium+Web:400,300,600' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css">


    <link rel="stylesheet" href="../../resources/css/logInSignUpStyle.css">


</head>

<body>
<div class="form">

    <c:if test="${errorMessage!=null}">
        <h2>${errorMessage}</h2>
    </c:if>
    <ul class="tab-group">
        <li class="tab active"><a href="#signup">Sign Up</a></li>
        <li class="tab"><a href="#login">Log In</a></li>
    </ul>

    <div class="tab-content">
        <div id="signup">
            <h1>Sign Up for Free</h1>
            <form action="/signup" method="post">
                <div class="top-row">
                    <div class="field-wrap">
                        <label>
                            First Name<span class="req"></span>
                        </label>
                        <input name="firstname" type="text" required autocomplete="off"/>
                    </div>
                    <div class="field-wrap">
                        <label>
                            Last Name<span class="req"></span>
                        </label>
                        <input name="lastname" type="text" required autocomplete="off"/>
                    </div>
                </div>
                <div class="field-wrap">
                    <label>
                        Age<span class="req"></span>
                    </label>
                    <input name="age" type="text" required="" autocomplete="off">
                </div>
                <div class="field-wrap">
                    <label>
                        Email Address<span class="req"></span>
                    </label>
                    <input name="email" type="email" required autocomplete="off"/>
                </div>
                <div class="field-wrap">
                    <label>
                        Set A Password<span class="req"></span>
                    </label>
                    <input name="password" type="password" required autocomplete="off"/>
                </div>
                <div class="field-wrap">
                    <label>
                        Re-type Password<span class="req"></span>
                    </label>
                    <input name="password2" type="password" required autocomplete="off"/>
                </div>
                <button type="submit" class="button button-block">Get Started</button>
            </form>
        </div>





        <div id="login">
            <h1>Welcome Back!</h1>
            <form action="/login" method="post">
                <div class="field-wrap">
                    <label>
                        Email Address<span class="req">*</span>
                    </label>
                    <input name="email" type="email" required autocomplete="off"/>
                </div>
                <div class="field-wrap">
                    <label>
                        Password<span class="req">*</span>
                    </label>
                    <input name="password" type="password" required autocomplete="off"/>
                </div>
                <p class="forgot"><a href="#">Forgot Password?</a></p>
                <button class="button button-block">Log In</button>
            </form>
        </div>

    </div><!-- tab-content -->

</div> <!-- /form -->
<script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>

<script src="../../resources/js/logInSignUpIndex.js"></script>

</body>
</html>