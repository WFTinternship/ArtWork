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
    
    <link rel="shortcut icon" href="../../resources/favicon.ico" type="image/x-icon" />
    
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
	<script src="../../resources/js/modernizr.js"></script> <!-- Modernizr -->
    
	<link id="light-dark-css" href="../../resources/dark/dark.css" rel="stylesheet" media="all" />

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
        <jsp:include page="header.jsp" />
        <div id="main">
        	<%--<div class="breadcrumb"><!-- *BreadCrumb Starts here** -->--%>
                <%--<div class="container">--%>
                    <%--<h2>Contact <span>Us</span></h2>--%>
                    <%--<div class="user-summary">--%>
                    	<%--<div class="account-links">--%>
                        	<%--<a href="#">My Account</a>--%>
                            <%--<a href="#">Checkout</a>--%>
                        <%--</div>--%>
                        <%--<div class="cart-count">--%>
                        	<%--<a href="#">Shopping Bag: 0 items</a> --%>
                            <%--<a href="#">($0.00)</a>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                <%--</div>--%>
        	<%--</div><!-- *BreadCrumb Ends here** -->--%>
            <section id="primary" class="content-full-width"><!-- **Primary Starts Here** -->
				<div class="fullwidth-section"><!-- Full-width section Starts Here -->
                	<div class="container">
						<div class="main-title animate" data-animation="pullDown" data-delay="100">
                            <h3> Contact </h3>
                            <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do</p>
                        </div>                    	
                    </div>
                    <div class="contact-section"><!-- **contact-section Starts Here** -->
                        <div id="contact_map" class="animate" data-animation="fadeInRight" data-delay="100"></div>
                        <div class="dt-sc-contact-info animate" data-animation="fadeInLeft" data-delay="100">
                            <h3>Artist Info</h3>
                            <div class="dt-sc-contact-details"><span class="fa fa-map-marker"></span> Address: No-58 A, East Madison St, Baltimore, MD, USA </div>
                            <div class="dt-sc-contact-details"><span class="fa fa-tablet"></span> Phone: +1 200 258 2145 </div>
                            <div class="dt-sc-contact-details"><span class="fa fa-paperclip"></span> Fax: +1 100 458 2345 </div>
                            <div class="dt-sc-contact-details"><span class="fa fa-envelope"></span> Email: <a href="mailto:yourname@somemail.com">yourname@somemail.com</a> </div>
                            <div class="dt-sc-contact-details"><span class="fa fa-globe"></span> Web: <a href="http://wedesignthemes.com">http://wedesignthemes.com</a> </div>
                            <ul class="type3 dt-sc-social-icons">
                                <li class="twitter"><a href="#"> <i class="fa fa-twitter"></i> </a></li>
                                <li class="facebook"><a href="#"> <i class="fa fa-facebook"></i> </a></li>
                                <li class="google"><a href="#"> <i class="fa fa-google"></i> </a></li>
                                <li class="dribbble"><a href="#"> <i class="fa fa-dribbble"></i> </a></li>
                                <li class="pinterest"><a href="#"> <i class="fa fa-pinterest"></i> </a></li>
                                <li class="linkedin"><a href="#"> <i class="fa fa-linkedin"></i> </a></li>
                                <li class="flickr"><a href="#"> <i class="fa fa-flickr"></i> </a></li>
                                <li class="tumblr"><a href="#"> <i class="fa fa-tumblr"></i> </a></li>
                            </ul>                        
                        </div>
                    </div><!-- **contact-section Ends Here** -->
                    <div class="dt-sc-hr-invisible-toosmall"></div>
                    <div class="container">
                    	<div class="dt-sc-three-fourth column first animate" data-animation="fadeInDown" data-delay="100">
                            <h3>Get in Touch</h3>
                            <form class="enquiry-form" action="php/send.php" method="post" novalidate="novalidate" name="enqform" >
                                <div class="column dt-sc-one-third first">
                                    <p class="input-text">
                                       <input class="input-field" type="text" required="" name="txtname" title="Enter your Name" placeholder="Name *"/>
                                    </p>
                                </div>
                                <div class="column dt-sc-one-third">
                                    <p class="input-text">
                                       <input class="input-field" type="email" required="" autocomplete="off" name="txtemail" title="Enter your valid id" placeholder="Email *"/>
                                    </p>
                                </div>
                                <div class="column dt-sc-one-third">
                                    <p class="input-text">
                                       <input class="input-field" type="text" autocomplete="off" placeholder="Website"/>
                                    </p>
                                </div>
                                <p class="input-text"> 
                                    <textarea class="input-field" required="" rows="3" cols="5" name="txtmessage" title="Enter your Message" placeholder="Message *"></textarea>
                                </p>
                                
                                <p class="submit"> <input type="submit" value="Send" name="submit" class="button" /> </p>
                            </form>
                            <div id="ajax_contactform_msg"></div>
                        </div>
                        <div class="dt-sc-one-fourth column animate" data-animation="fadeInRight" data-delay="100">
                        	<h5>London Office</h5>
                            <div class="enquiry-details">
                                <p> <i class="fa fa-cab"></i> 121 King St, Melbourne VIC 3, Australia </p>
                                <p> <i class="fa fa-phone"></i> +61 3 8376 6284 </p>
                                <p> <i class="fa fa-globe"></i> <a href="#"> envato.com </a> </p>
                                <p> <i class="fa fa-envelope"></i> <a href="#"> redart@gmail.com </a> </p>
                            </div>
                            <h5>Business Hours</h5>
                            <ul class="dt-sc-working-hours">
                            	<li>Hotline is available for 24 hours a day!..</li>
								<li>Monday - Friday :<span> 8am to 6pm</span></li>
                                <li>Saturday : <span>10am to 2pm</span></li>
                                <li>Sunday : <span>Closed</span></li>
                            </ul>
                        </div>
                        <div class="newsletter"><!--Newsletter Form Starts Here -->
                        		<h3>Newsletter</h3>
                                <form method="post" class="mailchimp-form dt-sc-one-half column first animate" data-animation="stretchLeft" data-delay="100" name="frmnewsletter" action="php/subscribe.php">	
                                    <p class="input-text">
                                       <input class="input-field" type="email" name="mc_email" value="" required/>
                                       <label class="input-label">
                                                <i class="fa fa-envelope-o icon"></i>
                                                <span class="input-label-content">Mail</span>
                                            </label>
                                       <input type="submit" name="submit" class="submit" value="Subscribe" />
                                    </p>
                                </form>
                                <div id="ajax_subscribe_msg"></div>
                                
                                <div class="newsletter-text animate dt-sc-one-half column" data-animation="stretchRight" data-delay="100"> <i class="fa fa-envelope-o"> </i> Feel free to place your Mail_ID and Subscribe to our Newsletter here. So that, you can receive our exiting Updates and Offers with no wait! </div>
                    	</div><!--Newsletter Form Ends Here -->
                    </div>
                </div><!-- Full-width section Ends Here -->
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
    
	<script src="../../resources/js/jquery.tabs.min.js"></script>
	<script type="text/javascript" src="../../resources/js/jquery-migrate.min.js"></script>
    
	<script src="../../resources/js/jquery.validate.min.js" type="text/javascript"></script>
    
	<script src="../../resources/js/jsplugins.js" type="text/javascript"></script>
    
	<script src="http://www.google.com/jsapi"></script>
    <script src="http://maps.google.com/maps/api/js?sensor=false"></script>
	<script src="../../resources/js/jquery.gmap.min.js" type="text/javascript"></script>
    
	<script src="../../resources/js/custom.js"></script>
        
</body>
</html>
