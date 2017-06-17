<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>

<c:set var="itemTypes" value='<%=request.getAttribute("itemTypes")%>' />
<c:set var="artistSpecTypes" value='<%=request.getAttribute("artistSpecTypes")%>' />
<c:set var="recentlyAddedItems" value='<%=request.getAttribute("recentlyAddedItems")%>' />

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

        <div id="main">
        	<div class="breadcrumb"><!-- *BreadCrumb Starts here** -->
                <div class="container">
                    <h2>Shop <span>Art</span></h2>
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
                        <h3> Shop </h3>
                        <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do</p>
                    </div>                    	
                </div>
				<div class="fullwidth-section shop-grid"><!-- Full-width section Starts Here -->
                	<div class="sorting-products"><!-- sorting-products Starts Here -->
                    	<div class="dt-sc-one-fifth column first">
                            <div class="categories">
                                <h5>Artist Specialization</h5>
                                <div class="selection-box">
                                    <select class="shop-dropdown">
                                        <option value="-1" selected>Choose artist specialization</option>
                                        <c:forEach items="${artistSpecTypes}" var="element">
                                            <option value="${element.id}" class="fa fa-eyedropper">${element.type}</option>
                                        </c:forEach>

                                    </select>
                                </div>
                            </div>
                        </div>
                    	<div class="dt-sc-one-fifth column">
                            <div class="categories">
                                <h5>Sort By</h5>
                                <div class="selection-box">
                                    <select class="shop-dropdown">
                                        <option value="-1" selected>Sort by</option>
                                        <option value="1" class="fa fa-mortar-board">Popular Artist</option>
										<option value="2" class="fa fa-money">Best Seller</option>                                        
                                        <option value="3" class="fa fa-thumb-tack">Featured Art</option>
                                        <option value="4" class="fa fa-child">New Artist</option>
                                    </select>                                    
                                </div>
                            </div>
                        </div>
                    	<div class="dt-sc-one-fifth column">
                            <div class="categories">
                                <h5>Art Type</h5>
                                <div class="selection-box">
                                    <select class="shop-dropdown">
                                        <option value="-1" selected>Choose art type</option>
                                        <c:forEach items="${itemTypes}" var="element">
                                            <option value="${element.typeId}" class="fa fa-flask">${element.type}</option>
                                        </c:forEach>
                                        <%--<option value="1" class="fa fa-flask">Acrylic</option>--%>
										<%--<option value="2" class="fa fa-paint-brush">Oil Painting</option>--%>
                                        <%--<option value="2" class="fa fa-scissors">Sculpture</option>--%>
                                        <%--<option value="3" class="fa fa-tint">Water Painting</option>--%>
                                    </select>                                    
                                </div>
                            </div>
                        </div>
                        <div class = "dt-sc-one-fifth column">
                            <div class="categories">
                                <h5>Search</h5>
                                <div class="submit">
                                    <a class="shop-dropdown" href="/shop"> <span> <h4>Click Here!</h4> </span> </a>
                                    <%--<button  type="button">Click Here!</button>--%>
                                </div>
                            </div>
                        </div>
                    	<%--<div class="dt-sc-one-fifth column">--%>
                            <%--<div class="categories">--%>
                                <%--<h5>Size &amp; Shape</h5>--%>
                                <%--<div class="selection-box">--%>
                                    <%--<select class="shop-dropdown">--%>
                                        <%--<option value="-1" selected>Choose your shape</option>--%>
                                        <%--<option value="1" class="fa fa-picture-o">Landscape</option>--%>
                                        <%--<option value="2" class="fa fa-barcode">Portrait</option>--%>
                                        <%--<option value="3" class="fa fa-area-chart">Skew Framed</option>--%>
                                    <%--</select>                                    --%>
                                <%--</div>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                    	<%--<div class="dt-sc-one-fifth column">--%>
                            <%--<div class="categories">--%>
                                <%--<h5>Color</h5>--%>
                                <%--<div class="selection-box">--%>
                                    <%--<select class="shop-dropdown">--%>
                                        <%--<option value="-1" selected>Choose your color</option>--%>
                                        <%--<option value="1" class="fa fa-bookmark red">Red</option>--%>
                                        <%--<option value="2" class="fa fa-bookmark yellow">Yellow</option>--%>
                                        <%--<option value="3" class="fa fa-bookmark blue">Blue</option>--%>
                                        <%--<option value="4" class="fa fa-bookmark green">Green</option>--%>
                                        <%--<option value="5" class="fa fa-bookmark black">Black</option>--%>
                                    <%--</select>--%>
                                <%--</div>--%>
                            <%--</div>--%>
                        <%--</div>                                               --%>
                    </div><!-- sorting-products Ends Here -->
                    <ul class="products isotope">
                        <c:forEach items="${recentlyAddedItems}" var="itemElement">
                        <li class="product-wrapper dt-sc-one-fifth"> <!-- **product-wrapper - Starts** -->
                            <!-- **product-container - Starts** -->   
                            <div class="product-container">
                                <a href="/shop-detail/${itemElement.id}"><div class="product-thumb"> <img src="${itemElement.photoURL}" alt="image"/> </div> </a>
                                <!-- **product-title - Starts** -->
                                <div class="product-title"> 
                                    <a href="/shop-cart" class="type1 dt-sc-button"> <span class="fa fa-shopping-cart"></span> Add to Cart </a>
                                    <a href="#" class="type1 dt-sc-button"> <span class="fa fa-unlink"></span> Options </a>
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
                    <div class="container">
                        <div class="dt-sc-post-pagination">
                            <a class="dt-sc-button small type3 with-icon prev-post" href="#"> <span> Previous </span> <i class="fa fa-hand-o-left"> </i> </a>
                            <a class="dt-sc-button small type3 with-icon next-post" href="#"><i class="fa fa-hand-o-right"> </i>  <span> Next </span> </a>
                        </div>
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

    <script type="text/javascript" src="../../resources/js/jquery.isotope.min.js"></script>
    <script type="text/javascript" src="../../resources/js/jquery.isotope.perfectmasonry.min.js"></script>

	<script type="text/javascript" src="../../resources/js/jquery.dropdown.js"></script>
    
	<script src="../../resources/js/jsplugins.js" type="text/javascript"></script>
    
	<script src="../../resources/js/custom.js"></script>
        
</body>
</html>
