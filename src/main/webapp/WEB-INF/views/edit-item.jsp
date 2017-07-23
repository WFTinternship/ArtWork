<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>


<c:set var="user" value='<%=session.getAttribute("user")%>'/>
<c:set var="item" value='<%=request.getAttribute("item")%>'/>
<c:set var="message" value='<%=request.getAttribute("message")%>'/>
<c:set var="itemTypes" value='<%=request.getAttribute("itemTypes")%>'/>


<!Doctype html>
<!--[if IE 7 ]> <html lang="en-gb" class="isie ie7 oldie no-js"> <![endif]-->
<!--[if IE 8 ]> <html lang="en-gb" class="isie ie8 oldie no-js"> <![endif]-->
<!--[if IE 9 ]> <html lang="en-gb" class="isie ie9 no-js"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!-->
<html lang="en-gb" class="no-js"> <!--<![endif]-->

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Red Art - Digital Painting</title>

    <meta name="description" content="">
    <meta name="author" content="">

    <!--[if lt IE 9]>
    <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

    <link rel="shortcut icon" href="../../resources/favicon.ico" type="image/x-icon"/>

    <!-- **CSS - stylesheets** -->
    <link href="../../resources/css/A.bootstrap.min.css+font-awesome.min.css,Mcc.IDMzkxuERs.css.pagespeed.cf.9_8KzKNf-A.css"
          rel="stylesheet"/>
    <link id="default-css" rel="stylesheet" href="../../resources/style.css" type="text/css" media="all"/>
    <link href="../../resources/css/A.style.css+style-less.css,Mcc.U0a7i6ixff.css.pagespeed.cf.gaKpoO-umx.css"
          rel="stylesheet"/>
    <!-- **Additional - stylesheets** -->
    <link href="../../resources/css/animations.css" rel="stylesheet" type="text/css" media="all"/>
    <link id="shortcodes-css" href="../../resources/css/shortcodes.css" rel="stylesheet" type="text/css" media="all"/>

    <link href="../../resources/css/isotope.css" rel="stylesheet" type="text/css" media="all"/>
    <link href="../../resources/css/prettyPhoto.css" rel="stylesheet" type="text/css"/>
    <link href="../../resources/css/pace.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" href="../../resources/css/responsive.css" type="text/css" media="all"/>
    <script src="../../resources/js/modernizr.js"></script> <!-- Modernizr -->

    <link id="light-dark-css" href="../../resources/light/light.css" rel="stylesheet" media="all"/>

    <!-- **Font Awesome** -->
    <link rel="stylesheet" href="../../resources/css/font-awesome.min.css" type="text/css"/>

</head>

<body>
<div class="loader-wrapper">
    <div id="large-header" class="large-header">
        <h1 class="loader-title"><span>Red</span> Art</h1>
    </div>
</div>
<!-- **Wrapper** -->
<div class="wrapper">
    <div class="inner-wrapper">
        <jsp:include page="header.jsp"/>
        <div id="main">
            <div class="breadcrumb"><!-- *BreadCrumb Starts here** -->
                <div class="container">
                    <h2>Product <span>Detail</span></h2>
                    <div class="user-summary">
                        <div class="account-links">
                            <a href="${pageContext.request.contextPath}/account">My Account</a>
                            <a href="#">Checkout</a>
                        </div>
                        <div class="cart-count">
                            <a href="#">Shopping Bag: 0 items</a>
                            <a href="#">($0.00)</a>
                        </div>
                    </div>
                </div>
            </div><!-- *BreadCrumb Ends here** -->
            <!-- main content -->
            <div class="main-content">
                <div class="container">

                    <!-- inner page content -->
                    <div class="inner-content">
                        <div class="row">
                            <div class="col-md-3 col-sm-3 hidden-xs">
                                <!-- inner navigation menu -->
                                <div class="navi">
                                    <!-- heading -->
                                    <h3>My Menu</h3>
                                    <!-- list -->
                                    <jsp:include page="chooser.jsp"/>
                                </div>
                            </div>
                            <div class="col-md-9 col-sm-9">
                                <!-- inner main content area -->
                                <div class="inner-main account">
                                    <c:choose>
                                        <c:when test="${message!=null}">
                                            <header class="cd-main-header">
                                                <h3 style="color:red;">${message}</h3>
                                            </header>
                                        </c:when>
                                        <c:otherwise>
                                            <header class="cd-main-header">
                                                <h2>Edit Item Info</h2>
                                            </header>
                                        </c:otherwise>
                                    </c:choose>
                                    <div class="edit-form">
                                        <div class="row">
                                            <div class="col-md-6 col-sm-6">
                                                <!-- edit personal details -->
                                                <form role="form"
                                                      action="${pageContext.request.contextPath}/edit-item/${item.id}"
                                                      method="post" enctype="multipart/form-data">
                                                    <div class="form-group">
                                                        <label for="exampleInput1">Title</label>
                                                        <input type="text" name="title" class="form-control"
                                                               id="exampleInput1" value="${item.title}" required>
                                                    </div>
                                                    <div class="form-group">
                                                        <label for="exampleInput111">Description</label>
                                                        <input type="text" name="description" class="form-control"
                                                               id="exampleInput111" value="${item.description}" required>
                                                    </div>
                                                    <div class="form-group">
                                                        <label for="exampleInput4">Price</label>
                                                        <input type="text" name="price" class="form-control"
                                                               id="exampleInput4" value="${item.price}" required>
                                                    </div>

                                                    <div class="form-group">
                                                        <label>
                                                            <select class="shop-dropdown" name="itemType">
                                                                <option value="${item.itemType}" selected
                                                                        class="fa fa-eyedropper">${item.itemType}
                                                                </option>
                                                                <c:forEach items="${itemTypes}" var="element">
                                                                    <c:if test="${element.type != item.itemType}">
                                                                        <option value="${element.type}"
                                                                                class="fa fa-eyedropper">${element.type}
                                                                        </option>
                                                                    </c:if>
                                                                </c:forEach>
                                                            </select>
                                                        </label>
                                                    </div>
                                                    <div class="row">
                                                        <c:forEach items="${item.photoURL}" var="element">
                                                            <div class="col-md-6 col-sm-6">
                                                                <div class="form-group">
                                                                        <%--<form action="${pageContext.request.contextPath}/edit-profile" method="post" enctype="multipart/form-data">--%>
                                                                    <label for="imageUpload">Change Item</label>
                                                                    <input type="file" id="imageUpload" name="image"/>
                                                                        <%--<img class="img-responsive user" src="data:image/jpeg;base64,${image}" alt=""/>--%>
                                                                    <img src="../../${element}" id="imagePreview" alt="" width="200px"/><br/>
                                                                        <%--<button type="submit" class="btn btn-warning">Apply</button>--%>
                                                                        <%--</form>--%>
                                                                </div>
                                                            </div>
                                                        </c:forEach>
                                                    </div>

                                                    <div class="row col-md-6 col-sm-6">
                                                        <button type="submit" class="btn btn-warning">
                                                            Update Personal Details
                                                        </button>
                                                    </div>
                                                </form>
                                            </div>

                                            <%--<input type="hidden" id="thisField1" name="editBlock" value="persInfo">--%>


                                            <%--<c:if test="${user['class'].simpleName eq 'Artist'}">--%>
                                            <%--<div class="form-group">--%>
                                            <%--<select class="shop-dropdown" name="specialization">--%>
                                            <%--<option value="${user.specialization}" selected class="fa fa-eyedropper">${user.specialization}</option>--%>
                                            <%--<c:forEach items="${artistSpecTypes}" var="element">--%>
                                            <%--<c:if test="${element.type != user.specialization}">--%>
                                            <%--<option value="${element.type}" class="fa fa-eyedropper">${element.type}</option>--%>
                                            <%--</c:if>--%>
                                            <%--</c:forEach>--%>
                                            <%--</select>--%>
                                            <%--</div>--%>
                                            <%--</c:if>--%>
                                            <%--<c:if test="${user['class'].simpleName eq 'Artist'}">--%>
                                            <%--<div class="col-md-6 col-sm-6">--%>
                                            <%--<div class="form-group">--%>
                                            <%--<form action="${pageContext.request.contextPath}/edit-profile" method="post" enctype="multipart/form-data">--%>
                                            <%--<label for="imageUpload">Choose Avatar</label>--%>
                                            <%--<input type="file" id="imageUpload" name="image"  />--%>
                                            <%--<img src="" id="imagePreview" alt="" width="200px"/><br/>--%>

                                            <%--<input type="hidden" id="thisField2" name="editBlock" value="avatar">--%>
                                            <%--<button type="submit" class="btn btn-warning">Apply</button>--%>
                                            <%--</form>--%>
                                            <%--</div>--%>
                                            <%--</div>--%>
                                            <%--</c:if>--%>
                                            <%--<div class="col-md-6 col-sm-6">--%>
                                            <%--<!-- Password details -->--%>
                                            <%--<form role="form" action="${pageContext.request.contextPath}/edit-profile" method="post" enctype="multipart/form-data">--%>
                                            <%--<div class="form-group">--%>
                                            <%--<label for="exampleInput31">Old Password</label>--%>
                                            <%--<input type="password" name="oldpassword" class="form-control" id="exampleInput31" placeholder="Old Password">--%>
                                            <%--</div>--%>
                                            <%--<div class="form-group">--%>
                                            <%--<label for="exampleInput32">New Password</label>--%>
                                            <%--<input type="password" name="newpassword" class="form-control" id="exampleInput32" placeholder="New Password">--%>
                                            <%--</div>--%>
                                            <%--<div class="form-group">--%>
                                            <%--<label for="exampleInput33">Re - Type Password</label>--%>
                                            <%--<input type="password" name="retypepassword" class="form-control" id="exampleInput33" placeholder="New Password">--%>
                                            <%--</div>--%>
                                            <%--<input type="hidden" id="thisField3" name="editBlock" value="password">--%>
                                            <%--<button type="submit" class="btn btn-warning">Update Password</button>--%>
                                            <%--</form>--%>
                                            <%--</div>--%>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- inner page content end -->
                </div>
            </div>
            <!-- main content end -->
            <footer id="footer" class="animate" data-animation="fadeIn" data-delay="100">
                <div class="container">
                    <div class="copyright">
                        <ul class="footer-links">
                            <li><a href="#">Contact us</a></li>
                            <li><a href="#">Privacy policy</a></li>
                            <li><a href="#">Terms of use</a></li>
                            <li><a href="#">Faq</a></li>
                        </ul>
                        <ul class="payment-options">
                            <li><a href="#" class="fa fa-cc-amex"></a></li>
                            <li><a href="#" class="fa fa-cc-mastercard"></a></li>
                            <li><a href="#" class="fa fa-cc-visa"></a></li>
                            <li><a href="#" class="fa fa-cc-discover"></a></li>
                            <li><a href="#" class="fa fa-cc-paypal"></a></li>
                        </ul>
                        <p>© 2015 <a href="#">RED ART</a>. All rights reserved.</p>
                    </div>
                </div>
            </footer>
        </div><!-- Main Ends Here-->
    </div>
</div><!-- **Wrapper Ends** -->

<!-- **jQuery** -->

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
<script src="../../resources/js/jquery-1.11.2.min.js" type="text/javascript"></script>

<script type="text/javascript" src="../../resources/js/jquery.isotope.min.js"></script>
<script type="text/javascript" src="../../resources/js/jquery.isotope.perfectmasonry.min.js"></script>

<script type="text/javascript" src="../../resources/js/jquery.dropdown.js"></script>

<script src="../../resources/js/jsplugins.js" type="text/javascript"></script>

<script src="../../resources/js/custom.js"></script>

</body>
</html>
