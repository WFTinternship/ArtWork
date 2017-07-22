<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Armen
  Date: 6/29/2017
  Time: 6:31 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<ul class="list-unstyled">
    <li><a href="${pageContext.request.contextPath}/account-details"><i class="fa fa-user"></i> My Account</a></li>
    <li><a href="${pageContext.request.contextPath}/edit-profile"><i class="fa fa-edit"></i> Edit Profile</a></li>
    <li><a href="${pageContext.request.contextPath}/purchase-history"><i class="fa fa-list-alt"></i> Purchase History</a></li>
    <c:if test="${user['class'].simpleName eq 'Artist'}">
        <li><a href="${pageContext.request.contextPath}/my-works"><i class="fa fa-list-alt"></i> My ArtWorks </a></li>
        <li><a href="${pageContext.request.contextPath}/add-item"><i class="fa fa-list-alt"></i> Add ArtWork </a></li>
    </c:if>
</ul>
