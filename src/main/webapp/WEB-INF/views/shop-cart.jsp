<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>

<c:set var="user" value='<%=session.getAttribute("user")%>'/>

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
        <%--<div id="main">--%>
        	<%--<div class="breadcrumb"><!-- *BreadCrumb Starts here** -->--%>
                <%--<div class="container">--%>
                    <%--<h2>Shop <span>Cart</span></h2>--%>
                    <%--<div class="user-summary">--%>
                    	<%--<div class="account-links">--%>
                        	<%--<a href="/account">My Account</a>--%>
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
            	<div class="container">
                	<div class="woocommerce">
                        <form action="#" method="post">
                            <table class="shop_table cart">
                                <thead>
                                    <tr>
                                        <th class="product-thumbnail">Image</th>
                                        <th class="product-name">Product</th>
                                        <th class="product-price">Price</th>
                                        <th class="product-quantity">Quantity</th>
                                        <th class="product-subtotal">Total</th>
                                        <th class="product-remove">Remove</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr class = "cart_table_item">
                                        <!-- The thumbnail -->
                                        <td class="product-thumbnail">
                                            <a href="/shop-detail"><img src="http://placehold.it/75x75&text=Thumb+1" class="attachment-shop_thumbnail wp-post-image" alt="T_7_front" /></a>
                                        </td>
            
                                        <!-- Product Name -->
                                        <td class="product-name">
                                            <h6><a href="/shop-detail">Secret To Creativity</a></h6>
                                        </td>
            
                                        <!-- Product price -->
                                        <td class="product-price">
                                            <span class="amount"><i class="fa fa-gbp"></i> 150</span>
                                        </td>
            
                                        <!-- Quantity inputs -->
                                        <td class="product-quantity">
                                            <div class="quantity">
                                                <input type="button" class="minus" value="-"/>
                                                <input type="number" name="quantity" step="1" value="1" min="1" title="Qty" class="input-text qty text" />
                                                <input type="button" class="plus" value="+"/>
                                            </div>			
                                        </td>
            
                                        <!-- Product subtotal -->
                                        <td class="product-subtotal">
                                            <span class="amount"><i class="fa fa-gbp"></i> 150</span>
                                        </td>
                                        
                                        <!-- Remove from cart link -->
                                        <td class="product-remove">
                                            <a href="#" class="remove" title="Remove this item">&times;</a>
                                        </td>
                                    </tr>
                                    <tr class = "cart_table_item">
            
                                        <!-- The thumbnail -->
                                        <td class="product-thumbnail">
                                            <a href="item-detail.jsp"><img src="http://placehold.it/75x75&text=Thumb+2" class="attachment-shop_thumbnail wp-post-image" alt="T_7_front" /></a>
                                        </td>
            
                                        <!-- Product Name -->
                                        <td class="product-name">
                                            <h6><a href="item-detail.jsp">Lonely in Rain</a></h6>
                                        </td>
            
                                        <!-- Product price -->
                                        <td class="product-price">
                                            <span class="amount"><i class="fa fa-gbp"></i> 175</span>
                                        </td>
            
                                        <!-- Quantity inputs -->
                                        <td class="product-quantity">
                                        	
                                            <div class="quantity">
                                                <input type="button" class="minus" value="-" />
                                                <input type="number" name="quantity" step="1" value="1" min="1" title="Qty" class="input-text qty text" />
                                                <input type="button" class="plus" value="+" />
                                            </div>			
                                        </td>
            
                                        <!-- Product subtotal -->
                                        <td class="product-subtotal">
                                            <span class="amount"><i class="fa fa-gbp"></i> 175</span>
                                        </td>
                                        
                                        <!-- Remove from cart link -->
                                        <td class="product-remove">
                                            <a href="#" class="remove" title="Remove this item">&times;</a>
										</td>
                                    </tr>
                                </tbody>
                            </table>
                            <%--<input type="submit" class="button" name="update_cart" value="Update Cart">--%>
                            <input type="submit" class="button" name="continue" value="Continue Shopping">                          
                        </form>
                        <div class="cart-collaterals">
                            <%--<div class="coupon">
                            	<h3>Coupon</h3>
                                <form action="#" method="post">
                                    <label for="coupon_code">Enter Coupon Code</label> 
                                    <input name="coupon_code" class="input-text" id="coupon_code" value="" placeholder="Enter Code" />
                                    <input type="submit" value="Apply Coupon" name="apply_coupon" class="button">
                                </form>                                
                            </div>--%>
                            <div class="cart_totals">
                            	<h3>Cart Totals</h3>
                                <table>
                                    <tbody>
                                    
                                        <tr class="cart-subtotal">
                                            <th>Cart Subtotal</th>
                                            <td><span class="amount"><i class="fa fa-gbp"></i> 150</span></td>
                                        </tr>
                                        
                                        <tr class="shipping">
                                            <th>Shipping</th>
                                            <td>Free Shipping<input type="hidden" name="shipping_method" id="shipping_method" value="free_shipping" /></td>
                                        </tr>
                                        
                                        <tr class="total">
                                            <th>Order Price Total</th>
                                            <td><strong><span class="amount"><i class="fa fa-gbp"></i> 150</span></strong></td>
                                        </tr>
                                    
                                    </tbody>
                                </table>
                                <a class="dt-sc-button medium type2 with-icon" href="shop-checkout.html"><i class="fa fa-shopping-cart"></i> <span> Proceed to Checkout </span> </a>                                                                
                            </div>                        	
                        </div>
                    </div>
                </div>
                <div class="dt-sc-hr-invisible-small"></div>
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
    
	<script src="../../resources/js/custom.js"></script>
        
</body>
</html>
