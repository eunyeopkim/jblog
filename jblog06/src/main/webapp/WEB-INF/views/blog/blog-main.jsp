<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!doctype html>
<%
	pageContext.setAttribute("newLine", "\n");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JBlog</title>
<Link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jblog.css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/blogMainHeader.jsp" />
		<div id="wrapper">
			<div id="content">
				<div class="blog-content">
					<h4>${postVoContents.title }</h4>
					<p>
						${fn:replace(postVoContents.contents, newLine, "<br>") }
					</p>
				</div>
				<ul class="blog-list">
					<c:forEach var="pvo" items="${postVo }" varStatus='status'>
						<li><a href="${pageContext.request.contextPath}/${authUser.id }/${pvo.categoryNo }/${pvo.no}">${pvo.title }</a> <span>${pvo.regDate }</span>	</li>
					</c:forEach>
				</ul>
			</div>
		</div>

		<div id="extra">
			<div class="blog-logo">
				<img src="${pageContext.request.contextPath}${blogVo.logo }">
			</div>
		</div>

		<div id="navigation">
			<h2>카테고리</h2>
			<ul>
				<c:forEach var="cvo" items="${categoryVo }" varStatus='status'>
					<li><a href="${pageContext.request.contextPath}/${authUser.id }/${cvo.no }">${cvo.name }</a></li>
				</c:forEach>
			</ul>
		</div>
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>