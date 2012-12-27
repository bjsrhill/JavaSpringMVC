<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Beverly S Hill</title>
	<LINK REL=stylesheet HREF='<c:url value="resources/sample.css" />' TYPE="text/css"/>
	<script src='<c:url value="resources/pagesList.js" />' ></script>
</head>
<body>
	<section id="banner">  
		<h1 id="headingMessage"></h1>   
	</section> 
		<section id="pitches">
			<article class="pagesListing">
				<c:forEach items="${pages}" var="pages">
					<h6>
					</h6>
				<h2>
				  <c:out value="${pages.textDesc}"/>
				</h2>	
				</c:forEach>
				<h6>
				</h6>
			</article>
		</section>
</body>
</html>
