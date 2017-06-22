<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>

<c:set var="artistSpecTypes" value='<%=request.getAttribute("artistSpecTypes")%>'/>



<!doctype html>
<html lang="en" class="no-js">

<!-- Mirrored from codyhouse.co/demo/animated-sign-up-flow/ by HTTrack Website Copier/3.x [XR&CO'2014], Fri, 16 Jun 2017 12:52:09 GMT -->
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">

	<link href='https://fonts.googleapis.com/css?family=Open+Sans:400,300,700,600' rel='stylesheet' type='text/css'>

	<link rel="stylesheet" href="../../resources/css/signUp-reset.css"> <!-- CSS reset -->
	<link rel="stylesheet" href="../../resources/css/signUp-style.css"> <!-- Resource style -->
	<script src="../../resources/js/signUp-modernizr.js"></script> <!-- Modernizr -->
  	
	<title>Welcome!</title>
</head>
<body>
	<header class="cd-main-header">
		<h1>Sign Up</h1>
	</header>

	<ul class="cd-pricing">
		<li>
			<header class="cd-pricing-header">
				<h2>Buyer</h2>

			</header> <!-- .cd-pricing-header -->

			<div class="cd-pricing-features">
				Lorem ipsum dolor sit amet, consectetur adipiscing elit.
				Fusce ut ante quis sem pretium viverra.
				Aliquam sit amet ligula dignissim, vestibulum mi quis, congue lorem.
			</div> <!-- .cd-pricing-features -->

			<footer class="cd-pricing-footer">
				<a class="buyer" href="#0">Select</a>
			</footer> <!-- .cd-pricing-footer -->
		</li>
		
		<li>
			<header class="cd-pricing-header">
				<h2>Artist</h2>

			</header> <!-- .cd-pricing-header -->

			<div class="cd-pricing-features">
				Lorem ipsum dolor sit amet, consectetur adipiscing elit.
				Fusce ut ante quis sem pretium viverra.
				Aliquam sit amet ligula dignissim, vestibulum mi quis, congue lorem.
			</div> <!-- .cd-pricing-features -->

			<footer class="cd-pricing-footer1">
				<a class="artist" href="#0">Select</a>
			</footer> <!-- .cd-pricing-footer -->
		</li>
	</ul> <!-- .cd-pricing -->

	<div class="cd-form">
		
		<div class="cd-plan-info">
			<!-- content will be loaded using jQuery - according to the selected plan -->
		</div>

		<div class="cd-more-info">
			<h3>Need help?</h3>
			<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit.</p>
		</div>
		
		<form action="/signup" method="post">
			<fieldset>
				<legend>Account Info</legend>

				<div class="half-width">
					<label for="userName">FirstName</label>
					<input type="text" id="userName" name="firstName">
				</div>

				<div class="half-width">
					<label for="userLastName">LastName</label>
					<input type="text" id="userLastName" name="lastName">
				</div>

				<div class="half-width">
					<label for="userAge">Age</label>
					<input type="text" id="userAge" name="age">
				</div>

				<div class="half-width">
					<label for="userEmail">Email</label>
					<input type="email" id="userEmail" name="email">
				</div>
				
				<div class="half-width">
					<label for="userPassword">Password</label>
					<input type="password" id="userPassword" name="password">
				</div>
				
				<div class="half-width">
					<label for="userPasswordRepeat">Repeat Password</label>
					<input type="password" id="userPasswordRepeat" name="userPasswordRepeat">
				</div>
			</fieldset>

			<fieldset>
				<div>
					<input type="submit" value="Get started">
				</div>
			</fieldset>
		</form>

		<a href="#0" class="cd-close"></a>
	</div> <!-- .cd-form -->

	<div class="cd-form1">

		<div class="cd-plan-info">
			<!-- content will be loaded using jQuery - according to the selected plan -->
		</div>

		<div class="cd-more-info">
			<h3>Need help?</h3>
			<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit.</p>
		</div>

		<form action="/signup" method="post">
			<fieldset>
				<legend>Account Info</legend>

				<div class="half-width">
					<label for="artistName">FirstName</label>
					<input type="text" id="artistName" name="firstName">
				</div>

				<div class="half-width">
					<label for="artistLastName">LastName</label>
					<input type="text" id="artistLastName" name="lastName">
				</div>

				<div class="half-width">
					<label for="artistAge">Age</label>
					<input type="text" id="artistAge" name="age">
				</div>

				<div class="half-width">
					<label for="artistEmail">Email</label>
					<input type="email" id="artistEmail" name="email">
				</div>

				<div class="half-width">
					<label for="artistPassword">Password</label>
					<input type="password" id="artistPassword" name="password">
				</div>

				<div class="half-width">
					<label for="artistPasswordRepeat">Repeat Password</label>
					<input type="password" id="artistPasswordRepeat" name="passwordRepeat">
				</div>

				<div class="half-width">
					<label for="artistSpec">Specialization</label>
					<br/>
					<select name="artistSpec" id="artistSpec">
						<c:forEach items="${artistSpecTypes}" var="element">
							<option value="${element.type}">${element.type}</option>
						</c:forEach>

						<%--<option value="PAINTER">PAINTER</option>--%>
						<%--<option value="SCULPTOR">SCULPTOR</option>--%>
						<%--<option value="PHOTOGRAPHER">PHOTOGRAPHER</option>--%>
						<%--<option value="OTHER">OTHER</option>--%>
					</select>
				</div>
				<br/>
				<div class="half-width">
					<input type="file" name="imageUpload" id="imageUpload" class="hide"/>
					<label for="imageUpload" class="btn btn-large">Select file</label><br/><br/><br/>
					<img src="" id="imagePreview" alt="Preview Image" width="200px"/>
				</div>

			</fieldset>

			<fieldset>
				<div>
					<input type="submit" value="Get started">
				</div>
			</fieldset>
		</form>

		<a href="#0" class="cd-close"></a>
	</div>
	
	<div class="cd-overlay"></div> <!-- shadow layer -->
<!-- <div id="carbonads-container">
		<div class="carbonad">
			<script async type="text/javascript" src="../../../cdn.carbonads.com/carbon3815.js?zoneid=1673&amp;serve=C6AILKT&amp;placement=codyhouseco" id="_carbonads_js"></script>
		</div>
		<a href="#0" class="close-carbon-adv">Close</a>
	</div> --> <!-- #carbonads-container -->
<script src="../../resources/js/jquery-2.1.4.js"></script>
<script src="../../resources/js/signUp-velocity.min.js"></script>
<script src="../../resources/js/signUp-main-min.js"></script> <!-- Resource jQuery -->
<script>
	(function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
  	(i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
  	m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
  	})(window,document,'script','../../../www.google-analytics.com/analytics.js','ga');

  	ga('create', 'UA-48014931-1', 'codyhouse.co');
  	ga('send', 'pageview');

  	jQuery(document).ready(function($){
  		$('.close-carbon-adv').on('click', function(event){
  			event.preventDefault();
  			$('#carbonads-container').hide();
  		});
  	});
</script>
</body>

<!-- Mirrored from codyhouse.co/demo/animated-sign-up-flow/ by HTTrack Website Copier/3.x [XR&CO'2014], Fri, 16 Jun 2017 12:52:12 GMT -->
</html>