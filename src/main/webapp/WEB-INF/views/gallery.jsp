<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>

<c:set var="imageUrlList" value='<%=request.getAttribute("imageUrlList")%>'/>


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

    <link id="light-dark-css" href="../../resources/dark/dark.css" rel="stylesheet" media="all" />

    <!-- **Font Awesome** -->
    <link rel="stylesheet" href="../../resources/css/font-awesome.min.css" type="text/css" />

    <!-- Modernizr -->
    <script src="../../resources/js/modernizr.js"></script>

    <%--fancyBox--%>
    <script src="//code.jquery.com/jquery-3.2.1.min.js"></script>

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/fancybox/3.0.47/jquery.fancybox.min.css" />
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
        <div class="slider-container">
            <div class="slider fullwidth-section parallax"></div>
        </div>
        <div id="main">
            <section id="primary" class="content-full-width"> <!-- **Primary Starts Here** -->

                <div class="dt-sc-hr-invisible-small"></div>

                <div class="fullwidth-section"> <!-- **Full-width-section Starts Here** -->
                    <div class="container">
                        <div class="main-title animate" data-animation="pullDown" data-delay="100">
                            <h2 class="aligncenter"> Gallery </h2>
                            <p> Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do </p>
                        </div>
                    </div>
                    <div class="dt-sc-sorting-container">
                        <a data-filter="*" href="#" title = "09" class="dt-sc-tooltip-top active-sort type1 dt-sc-button animate" data-animation="fadeIn" data-delay="100">All</a>
                        <a data-filter=".nature" href="#" title = "06" class="dt-sc-tooltip-top type1 dt-sc-button animate" data-animation="fadeIn" data-delay="200">Nature</a>
                        <a data-filter=".people" href="#" title = "06" class="dt-sc-tooltip-top type1 dt-sc-button animate" data-animation="fadeIn" data-delay="300">People</a>
                        <a data-filter=".street" href="#" title = "05" class="dt-sc-tooltip-top type1 dt-sc-button animate" data-animation="fadeIn" data-delay="400">Street</a>
                        <a data-filter=".still-life" href="#" title = "08" class="dt-sc-tooltip-top type1 dt-sc-button animate" data-animation="fadeIn" data-delay="500">Still life</a>
                    </div>
                    <div class="portfolio-fullwidth">
                        <div class="portfolio-grid">
                            <div class="dt-sc-portfolio-container isotope"> <!-- **dt-sc-portfolio-container Starts Here** -->
                                <%--<div class="portfolio nature still-life dt-sc-one-fourth">--%>
                                    <%--<a data-fancybox="gallery" href="../../resources/images/product/Alice_Lin.jpg"><img src="../../resources/images/product/Alice_Lin.jpg"></a>--%>

                                    <c:forEach  items="${imageUrlList}"  var="image">
                                        <div class="portfolio nature still-life dt-sc-one-fourth">g
                                            <a data-fancybox="gallery" href="${image}"><img src="${image}"></a>
                                        </div>
                                    </c:forEach>

                                <%--<div class="portfolio street landscapes still-life dt-sc-one-fourth">--%>
                                    <%--<a data-fancybox="gallery.jsp" href="../../resources/images/product/Artem_RHADS_Chebokha.jpg"><img src="../../resources/images/product/Artem_RHADS_Chebokha.jpg"></a>--%>
                                   <%----%>
                                <%--</div>--%>
                                <%--<div class="portfolio nature still-life dt-sc-one-fourth">--%>
                                    <%--<a data-fancybox="gallery.jsp" href="../../resources/images/product/Claude_Monet.jpg"><img src="../../resources/images/product/Claude_Monet.jpg"></a>--%>
                                  <%----%>
                                <%--</div>--%>
                                <%--<div class="portfolio people still-life dt-sc-one-fourth">--%>
                                    <%--<a data-fancybox="gallery.jsp" href="../../resources/images/product/Elia_Colombo.jpg"><img src="../../resources/images/product/Elia_Colombo.jpg"></a>--%>
                                   <%----%>
                                <%--</div>--%>
                                <%--<div class="portfolio people still-life dt-sc-one-fourth">--%>
                                    <%--<a data-fancybox="gallery.jsp" href="../../resources/images/product/James_E._Tennison.jpg"><img src="../../resources/images/product/James_E._Tennison.jpg"></a>--%>
                                  <%----%>
                                <%--</div>--%>
                                <%--<div class="portfolio people nature still-life street dt-sc-one-fourth">--%>
                                    <%--<a data-fancybox="gallery.jsp" href="../../resources/images/product/Julia_Razumova.jpg"><img src="../../resources/images/product/Julia_Razumova.jpg"></a>--%>
                                   <%----%>
                                <%--</div>--%>
                                <%--<div class="portfolio nature people street still-life dt-sc-one-fourth">--%>
                                    <%--<a data-fancybox="gallery.jsp" href="../../resources/images/product/Jean_Pierre_Arboleda.jpg"><img src="../../resources/images/product/Jean_Pierre_Arboleda.jpg"></a>--%>
                                  <%----%>
                                <%--</div>--%>
                                <%--<div class="portfolio street nature people still-life dt-sc-one-fourth">--%>
                                    <%--<a data-fancybox="gallery.jsp" href="../../resources/images/product/Joey_Guidone.jpg"><img src="../../resources/images/product/Joey_Guidone.jpg"></a>--%>
                                  <%----%>
                                <%--</div>--%>
                            </div><!-- **dt-sc-portfolio-container Ends Here** -->
                        </div>
                    </div>
                </div><!-- **Full-width-section Ends Here** -->
                <div class="clear"></div>

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

    </div><!-- **inner-wrapper - End** -->
</div><!-- **Wrapper Ends** -->

<!-- **jQuery** -->
<script src="${pageContext.request.contextPath}/resources/js/jquery-1.11.2.min.js" type="text/javascript"></script>

<script src="${pageContext.request.contextPath}/resources/js/jquery.inview.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/resources/js/jquery.viewport.js" type="text/javascript"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.isotope.min.js"></script>

<script src="${pageContext.request.contextPath}/resources/js/jsplugins.js" type="text/javascript"></script>

<script src="${pageContext.request.contextPath}/resources/js/jquery.prettyPhoto.js" type="text/javascript"></script>

<script src="${pageContext.request.contextPath}/resources/js/jquery.validate.min.js" type="text/javascript"></script>

<script src="${pageContext.request.contextPath}/resources/js/jquery.tipTip.minified.js" type="text/javascript"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.bxslider.min.js"></script>

<script src="${pageContext.request.contextPath}/resources/js/custom.js"></script>
</body>
</html>
