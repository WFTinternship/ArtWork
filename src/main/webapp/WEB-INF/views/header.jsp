<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Armen
  Date: 6/17/2017
  Time: 11:33 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:set var="user" value='<%=session.getAttribute("user")%>' />


<div id="header-wrapper" class="dt-sticky-menu"> <!-- **header-wrapper Starts** -->
    <div id="header" class="header">
        <div class="container menu-container">
            <a class="logo" href="${pageContext.request.contextPath}/index"><img alt="Logo" src="../../resources/images/logo.png"></a>

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
            <li class="current_page_item menu-item-simple-parent"><a href="${pageContext.request.contextPath}/index">Home <span class="fa fa-home"></span></a>
                <%--<ul class="sub-menu">--%>
                    <%--<li class="current_page_item"><a href="http://www.wedesignthemes.com/html/redart/default">Default</a></li>--%>
                    <%--<li><a href="http://www.wedesignthemes.com/html/redart/menu-overlay">Menu Overlay</a></li>--%>
                    <%--<li><a href="http://www.wedesignthemes.com/html/redart/slide-bar">Slide Bar</a></li>--%>
                    <%--<li><a href="http://www.wedesignthemes.com/html/redart/slider-over-menu">Slider Over Menu</a></li>--%>
                <%--</ul>--%>
                <%--<a class="dt-menu-expand">+</a>--%>
            </li>
            <li class="menu-item-simple-parent">
                <a href="${pageContext.request.contextPath}/about">About us <span class="fa fa-user-secret"></span></a>
            </li>
            <li class="menu-item-simple-parent"><a href="gallery.tml">Gallery <span class="fa fa-camera-retro"></span></a>
                <%--<ul class="sub-menu">--%>
                    <%--<li><a href="gallery-detail.html">Gallery detail</a></li>--%>
                    <%--<li><a href="gallery-detail-with-lhs.html">Gallery-detail-left-sidebar</a></li>--%>
                    <%--<li><a href="gallery-detail-with-rhs.html">Gallery-detail-right-sidebar</a></li>--%>
                <%--</ul>--%>
                <%--<a class="dt-menu-expand">+</a>--%>
            </li>
            <li class="menu-item-simple-parent"><a href="${pageContext.request.contextPath}/shop">Shop <span class="fa fa-cart-plus"></span></a>
                <%--<ul class="sub-menu">--%>
                <%--<li><a href="item-detail.jsp">Shop Detail</a></li>--%>
                <%--<li><a href="shop-cart.jsp">Cart Page</a></li>--%>
                <%--<li><a href="shop-checkout.html">Checkout Page</a></li>--%>
                <%--</ul>--%>
                <%--<a class="dt-menu-expand">+</a>                    --%>
            </li>
            <%--<li class="menu-item-simple-parent"><a href="blog.html">Blog <span class="fa fa-pencil-square-o"></span></a>
                <ul class="sub-menu">
                    <li><a href="blog-detail.html">Blog detail</a></li>
                    <li><a href="blog-detail-with-lhs.html">Blog-detail-left-sidebar</a></li>
                    <li><a href="blog-detail-with-rhs.html">Blog-detail-right-sidebar</a></li>
                </ul>
                <a class="dt-menu-expand">+</a>
            </li>--%>
            <li class="menu-item-simple-parent">
                <a href="${pageContext.request.contextPath}/contact">Contact<span class="fa fa-map-marker"></span></a>
            </li>
            <li class="menu-item-simple-parent">
                <c:choose>
                    <c:when test="${user==null}">
                        <a href="${pageContext.request.contextPath}/login"> Account <span class="fa fa-paint-brush"></span></a>
                    </c:when>
                    <c:otherwise>
                        <a href="${pageContext.request.contextPath}/account"> Hi ${user.firstName} <span class="fa fa-paint-brush"></span></a>
                    </c:otherwise>
                </c:choose>

                <ul class="sub-menu">
                    <%--<li><a href="progressbar.html"> Progress-bar </a></li>
                    <li><a href="buttons.html"> Buttons Page </a></li>
                    <li><a href="tabs.html"> tabs-accordions </a></li>
                    <li><a href="typography.html"> typography </a></li>
                    <li><a href="columns.html"> columns </a></li>--%>
                    <c:choose>
                        <c:when test="${user==null}">
                            <li><a href="${pageContext.request.contextPath}/login">Log in  </a> </li>
                            <li><a href="${pageContext.request.contextPath}/signup">Sign up </a> </li>
                        </c:when>
                        <c:otherwise>
                            <li><a href="${pageContext.request.contextPath}/account">My Account </a> </li>
                            <li><a href="${pageContext.request.contextPath}/logoutProcess">Log out  </a> </li>
                        </c:otherwise>
                    </c:choose>
                </ul>
                <a class="dt-menu-expand">+</a>
            </li>
        </ul> <!-- Menu Ends -->
    </nav> <!-- Main-menu Ends -->
</div><!-- **header-wrapper Ends** -->