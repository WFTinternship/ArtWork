<%--
  Created by IntelliJ IDEA.
  User: Armen
  Date: 6/17/2017
  Time: 11:33 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="header-wrapper" class="dt-sticky-menu"> <!-- **header-wrapper Starts** -->
    <div id="header" class="header">
        <div class="container menu-container">
            <a class="logo" href="index.jsp"><img alt="Logo" src="../../resources/images/logo.png"></a>

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
            <li class="menu-item-simple-parent"><a href="/index">Home <span class="fa fa-home"></span></a>
                <ul class="sub-menu">
                    <li><a href="http://www.wedesignthemes.com/html/redart/default">Default</a></li>
                    <li><a href="http://www.wedesignthemes.com/html/redart/menu-overlay">Menu Overlay</a></li>
                    <li><a href="http://www.wedesignthemes.com/html/redart/slide-bar">Slide Bar</a></li>
                    <li><a href="http://www.wedesignthemes.com/html/redart/slider-over-menu">Slider Over Menu</a></li>
                </ul>
                <a class="dt-menu-expand">+</a>
            </li>

            <li class="menu-item-simple-parent">
                <a href="/about">About us <span class="fa fa-user-secret"></span></a>
            </li>
            <li class="menu-item-simple-parent"><a href="../../resources/gallery.html">Gallery <span class="fa fa-camera-retro"></span></a>
                <ul class="sub-menu">
                    <li><a href="../../resources/gallery-detail.html">Gallery detail</a></li>
                    <li><a href="../../resources/gallery-detail-with-lhs.html">Gallery-detail-left-sidebar</a></li>
                    <li><a href="../../resources/gallery-detail-with-rhs.html">Gallery-detail-right-sidebar</a></li>
                </ul>
                <a class="dt-menu-expand">+</a>
            </li>
            <li class="current_page_item menu-item-simple-parent"><a href="/shop<%--.jsp--%>">Shop <span class="fa fa-cart-plus"></span></a>
                <ul class="sub-menu">
                    <li><a href="../../resources/shop-detail.html">Shop Detail</a></li>
                    <li><a href="../../resources/shop-cart.html">Cart Page</a></li>
                    <li><a href="../../resources/shop-checkout.html">Checkout Page</a></li>
                </ul>
                <a class="dt-menu-expand">+</a>
            </li>
            <<%--li class="menu-item-simple-parent"><a href="../../resources/blog.html">Blog <span class="fa fa-pencil-square-o"></span></a>
                        <ul class="sub-menu">
                            <li><a href="../../resources/blog-detail.html">Blog detail</a></li>
                            <li><a href="../../resources/blog-detail-with-lhs.html">Blog-detail-left-sidebar</a></li>
                            <li><a href="../../resources/blog-detail-with-rhs.html">Blog-detail-right-sidebar</a></li>
                        </ul>
                        <a class="dt-menu-expand">+</a>
                    </li>--%>
            <li class="menu-item-simple-parent">
                <a href="contact">Contact <span class="fa fa-map-marker"></span></a>
            </li>
            <li class="menu-item-simple-parent">
                <a href="<%--../../resources/progressbar.html--%>">Account<%--shortcodes--%> <span class="fa fa-paint-brush"></span></a>
                <ul class="sub-menu">
                    <%--<li><a href="../../resources/progressbar.html"> Progress-bar </a></li>
                    <li><a href="../../resources/buttons.html"> Buttons Page </a></li>
                    <li><a href="../../resources/tabs.html"> tabs-accordions </a></li>
                    <li><a href="../../resources/typography.html"> typography </a></li>
                    <li><a href="../../resources/columns.html"> columns </a></li>--%>
                    <li><a href="/account">My Account </a> </li>
                    <li><a href="/login">Log in </a> </li>
                    <li><a href="/signup">Sign up </a> </li>
                </ul>
                <a class="dt-menu-expand">+</a>
            </li>
        </ul> <!-- Menu Ends -->
    </nav> <!-- Main-menu Ends -->
</div><!-- **header-wrapper Ends** -->
