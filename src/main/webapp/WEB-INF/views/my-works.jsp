<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>


<c:set var="itemTypes" value='<%=request.getAttribute("itemTypes")%>' />
<c:set var="artistSpecTypes" value='<%=request.getAttribute("artistSpecTypes")%>' />
<c:set var="artistItems" value='<%=request.getAttribute("artistItems")%>' />

<!Doctype html>
<!--[if IE 7 ]>    <html lang="en-gb" class="isie ie7 oldie no-js"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en-gb" class="isie ie8 oldie no-js"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en-gb" class="isie ie9 no-js"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en-gb" class="no-js"> <!--<![endif]-->

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Red Art - Digital Painting</title>

    <meta name="description" content="">
    <meta name="author" content="">

    <!--[if lt IE 9]>
    <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

    <link rel="shortcut icon" href="../../resources/favicon.ico" type="image/x-icon" />

    <!-- **CSS - stylesheets** -->
    <link href="../../resources/css/A.bootstrap.min.css+font-awesome.min.css,Mcc.IDMzkxuERs.css.pagespeed.cf.9_8KzKNf-A.css" rel="stylesheet"/>
    <link id="default-css" rel="stylesheet" href="../../resources/style.css" type="text/css" media="all" />
    <link href="../../resources/css/A.style.css+style-less.css,Mcc.U0a7i6ixff.css.pagespeed.cf.gaKpoO-umx.css" rel="stylesheet"/>





    <!-- **Additional - stylesheets** -->
    <link href="../../resources/css/animations.css" rel="stylesheet" type="text/css" media="all" />
    <link id="shortcodes-css" href="../../resources/css/shortcodes.css" rel="stylesheet" type="text/css" media="all"/>

    <link href="../../resources/css/isotope.css" rel="stylesheet" type="text/css" media="all" />
    <link href="../../resources/css/prettyPhoto.css" rel="stylesheet" type="text/css" />
    <link href="../../resources/css/pace.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" href="../../resources/css/responsive.css" type="text/css" media="all"/>
    <script src="../../resources/js/modernizr.js"></script> <!-- Modernizr -->

    <link id="light-dark-css" href="../../resources/light/light.css" rel="stylesheet" media="all" />

    <!-- **Font Awesome** -->
    <link rel="stylesheet" href="../../resources/css/font-awesome.min.css" type="text/css" />

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
        <div id="header-wrapper" class="dt-sticky-menu"> <!-- **header-wrapper Starts** -->
            <div id="header" class="header">
                <div class="container menu-container">
                    <a class="logo" href="../../resources/index.html"><img alt="Logo" src="../../resources/images/logo.png"></a>

                    <a href="#" class="menu-trigger">
                        <span></span>
                    </a>
                </div>
            </div>

            <nav id="main-menu"><!-- Main-menu Starts -->
                <div id="dt-menu-toggle" class="dt-menu-toggle">
                    Menu
                    <span class="dt-menu-toggle-icon"></span>
                </div>
                <ul class="menu type1"><!-- Menu Starts -->
                    <li class="menu-item-simple-parent"><a href="../../resources/index.html">Home <span class="fa fa-home"></span></a>
                        <ul class="sub-menu">
                            <li><a href="http://www.wedesignthemes.com/html/redart/default">Default</a></li>
                            <li><a href="http://www.wedesignthemes.com/html/redart/menu-overlay">Menu Overlay</a></li>
                            <li><a href="http://www.wedesignthemes.com/html/redart/slide-bar">Slide Bar</a></li>
                            <li><a href="http://www.wedesignthemes.com/html/redart/slider-over-menu">Slider Over Menu</a></li>
                        </ul>
                        <a class="dt-menu-expand">+</a>
                    </li>

                    <li class="menu-item-simple-parent">
                        <a href="../../resources/about.html">About us <span class="fa fa-user-secret"></span></a>
                    </li>
                    <li class="menu-item-simple-parent"><a href="../../resources/gallery.html">Gallery <span class="fa fa-camera-retro"></span></a>
                        <ul class="sub-menu">
                            <li><a href="../../resources/gallery-detail.html">Gallery detail</a></li>
                            <li><a href="../../resources/gallery-detail-with-lhs.html">Gallery-detail-left-sidebar</a></li>
                            <li><a href="../../resources/gallery-detail-with-rhs.html">Gallery-detail-right-sidebar</a></li>
                        </ul>
                        <a class="dt-menu-expand">+</a>
                    </li>
                    <li class="current_page_item menu-item-simple-parent"><a href="../../resources/shop.html">Shop <span class="fa fa-cart-plus"></span></a>
                        <ul class="sub-menu">
                            <li class="current_page_item"><a href="shop-detail.html">Shop Detail</a></li>
                            <li><a href="../../resources/shop-cart.html">Cart Page</a></li>
                            <li><a href="../../resources/shop-checkout.html">Checkout Page</a></li>
                        </ul>
                        <a class="dt-menu-expand">+</a>
                    </li>
                    <li class="menu-item-simple-parent">
                        <a href="../../resources/contact.html">contact <span class="fa fa-map-marker"></span></a>
                    </li>

                </ul> <!-- Menu Ends -->
            </nav> <!-- Main-menu Ends -->
        </div><!-- **header-wrapper Ends** -->
        <div id="main">
            <div class="breadcrumb"><!-- *BreadCrumb Starts here** -->
                <div class="container">
                    <h2>Product <span>Detail</span></h2>
                    <div class="user-summary">
                        <div class="account-links">
                            <a href="account">My Account</a>
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
                                    <ul class="list-unstyled">
                                        <li><a href="account"><i class="fa fa-user"></i> My Account</a></li>
                                        <li><a href="edit-profile"><i class="fa fa-edit"></i> Edit Profile</a></li>
                                        <li><a href="purchase-history"><i class="fa fa-list-alt"></i> Purchase History</a></li>
                                        <li><a href="my-works"><i class="fa fa-list-alt"></i> My ArtWorks </a></li>
                                        <li><a href="additem"><i class="fa fa-list-alt"></i> Add ArtWork </a></li>
                                    </ul>
                                </div>
                            </div>
                            <div class="col-md-9 col-sm-9">
                                <!-- inner main content area -->
                                <div class="inner-main account">
                                    <!-- top heading -->
                                    <h2>Your ArtWorks</h2>

                                        <ul class="products isotope">
                                            <c:forEach items="${artistItems}" var="itemElement">
                                                <li class="product-wrapper dt-sc-one-fifth"> <!-- **product-wrapper - Starts** -->
                                                    <!-- **product-container - Starts** -->
                                                    <div class="product-container">
                                                        <a href="/shop-detail/${itemElement.id}"><div class="product-thumb"> <img src="${itemElement.photoURL}" alt="image"/> </div> </a>
                                                        <!-- **product-title - Starts** -->
                                                        <div class="product-title">
                                                            <a href="#" class="type1 dt-sc-button"> <span class="fa fa-shopping-cart"></span> Edit </a>
                                                            <a href="#" class="type1 dt-sc-button"> <span class="fa fa-unlink"></span> Delete </a>
                                                            <p>You don't take a photograph, Just make it</p>
                                                        </div> <!-- **product-title - Ends** -->
                                                    </div> <!-- **product-container - Ends** -->
                                                    <!-- **product-details - Starts** -->
                                                    <div class="product-details">
                                                        <h5> <a href="shop-detail"> ${itemElement.title} </a> </h5>
                                                        <span class="amount"> $${itemElement.price} </span>
                                                    </div> <!-- **product-details - Ends** -->
                                                </li><!-- **product-wrapper - Ends** -->
                                            </c:forEach>
                                        </ul>
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
<script src="../../resources/js/jquery-1.11.2.min.js" type="text/javascript"></script>

<script type="text/javascript" src="../../resources/js/jquery.isotope.min.js"></script>
<script type="text/javascript" src="../../resources/js/jquery.isotope.perfectmasonry.min.js"></script>

<script type="text/javascript" src="../../resources/js/jquery.dropdown.js"></script>

<script src="../../resources/js/jsplugins.js" type="text/javascript"></script>

<script src="../../resources/js/custom.js"></script>

</body>
</html>