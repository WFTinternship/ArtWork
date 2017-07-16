<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>

<c:set var="user" value='<%=session.getAttribute("user")%>' />




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
    
    <link rel="shortcut icon" href="../../resources/favicon.ico" type="../../resources/image/x-icon" />
    
    <!-- **CSS - stylesheets** -->
	<link id="default-css" rel="stylesheet" href="../../resources/style.css" type="text/css" media="all" />
	
    <!-- **Additional - stylesheets** -->
    <link href="../../resources/css/animations.css" rel="stylesheet" type="text/css" media="all" />
	<link id="shortcodes-css" href="../../resources/css/shortcodes.css" rel="stylesheet" type="text/css" media="all"/>
    <link id="skin-css" href="../../resources/skins/red/style.css" rel="stylesheet" media="all" />
    <link href="../../resources/css/isotope.css" rel="stylesheet" type="text/css" media="all" />
    <link href="../../resources/css/prettyPhoto.css" rel="stylesheet" type="text/css" />
    <link href="../../resources/css/pace.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" href="../../resources/css/responsive.css" type="text/css" media="all"/>

	<link id="light-dark-css" href="../../resources/dark/dark.css" rel="stylesheet" media="all" />

    <!-- **Font Awesome** -->
	<link rel="stylesheet" href="../../resources/css/font-awesome.min.css" type="text/css" />
        
	<script src="../../resources/js/modernizr.js"></script> <!-- Modernizr -->
    

    
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
                    <h2>about <span>us</span></h2>
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
			<section id="primary" class="content-full-width"> <!-- **Primary Starts Here** -->            
                <div class="container">
                	<div class="main-title animate" data-animation="pullDown" data-delay="100">
                        <h3> About Art </h3>
                        <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do</p>
                    </div>
                    <div class="dt-sc-service-content">
                    	<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore exercitation ullamco laboris nisi 
ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate 
velit esse cillum dolore </p>
                    </div>
                    <div class="dt-sc-hr-invisible"></div>
                    <div class="service-grid">
                        <div class="dt-sc-one-half column first animate" data-animation="fadeInDown" data-delay="100">
                        	<img src="images/about-img.png" alt="" title="">
                        </div>
                        <div class="dt-sc-one-half column">
                        	<div class="dt-sc-icon-content-wrapper"><!-- *dt-sc-icon-content-wrapper Starts here** -->
                                <div class="dt-sc-one-half column first">
                                    <div class="dt-sc-ico-content animate" data-animation="fadeInRight" data-delay="100">
                                        <h6>About Me</h6>
                                        <p><span>Name:</span>Darren Tiler</p>
                                        <p><span>Age:</span>40</p>
                                        <p><span>Location:</span>New York / USA</p>
                                        <div class="dt-sc-hr-invisible-very-small"></div>
                                        <p>Lorem ipsum dolor sit amet elit elit elit elit, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.</p>
                                    </div>
                                </div>
                                <div class="dt-sc-one-half column dt-sc-icon-wrapper">
                                    <div class="dt-sc-icon"><i class="fa fa-user-secret animate" data-animation="fadeInLeft" data-delay="100"></i></div>
                                </div>
                            </div><!-- *dt-sc-icon-content-wrapper Ends here** -->
                        </div>
                        <div class="dt-sc-one-half column">
                        	<div class="dt-sc-icon-content-wrapper left"><!-- *dt-sc-icon-content-wrapper Starts here** -->
                                <div class="dt-sc-one-half column first dt-sc-icon-wrapper">
                                    <div class="dt-sc-icon"><i class="fa fa-paint-brush animate" data-animation="fadeInRight" data-delay="100"></i></div>
                                </div>
                                <div class="dt-sc-one-half column">
                                    <div class="dt-sc-ico-content animate" data-animation="fadeInLeft" data-delay="100">
                                        <h6>exhibitions</h6>
                                        <p><span>London:</span> Jan 2nd to Feb 12th</p>
                                        <p><span>France:</span> Mar 5th to Apr 18th</p>
                                        <p><span>Germany:</span> Sep 22nd to Nov 1st</p>
                                        <p><span>Australia:</span> Nov 21st to Dec 25th</p>
                                        <p><span>Rome:</span> Vacation</p>
                                    </div>
                                </div>
                            </div><!-- *dt-sc-icon-content-wrapper Ends here** -->
                        </div>
                        <div class="dt-sc-one-half column">
                        	<div class="dt-sc-icon-content-wrapper"><!-- *dt-sc-icon-content-wrapper Starts here** -->
                                <div class="dt-sc-one-half column first">
                                    <div class="dt-sc-ico-content animate" data-animation="fadeInRight" data-delay="100">
                                        <h6>Get in touch</h6>
                                        <p><i class="fa fa-map-marker"></i>109, KimAvenue, Baltimore, USA</p>
                                        <p><i class="fa fa-phone"></i> +22 004 324 1124 </p>
                                        <p><i class="fa fa-envelope"></i><a href="#">RedArt@gmail.com</a></p>
                                        <div class="dt-sc-hr-invisible-very-small"></div>
                                        <h6>Follow us on</h6>
                                        <ul class="type3 dt-sc-social-icons">
                                            <li class="twitter"><a href="#"> <i class="fa fa-twitter"></i> </a></li>
                                            <li class="facebook"><a href="#"> <i class="fa fa-facebook"></i> </a></li>
                                            <li class="google"><a href="#"> <i class="fa fa-google"></i> </a></li>
                                            <li class="dribbble"><a href="#"> <i class="fa fa-dribbble"></i> </a></li>
                                        </ul>                                    
                                    </div>
                                </div>
                                <div class="dt-sc-one-half column dt-sc-icon-wrapper">
                                    <div class="dt-sc-icon"><i class="fa fa-whatsapp animate" data-animation="fadeInLeft" data-delay="100"></i></div>
                                </div>
                            </div><!-- *dt-sc-icon-content-wrapper Ends here** -->
                        </div>
                    </div>
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
    <script src="../../resources/js/jsplugins.js" type="text/javascript"></script>
    
	<script src="../../resources/js/controlpanel.js" type="text/javascript"></script>

	<script src="../../resources/js/custom.js"></script>
        
</body>
</html>
