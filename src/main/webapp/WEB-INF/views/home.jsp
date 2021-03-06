<%--<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>

<c:set var="user" value='<%=session.getAttribute("user")%>' />
<c:set var="message" value='<%=request.getAttribute("message")%>' />


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
                            <h2 class="aligncenter"> find your inspiration </h2>
                            <p> Discover new work by emerging artists from around the world </p>
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
                                <div class="portfolio nature still-life dt-sc-one-fourth">
                                    <a data-fancybox="gallery" href="../../resources/images/product/Alice_Lin.jpg"><img src="../../resources/images/product/Alice_Lin.jpg"></a>
                                    <%--<figure>
                                    <img src="../../resources/images/product/Alice_Lin.jpg&lt;%&ndash;http://placehold.it/500x800&text=Portfolio+Image1&ndash;%&gt;" alt="" title="">
                                        <figcaption>
                                            <div class="portfolio-detail">
                                                <div class="views">
                                                    <a class="fa fa-camera-retro" data-gal="prettyPhoto[gallery]" href="http://placehold.it/500x800&text=Portfolio+Image1"></a><span>3</span>
                                                </div>
                                                <div class="portfolio-title">
                                                    <h5><a href="gallery-detail.html">Proposing Love</a></h5>
                                                    <p>Sample text here looks good</p>
                                                </div>
                                            </div>
                                        </figcaption>
                                    </figure>--%>
                                </div>
                                <div class="portfolio nature people street dt-sc-one-fourth">
                                    <a data-fancybox="gallery" href="../../resources/images/product/Andy_Westface.jpg"><img src="../../resources/images/product/Andy_Westface.jpg"></a>
                                    <%--<figure>
                                        <img src="../../resources/images/product/Andy_Westface.jpg&lt;%&ndash;http://placehold.it/1250x1160&text=Portfolio+Image2&ndash;%&gt;" alt="" title="">
                                        <figcaption>
                                            <div class="portfolio-detail">
                                                <div class="views">
                                                    <a class="fa fa-camera-retro" data-gal="prettyPhoto[gallery]" href="http://placehold.it/1250x1160&text=Portfolio+Image2"></a><span>7</span>
                                                </div>
                                                <div class="portfolio-title">
                                                    <h5><a href="gallery-detail-with-lhs.html">Brave Man</a></h5>
                                                    <p>A cowboy is a man with guts</p>
                                                </div>
                                            </div>
                                        </figcaption>
                                   </figure>--%>
                                </div>
                                <div class="portfolio street landscapes still-life dt-sc-one-fourth">
                                    <a data-fancybox="gallery" href="../../resources/images/product/Artem_RHADS_Chebokha.jpg"><img src="../../resources/images/product/Artem_RHADS_Chebokha.jpg"></a>
                                    <%--<figure>
                                        <img src="../../resources/images/product/Artem_RHADS_Chebokha.jpg&lt;%&ndash;http://placehold.it/1300x900&text=Portfolio+Image3&ndash;%&gt;" alt="" title="">
                                        <figcaption>
                                            <div class="portfolio-detail">
                                                <div class="views">
                                                    <a class="fa fa-camera-retro" data-gal="prettyPhoto[gallery]" href="http://placehold.it/1300x900&text=Portfolio+Image3"></a><span>9</span>
                                                </div>
                                                <div class="portfolio-title">
                                                    <h5><a href="gallery-detail-with-rhs.html">Mountain Ride</a></h5>
                                                    <p>the swagger of a cowboy</p>
                                                </div>
                                            </div>
                                        </figcaption>
                                   </figure>--%>
                                </div>
                                <div class="portfolio nature still-life dt-sc-one-fourth">
                                    <a data-fancybox="gallery" href="../../resources/images/product/Claude_Monet.jpg"><img src="../../resources/images/product/Claude_Monet.jpg"></a>
                                    <%--<figure>
                                        <img src="../../resources/images/product/Claude_Monet.jpg&lt;%&ndash;http://placehold.it/700x600&text=Portfolio+Image4&ndash;%&gt;" alt="" title="">
                                        <figcaption>
                                            <div class="portfolio-detail">
                                                <div class="views">
                                                    <a class="fa fa-camera-retro" data-gal="prettyPhoto[gallery]" href="http://placehold.it/700x600&text=Portfolio+Image4"></a><span>5</span>
                                                </div>
                                                <div class="portfolio-title">
                                                    <h5><a href="gallery-detail.html">River Ride</a></h5>
                                                    <p>Live Free, Ride Hard!!</p>
                                                </div>
                                            </div>
                                        </figcaption>
                                   </figure>--%>
                                </div>
                                <div class="portfolio people still-life dt-sc-one-fourth">
                                    <a data-fancybox="gallery" href="../../resources/images/product/Elia_Colombo.jpg"><img src="../../resources/images/product/Elia_Colombo.jpg"></a>
                                    <%--<figure>
                                        <img src="../../resources/images/product/Elia_Colombo.jpg&lt;%&ndash;http://placehold.it/1600x1300&text=Portfolio+Image5&ndash;%&gt;" alt="" title="">
                                        <figcaption>
                                            <div class="portfolio-detail">
                                                <div class="views">
                                                    <a class="fa fa-camera-retro" data-gal="prettyPhoto[gallery]" href="http://placehold.it/1600x1300&text=Portfolio+Image5"></a><span>6</span>
                                                </div>
                                                <div class="portfolio-title">
                                                    <h5><a href="gallery-detail-with-lhs.html">Ride Hard</a></h5>
                                                    <p>Saddle your horse</p>
                                                </div>
                                            </div>
                                        </figcaption>
                                   </figure>--%>
                                </div>
                                <div class="portfolio people still-life dt-sc-one-fourth">
                                    <a data-fancybox="gallery" href="../../resources/images/product/James_E._Tennison.jpg"><img src="../../resources/images/product/James_E._Tennison.jpg"></a>
                                    <%--<figure>
                                        <img src="../../resources/images/product/James_E._Tennison.jpg&lt;%&ndash;http://placehold.it/1200x1500&text=Portfolio+Image6&ndash;%&gt;" alt="" title="">
                                        <figcaption>
                                            <div class="portfolio-detail">
                                                <div class="views">
                                                    <a class="fa fa-camera-retro" data-gal="prettyPhoto[gallery]" href="http://placehold.it/1200x1500&text=Portfolio+Image6"></a><span>1</span>
                                                </div>
                                                <div class="portfolio-title">
                                                    <h5><a href="gallery-detail-with-rhs.html">Black Rider</a></h5>
                                                    <p>No reason to ride a horse</p>
                                                </div>
                                            </div>
                                        </figcaption>
                                   </figure>--%>
                                </div>
                                <div class="portfolio people nature still-life street dt-sc-one-fourth">
                                    <a data-fancybox="gallery" href="../../resources/images/product/Julia_Razumova.jpg"><img src="../../resources/images/product/Julia_Razumova.jpg"></a>
                                    <%--<figure>
                                        <img src="http://placehold.it/1500x2000&text=Portfolio+Image7" alt="" title="">
                                        <figcaption>
                                            <div class="portfolio-detail">
                                                <div class="views">
                                                    <a class="fa fa-camera-retro" data-gal="prettyPhoto[gallery]" href="http://placehold.it/1500x2000&text=Portfolio+Image7"></a><span>5</span>
                                                </div>
                                                <div class="portfolio-title">
                                                    <h5><a href="gallery-detail.html">Stay single</a></h5>
                                                    <p>Make Way</p>
                                                </div>
                                            </div>
                                        </figcaption>
                                   </figure>--%>
                                </div>
                                <div class="portfolio nature people street still-life dt-sc-one-fourth">
                                    <a data-fancybox="gallery" href="../../resources/images/product/Jean_Pierre_Arboleda.jpg"><img src="../../resources/images/product/Jean_Pierre_Arboleda.jpg"></a>
                                    <%--<figure>
                                        <img src="http://placehold.it/1600x1100&text=Portfolio+Image8" alt="" title="">
                                        <figcaption>
                                            <div class="portfolio-detail">
                                                <div class="views">
                                                    <a class="fa fa-camera-retro" data-gal="prettyPhoto[gallery]" href="http://placehold.it/1600x1100&text=Portfolio+Image8"></a><span>12</span>
                                                </div>
                                                <div class="portfolio-title">
                                                    <h5><a href="gallery-detail-with-lhs.html">Born to Ride</a></h5>
                                                    <p>Cowboys are born, ain't made!</p>
                                                </div>
                                            </div>
                                        </figcaption>
                                   </figure>--%>
                                </div>
                                <div class="portfolio street nature people still-life dt-sc-one-fourth">
                                    <a data-fancybox="gallery" href="../../resources/images/product/Joey_Guidone.jpg"><img src="../../resources/images/product/Joey_Guidone.jpg"></a>
                                    <%--<figure>
                                        <img src="http://placehold.it/650x450&text=Portfolio+Image9" alt="" title="">
                                        <figcaption>
                                            <div class="portfolio-detail">
                                                <div class="views">
                                                    <a class="fa fa-camera-retro" data-gal="prettyPhoto[gallery]" href="http://placehold.it/650x450&text=Portfolio+Image9"></a><span>15</span>
                                                </div>
                                                <div class="portfolio-title">
                                                    <h5><a href="gallery-detail-with-rhs.html">Stand Tall</a></h5>
                                                    <p>Hard to Settle!!</p>
                                                </div>
                                            </div>
                                        </figcaption>
                                   </figure>--%>
                                </div>
                            </div><!-- **dt-sc-portfolio-container Ends Here** -->
                        </div>
                    </div>
                </div><!-- **Full-width-section Ends Here** -->
                <div class="clear"></div>              
                <%--<div class="container">--%>
                    <%--<div class="main-title animate" data-animation="pullDown" data-delay="100">--%>
                        <%--<h2 class="aligncenter"> Blog </h2>--%>
                        <%--<p> Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do </p>--%>
                    <%--</div>--%>
                <%--</div>	--%>
                <%--<div class="fullwidth-section"><!-- **Full-width-section Starts Here** -->--%>
                	<%--<div class="blog-section">--%>
                        <%--<article class="blog-entry">--%>
                            <%--<div class="entry-thumb">--%>
                                <%--<ul class="blog-slider">--%>
                                    <%--<li> <img src="http://placehold.it/955x470&text=Blog+Slider1" alt="" title=""> </li>--%>
                                    <%--<li> <img src="http://placehold.it/955x470&text=Blog+Slider2" alt="" title=""> </li>--%>
                                    <%--<li> <img src="http://placehold.it/955x470&text=Blog+Slider3" alt="" title=""> </li>--%>
                                    <%--<li> <img src="http://placehold.it/955x470&text=Blog+Slider4" alt="" title=""> </li>--%>
                                <%--</ul>--%>
                            <%--</div> --%>
                            <%--<div class="entry-details">--%>
                                <%--<div class="entry-title">--%>
                                    <%--<h3><a href="blog-detail.html">Acrylic</a></h3>--%>
                                <%--</div>--%>
                                <%--<div class="entry-body">--%>
                                    <%--<p><b>Acrylic painting</b>, technique in which pigments are mixed with hot, liquid wax. After all of the colours have been applied to the painting surface, a heating element is passed over them until the individual brush or spatula marks fuse into a uniform film. </p>--%>
                                <%--</div>--%>
                                <%--<a class="type1 dt-sc-button small" href="gallery-detail.html">View Gallery<i class="fa fa-angle-right"></i></a>--%>
                            <%--</div>                   	--%>
                        <%--</article>--%>
					<%--</div>--%>
                    <%----%>
                	<%--<div class="blog-section">--%>
                        <%--<article class="blog-entry type2">--%>
                            <%--<div class="entry-details">--%>
                                <%--<div class="entry-title">--%>
                                    <%--<h3><a href="blog-detail.html">Encaustic</a></h3>--%>
                                <%--</div>--%>
                                <%--<div class="entry-body">--%>
                                    <%--<p><b>Encaustic painting</b>, technique in which pigments are mixed with hot, liquid wax. After all of the colours have been applied to the painting surface, a heating element is passed over them until the individual brush or spatula marks fuse into a uniform film. </p>--%>
                                <%--</div>--%>
                                <%--<a class="type1 dt-sc-button small" href="gallery-detail-with-lhs.html">View Gallery<i class="fa fa-angle-right"></i></a>--%>
                            <%--</div>                   	--%>
							<%--<div class="entry-thumb">--%>
                                <%--<ul class="blog-slider">--%>
                                    <%--<li> <img src="http://placehold.it/955x470&text=Blog+Slider5" alt="" title=""> </li>--%>
                                    <%--<li> <img src="http://placehold.it/955x470&text=Blog+Slider6" alt="" title=""> </li>--%>
                                    <%--<li> <img src="http://placehold.it/955x470&text=Blog+Slider7" alt="" title=""> </li>--%>
                                    <%--<li> <img src="http://placehold.it/955x470&text=Blog+Slider8" alt="" title=""> </li>--%>
                                <%--</ul>--%>
                            <%--</div>                             --%>
                        <%--</article>--%>
					<%--</div>--%>
                    <%----%>
                	<%--<div class="blog-section">--%>
                        <%--<article class="blog-entry">--%>
                            <%--<div class="entry-thumb">--%>
                                <%--<ul class="blog-slider">--%>
                                    <%--<li> <img src="http://placehold.it/955x470&text=Blog+Slider9" alt="" title=""> </li>--%>
                                    <%--<li> <img src="http://placehold.it/955x470&text=Blog+Slider10" alt="" title=""> </li>--%>
                                    <%--<li> <img src="http://placehold.it/955x470&text=Blog+Slider11" alt="" title=""> </li>--%>
                                    <%--<li> <img src="http://placehold.it/955x470&text=Blog+Slider12" alt="" title=""> </li>--%>
                                <%--</ul>--%>
                            <%--</div> --%>
                            <%--<div class="entry-details">--%>
                                <%--<div class="entry-title">--%>
                                    <%--<h3><a href="blog-detail-with-lhs.html">Oil painting</a></h3>--%>
                                <%--</div>--%>
                                <%--<div class="entry-body">--%>
                                    <%--<p><b>Oil painting</b>, technique in which pigments are mixed with hot, liquid wax. After all of the colours have been applied to the painting surface, a heating element is passed over them until the individual brush or spatula marks fuse into a uniform film. </p>--%>
                                <%--</div>--%>
                                <%--<a class="type1 dt-sc-button small" href="gallery-detail-with-rhs.html">View Gallery<i class="fa fa-angle-right"></i></a>--%>
                            <%--</div>                   	--%>
                        <%--</article>--%>
					<%--</div>--%>
                    <%----%>
                	<%--<div class="blog-section">--%>
                        <%--<article class="blog-entry type2">--%>
                            <%--<div class="entry-details">--%>
                                <%--<div class="entry-title">--%>
                                    <%--<h3><a href="blog-detail-with-rhs.html">Impasto</a></h3>--%>
                                <%--</div>--%>
                                <%--<div class="entry-body">--%>
                                    <%--<p><b>Impasto painting</b>, technique in which pigments are mixed with hot, liquid wax. After all of the colours have been applied to the painting surface, a heating element is passed over them until the individual brush or spatula marks fuse into a uniform film. </p>--%>
                                <%--</div>--%>
                                <%--<a class="type1 dt-sc-button small" href="gallery-detail-with-lhs.html">View Gallery<i class="fa fa-angle-right"></i></a>--%>
                            <%--</div>                   	--%>
							<%--<div class="entry-thumb">--%>
                                <%--<ul class="blog-slider">--%>
                                    <%--<li> <img src="http://placehold.it/955x470&text=Blog+Slider13" alt="" title=""> </li>--%>
                                    <%--<li> <img src="http://placehold.it/955x470&text=Blog+Slider14" alt="" title=""> </li>--%>
                                    <%--<li> <img src="http://placehold.it/955x470&text=Blog+Slider15" alt="" title=""> </li>--%>
                                    <%--<li> <img src="http://placehold.it/955x470&text=Blog+Slider16" alt="" title=""> </li>--%>
                                <%--</ul>--%>
                            <%--</div>                             --%>
                        <%--</article>--%>
					<%--</div>--%>
                <%--</div><!-- **Full-width-section Ends Here** -->--%>
                <%--<div class="clear"></div>             --%>
                <%----%>
                <%--<div class="fullwidth-section"><!-- **Full-width-section Starts Here** -->--%>
                    <%--<div class="container">--%>
                        <%--<div class="main-title animate" data-animation="pullDown" data-delay="100">--%>
                            <%--<h2 class="aligncenter"> Frames </h2>--%>
                            <%--<p> Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do </p>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <%--<div class="frame-grid"><!-- **Frame-Grid Starts Here** -->--%>
                        <%--<div class="frame-thumb"><!-- **Frame-Thumb Starts Here** -->--%>
                            <%--<div class="frame-fullwidth">--%>
                                <%--<div class="dt-sc-frame-container isotope"> <!-- **dt-sc-portfolio-container Starts Here** -->--%>
                                    <%--<div class="frame ceramic dt-sc-one-third">--%>
                                        <%--<figure>--%>
                                            <%--<img src="http://placehold.it/272x185" alt="" title="">--%>
                                       <%--</figure>--%>
                                    <%--</div>--%>
                                    <%--<div class="frame plastic dt-sc-one-third">--%>
                                        <%--<figure>--%>
                                            <%--<img src="http://placehold.it/200x250" alt="" title="">--%>
                                       <%--</figure>--%>
                                    <%--</div>--%>
                                    <%--<div class="frame steel dt-sc-one-third">--%>
                                        <%--<figure>--%>
                                            <%--<img src="http://placehold.it/260x195" alt="" title="">--%>
                                       <%--</figure>--%>
                                    <%--</div>--%>
                                    <%--<div class="frame wooden dt-sc-one-third">--%>
                                        <%--<figure>--%>
                                            <%--<img src="http://placehold.it/470x500" alt="" title="">--%>
                                       <%--</figure>--%>
                                    <%--</div>                                    --%>
                                    <%--<div class="frame wooden dt-sc-one-third">--%>
                                        <%--<figure>--%>
                                            <%--<img src="http://placehold.it/375x300" alt="" title="">--%>
                                       <%--</figure>--%>
                                    <%--</div>--%>
                                <%--</div><!-- **dt-sc-portfolio-container Ends Here** -->--%>
                            <%--</div>--%>
                        <%--</div><!-- **Frame-Thumb Starts Here** -->--%>
                        <%--<div class="frame-details"><!-- **Frame-Details Starts Here** -->--%>
                        	<%--<div class="frame-content">--%>
                            	<%--<div id="frame-all" class="dt-frames">--%>
                                    <%--<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore</p>--%>
                                <%--</div>--%>
                            	<%--<div id="frame-steel" class="dt-frames hidden">--%>
                                    <%--<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris.</p>--%>
                                <%--</div>--%>
                            	<%--<div id="frame-wooden" class="dt-frames hidden">--%>
                                    <%--<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.</p>--%>
                                <%--</div>--%>
                            	<%--<div id="frame-plastic" class="dt-frames hidden">--%>
                                    <%--<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit.</p>--%>
                                <%--</div>--%>
                            	<%--<div id="frame-ceramic" class="dt-frames hidden">--%>
                                    <%--<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam.</p>--%>
                                <%--</div>--%>
                            <%--</div>--%>
                            <%--<div class="frame-sorting"><!-- **Frame-sorting Starts Here** -->--%>
                                <%--<a data-filter="*" href="#" class="active-sort type1 dt-sc-button animate" data-animation="fadeIn" data-delay="100">All</a>--%>
                                <%--<a data-filter=".steel" href="#" class="type1 dt-sc-button animate" data-animation="fadeIn" data-delay="200">Steel</a>--%>
                                <%--<a data-filter=".wooden" href="#" class="type1 dt-sc-button animate" data-animation="fadeIn" data-delay="300">Wooden</a>--%>
                                <%--<a data-filter=".plastic" href="#" class="type1 dt-sc-button animate" data-animation="fadeIn" data-delay="400">plastic</a>--%>
                                <%--<a data-filter=".ceramic" href="#" class="type1 dt-sc-button animate" data-animation="fadeIn" data-delay="500">Ceramic</a>--%>
                            <%--</div><!-- **Frame-sorting Ends Here** -->--%>
                        <%--</div><!-- **Frame-Details Ends Here** -->--%>
                    <%--</div><!-- **Frame-Grid Ends Here** -->--%>
                <%--</div><!-- **Full-width-section Ends Here** -->--%>
                
            	<%--<div class="dt-sc-hr-invisible-small"></div>--%>
                <%--<div class="clear"></div>--%>

                <%--<div class="fullwidth-section"><!-- **Full-width-section Starts Here** -->--%>
                    <%--<div class="container">--%>
                    <%----%>
                        <%--<div class="main-title animate" data-animation="pullDown" data-delay="100">--%>
                            <%--<h2 class="aligncenter"> About Me </h2>--%>
                            <%--<p> Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do </p>--%>
                        <%--</div>--%>
                        <%----%>
                        <%--<div class="about-section">--%>
                        <%----%>
                            <%--<div class="dt-sc-one-half column first">--%>
                                <%--<img src="../../resources/images/about.png" title="" alt="">--%>
                            <%--</div>--%>
                            <%----%>
                            <%--<div class="dt-sc-one-half column">--%>
                                <%--<h3 class="animate" data-animation="fadeInLeft" data-delay="200"> A Little Intro</h3>--%>
                                <%--<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore</p>--%>
                                <%--<h3 class="animate" data-animation="fadeInLeft" data-delay="300">My Exhibitions</h3>--%>
                                <%--<p>Sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, Lorem ipsum dolor quis nostrud exercitation ullamco</p>--%>
                                <%--<h3 class="animate" data-animation="fadeInLeft" data-delay="400">Newsletter</h3>--%>
                                <%--<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit,</p>--%>
                                <%--<form method="post" class="mailchimp-form dt-sc-three-fourth" name="frmnewsletter" action="../../resources/php/subscribe.php">--%>
                                    <%--<p class="input-text">--%>
                                       <%--<input class="input-field" type="email" name="mc_email" value="" required/>--%>
                                       <%--<label class="input-label">--%>
                                                <%--<i class="fa fa-envelope-o icon"></i>--%>
                                                <%--<span class="input-label-content">Mail</span>--%>
                                            <%--</label>--%>
                                       <%--<input type="submit" name="submit" class="submit" value="Subscribe" />--%>
                                    <%--</p>--%>
                                <%--</form>--%>
                                <%--<div id="ajax_subscribe_msg"></div>                               --%>
                            <%--</div>--%>
                        <%--</div>--%>
                    <%--</div>--%>
				<%--</div><!-- **Full-width-section Ends Here** -->--%>
                
            	<div class="dt-sc-hr-invisible-small"></div>
                
            </section><!-- **Primary Ends Here** -->
            
            <footer id="footer" class="animate" data-animation="fadeIn" data-delay="100">
                <div class="container">
                    <div class="copyright">
                    
                        <ul class="footer-links">
                            <li><a href="${pageContext.request.contextPath}/contact">Contact us</a></li>
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
