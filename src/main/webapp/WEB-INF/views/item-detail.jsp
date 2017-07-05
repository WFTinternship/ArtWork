<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>


<c:set var="itemDetail" value='<%=request.getSession().getAttribute("itemDetail")%>'/>
<c:set var="artistItems" value='<%=request.getSession().getAttribute("artistItems")%>'/>
<c:set var="artistInfo" value='<%=request.getSession().getAttribute("artistInfo")%>'/>
<c:set var="user" value='<%=request.getSession().getAttribute("user")%>'/>



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
    <link id="default-css" rel="stylesheet" href="../../resources/style.css" type="text/css" media="all"/>

    <!-- **Additional - stylesheets** -->
    <link href="../../resources/css/animations.css" rel="stylesheet" type="text/css" media="all"/>
    <link id="shortcodes-css" href="../../resources/css/shortcodes.css" rel="stylesheet" type="text/css" media="all"/>
    <link id="skin-css" href="../../resources/skins/red/style.css" rel="stylesheet" media="all"/>
    <link href="../../resources/css/isotope.css" rel="stylesheet" type="text/css" media="all"/>
    <link href="../../resources/css/prettyPhoto.css" rel="stylesheet" type="text/css"/>
    <link href="../../resources/css/pace.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" href="../../resources/css/responsive.css" type="text/css" media="all"/>
    <script src="../../resources/js/modernizr.js"></script> <!-- Modernizr -->

    <link id="light-dark-css" href="../../resources/dark/dark.css" rel="stylesheet" media="all"/>

    <!-- **Font Awesome** -->
    <link rel="stylesheet" href="../../resources/css/font-awesome.min.css" type="text/css"/>

    <%--fancybox--%>
    <script src="//code.jquery.com/jquery-3.2.1.min.js"></script>

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/fancybox/3.0.47/jquery.fancybox.min.css"/>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/fancybox/3.0.47/jquery.fancybox.min.js"></script>

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
        <jsp:include page="header.jsp" />
        <div id="main">
            <div class="breadcrumb"><!-- *BreadCrumb Starts here** -->
                <div class="container">
                    <h2>Product <span>Detail</span></h2>
                    <div class="user-summary">
                        <div class="account-links">
                            <a href="#">My Account</a>
                            <a href="#">Checkout</a>
                        </div>
                        <div class="cart-count">
                            <a href="#">Shopping Bag: 0 items</a>
                            <a href="#">($0.00)</a>
                        </div>
                    </div>
                </div>
            </div><!-- *BreadCrumb Ends here** -->
            <section id="primary" class="content-full-width"><!-- **Primary Starts Here** -->
                <div class="container">
                    <div class="main-title animate" data-animation="pullDown" data-delay="100">
                        <h3> How Art Saved My Life </h3>
                        <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do</p>
                    </div>
                    <div class="cart-wrapper"><!-- *cart-wrapper starts here** -->
                        <div class="dt-sc-three-fifth column first">
                            <div class="cart-thumb">
                                <a data-fancybox="gallery" href="${pageContext.request.contextPath}/${itemDetail.photoURL[0]}">
                                    <img src="${pageContext.request.contextPath}/${itemDetail.photoURL[0]}" alt="" title="Acrylic">
                                </a>
                            </div>
                            <ul class="thumblist">
                                <c:forEach items="${itemDetail.photoURL}" var="itemElement">
                                    <li>
                                        <a href="${pageContext.request.contextPath}/${itemElement}" class="product">
                                            <img src="${pageContext.request.contextPath}/${itemElement}"
                                                alt="" height="150" width="150" title=""></a>
                                    </li>
                                </c:forEach>
                            </ul>
                            <h5>more from this artist</h5>
                            <ul class="thumblist">
                                <c:forEach items="${artistItems}" var="itemElement">
                                    <li>
                                        <a href="/item-detail/${itemElement.id}" class="product"><img
                                                src="${pageContext.request.contextPath}/${itemElement.photoURL[0]}"
                                                alt="" height="150" width="150" title=""></a>
                                    </li>
                                </c:forEach>
                            </ul>
                            <%--<div class="commententries"><!-- *commententries starts here** -->--%>
                            <%--<h4> Comments ( 3 ) </h4>--%>
                            <%--<h6><a href="#"><i class="fa fa-comments-o"></i>Add Comments</a></h6>--%>
                            <%--<ul class="commentlist"><!-- *commentlist starts here** -->--%>
                            <%--<li>--%>
                            <%--<div class="comment">--%>
                            <%--<header class="comment-author">--%>
                            <%--<img title="" alt="image" src="http://placehold.it/85x85&text=Post+Image">--%>
                            <%--</header>--%>
                            <%--<div class="comment-details">--%>
                            <%--<div class="author-name">--%>
                            <%--<a href="#">Callahan James</a>--%>
                            <%--</div>--%>
                            <%--<div class="commentmetadata"><span>/</span> Acrylic Painting</div>--%>
                            <%--<div title="Rated 5.00 out of 5" class="star-rating"><span style="width:80%"></span></div>--%>
                            <%--<div class="comment-body">--%>
                            <%--<div class="comment-content">--%>
                            <%--<p>The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains.</p>--%>
                            <%--<div class="author-metadata">--%>
                            <%--<p><span class="fa fa-comments"></span><a href="#"> 19 </a></p>--%>
                            <%--<p><span class="fa fa-folder-open"> </span><a href="#"> Art</a></p>--%>
                            <%--<p><span class="fa fa-calendar"></span><a href="#"> 05 Apr 2015 </a></p>--%>
                            <%--</div>                                                    --%>
                            <%--</div>--%>
                            <%--</div>--%>
                            <%--</div>--%>
                            <%--</div>--%>
                            <%--<ul class="children">--%>
                            <%--<li>--%>
                            <%--<div class="comment">--%>
                            <%--<header class="comment-author">--%>
                            <%--<img title="" alt="image" src="http://placehold.it/85x85&text=Post+Image">--%>
                            <%--</header>--%>
                            <%--<div class="comment-details">--%>
                            <%--<div class="author-name">--%>
                            <%--<a href="#">Sean Bean</a>--%>
                            <%--</div>--%>
                            <%--<div class="commentmetadata"><span>/</span> Sculpture</div>--%>
                            <%--<div title="Rated 5.00 out of 5" class="star-rating"><span style="width:70%"></span></div>                                                    --%>
                            <%--<div class="comment-body">--%>
                            <%--<div class="comment-content">--%>
                            <%--<p>The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains.</p>--%>
                            <%--<div class="author-metadata">--%>
                            <%--<p><span class="fa fa-comments"></span><a href="#"> 08 </a></p>--%>
                            <%--<p><span class="fa fa-folder-open"> </span><a href="#"> Creative</a></p>--%>
                            <%--<p><span class="fa fa-calendar"></span><a href="#"> 26 Aug 2015 </a></p>--%>
                            <%--</div>--%>
                            <%--</div>--%>
                            <%--</div>--%>
                            <%--</div>--%>
                            <%--</div>--%>
                            <%--</li>--%>
                            <%--</ul>                                    --%>
                            <%--</li>--%>
                            <%--</ul><!-- *commentlist Ends here** -->--%>
                            <%--</div><!-- *commententries Ends here** -->--%>
                        </div>
                        <div class="dt-sc-two-fifth column">
                            <form action="/item-detail/*" method="post">
                                <!-- Author Detail Starts Here -->
                                <%--<div class="post-author-details">--%>
                                <%--<div class="entry-author-image">--%>
                                <%--<img src="http://placehold.it/100x100&text=Thumb" alt="" title=""/>--%>
                                <%--</div>--%>
                                <%--<div class="author-title">--%>
                                <%--<h5><a href="#">James Dean</a></h5>--%>
                                <%--<span>Garland, TX - United States</span>--%>
                                <%--<div class="woocommerce-product-rating">--%>
                                <%--<div title="Rated 4.40 out of 5" class="star-rating"><span--%>
                                <%--style="width:85%"></span></div>--%>
                                <%--<a href="#">( 4 customer reviews )</a>--%>
                                <%--</div>--%>
                                <%--</div>--%>
                                <%--<div class="author-desc">--%>
                                <%--<p>Remaining art are essentially unchanged. It was popularised in the 1960s with a--%>
                                <%--release of Latest sheets containing passages, and more recently with desktop--%>
                                <%--publishing software.</p>--%>
                                <%--</div>--%>
                                <%--</div>--%>
                                <!-- Author Detail Ends Here -->
                                <%--<ul class="cart-thumb-categories"><!-- cart-thumb-categories starts Here -->--%>
                                <%--<li>--%>
                                <%--<a href="#" class="product"><img src="http://placehold.it/100x100&text=Cart+Thumb" alt="" title=""></a>--%>
                                <%--<div class="category-details"> --%>
                                <%--<h6><a href="#"> Canvas Print </a> </h6>--%>
                                <%--<span> $25 </span> --%>
                                <%--</div>                                    --%>
                                <%--</li>--%>
                                <%--<li>--%>
                                <%--<a href="#" class="product"><img src="http://placehold.it/100x100&text=Cart+Thumb" alt="" title=""></a>--%>
                                <%--<div class="category-details"> --%>
                                <%--<h6><a href="#"> photogenic </a> </h6>--%>
                                <%--<span> $30 </span> --%>
                                <%--</div>                                    --%>
                                <%--</li>--%>
                                <%--<li class="last">--%>
                                <%--<a href="#" class="product"><img src="http://placehold.it/100x100&text=Cart+Thumb" alt="" title=""></a>--%>
                                <%--<div class="category-details"> --%>
                                <%--<h6><a href="#"> Oil painting </a> </h6>--%>
                                <%--<span> $28 </span> --%>
                                <%--</div>                                    --%>
                                <%--</li>--%>
                                <%--<li>--%>
                                <%--<a href="#" class="product"><img src="http://placehold.it/100x100&text=Cart+Thumb" alt="" title=""></a>--%>
                                <%--<div class="category-details"> --%>
                                <%--<h6><a href="#"> Acrylic Art </a> </h6>--%>
                                <%--<span> $45 </span> --%>
                                <%--</div>                                    --%>
                                <%--</li>--%>
                                <%--<li>--%>
                                <%--<a href="#" class="product"><img src="http://placehold.it/100x100&text=Cart+Thumb" alt="" title=""></a>--%>
                                <%--<div class="category-details"> --%>
                                <%--<h6><a href="#"> Sketches </a> </h6>--%>
                                <%--<span> $15 </span> --%>
                                <%--</div>                                    --%>
                                <%--</li>--%>
                                <%--<li class="last">--%>
                                <%--<a href="#" class="product"><img src="http://placehold.it/100x100&text=Cart+Thumb" alt="" title=""></a>--%>
                                <%--<div class="category-details"> --%>
                                <%--<h6><a href="#"> Dropper Art </a> </h6>--%>
                                <%--<span> $20 </span> --%>
                                <%--</div>                                    --%>
                                <%--</li>--%>
                                <%--<li>--%>
                                <%--<a href="#" class="product"><img src="http://placehold.it/100x100&text=Cart+Thumb" alt="" title=""></a>--%>
                                <%--<div class="category-details"> --%>
                                <%--<h6><a href="#"> Sculpture </a> </h6>--%>
                                <%--<span> $55 </span> --%>
                                <%--</div>                                    --%>
                                <%--</li>--%>
                                <%--<li>--%>
                                <%--<a href="#" class="product"><img src="http://placehold.it/100x100&text=Cart+Thumb" alt="" title=""></a>--%>
                                <%--<div class="category-details"> --%>
                                <%--<h6><a href="#"> Water Print </a> </h6>--%>
                                <%--<span> $10 </span> --%>
                                <%--</div>                                    --%>
                                <%--</li>                                                                                                --%>
                                <%--</ul><!-- cart-thumb-categories Ends Here -->--%>
                                <div class="project-details">
                                    <ul class="client-details">
                                        <li>
                                            <p><span>Title :</span>${itemDetail.title}</p>
                                        </li>
                                        <li>
                                            <p><span>Artist :</span>${artistInfo.firstName} ${artistInfo.lastName}</p>
                                        </li>
                                        <li>
                                            <p><span>Category :</span>${itemDetail.itemType}</p>
                                        </li>
                                        <li>
                                            <p><span>Description :</span>${itemDetail.description}</p>
                                        </li>
                                        <li>
                                            <p><span>Uploaded :</span>${itemDetail.additionDate}</p>
                                        </li>
                                        <li>
                                            <p><span>Price :</span> $${itemDetail.price}</p>
                                        </li>
                                        <%--<li>--%>
                                        <%--<p><span>Colors :</span><a href="#" class="yellow"></a><a href="#" class="green"></a><a href="#" class="orange"></a><a href="#" class="red"></a></p>--%>
                                        <%--</li>--%>
                                        <%--<li>--%>
                                        <%--<p><span>Sales Sheet :</span>PDF</p>--%>
                                        <%--</li>--%>
                                        <%--<li>--%>
                                        <%--<p>--%>
                                        <%--<span>Tags :</span>--%>
                                        <%--<div class="tagcloud type3">--%>
                                        <%--<a href="#">Sketches</a>--%>
                                        <%--<a href="#">Fashion</a>--%>
                                        <%--<a href="#">Art</a>--%>
                                        <%--<a href="#">Rain</a>--%>
                                        <%--<a href="#">Scupture</a>--%>
                                        <%--<a href="#">Lonely</a>--%>
                                        <%--<a href="#">Oil color</a>--%>
                                        <%--<a href="#">Gallery</a>--%>
                                        <%--<a href="#">Mordern Art</a>                                                                                                                                                --%>
                                        <%--</div>--%>
                                        <%--</p>--%>
                                        <%--</li>                                                                                                                                                --%>
                                    </ul>
                                </div>
                                <c:if test="${itemDetail.status==true}">
                                    <div class="dt-sc-one-fifth column">
                                        <div class="container">
                                            <button type="submit" class="button">Buy Art Work!</button>
                                        </div>
                                    </div>
                                </c:if>
                            </form>
                        </div>
                    </div><!-- *cart-wrapper Ends here** -->
                </div>
            </section><!-- **Primary Ends Here** -->
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
<script src="../../resources/js/jquery-1.11.2.min.js" type="text/javascript"></script>

<script type="text/javascript" src="../../resources/js/jquery.isotope.min.js"></script>
<script type="text/javascript" src="../../resources/js/jquery.isotope.perfectmasonry.min.js"></script>

<script type="text/javascript" src="../../resources/js/jquery.dropdown.js"></script>

<script src="../../resources/js/jsplugins.js" type="text/javascript"></script>

<script src="../../resources/js/custom.js"></script>

</body>
</html>