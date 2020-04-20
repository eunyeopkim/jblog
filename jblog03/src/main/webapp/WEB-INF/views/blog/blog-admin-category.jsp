<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JBlog</title>
<Link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jblog.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-3.4.1.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/ejs/ejs.js"></script>
<script>


var listItemAddTemplate = new EJS({
	url:"${pageContext.request.contextPath }/assets/js/ejs/list-item-add.ejs"
});

var listTemplate = new EJS({
	url:"${pageContext.request.contextPath }/assets/js/ejs/list-template.ejs"
});

var fetchList = function(){
	
	$.ajax({
		url: '${pageContext.request.contextPath }/${authUser.id}/api/admin/category',
		async: true,
		type: 'get',
		dataType: 'json',
		data: '',
		success: function(response){
			console.log(response);
			if(response.result != "success"){
				console.error(response.message);
				return;
			}
			var imgUrl = '${pageContext.request.contextPath}/assets/images/delete.jpg';
			response.data.url = imgUrl;
			var pageContext = '${pageContext.request.contextPath }/${authUser.id}';
			response.data.pageContext = pageContext;
			 
			var html = listTemplate.render(response);
			$(".admin-cat").append(html);
			
			
		},
		error: function(xhr, status, e){
			console.error(status + " : " + e);
		}
	});	
}
$(function(){
	
		//입력폼 submit 이벤트
		$('#add-form').submit(function(event){
			event.preventDefault();
			var vo = {};
			vo.name = $('#text-category').val();
			vo.description = $('#text-description').val();
			
			$.ajax({
				url: '${pageContext.request.contextPath }/${authUser.id}/api/add',
				async: true,
				type: 'post',
				dataType: 'json',
				contentType: 'application/json',
				data: JSON.stringify(vo),
				success: function(response){
					if(response.result != "success"){
						console.error(response.message);
						return;
					}
					// rendering
					var imgUrl = '${pageContext.request.contextPath}/assets/images/delete.jpg';
					response.data.url = imgUrl; 
					var pageContext = '${pageContext.request.contextPath }/${authUser.id}';
					response.data.pageContext = pageContext;
					var html = listItemAddTemplate.render(response.data);
					$(".admin-cat").prepend(html);
					
				},
				error: function(xhr, status, e){
					console.error(status + " : " + e);
				}
			});
		});
		
		$('.admin-cat tr th a').click(function(){
			event.preventDefault();
			
			$.ajax({
				url: '${pageContext.request.contextPath }/${authUser.id}/api/delete/'+ no,
				async: true,
				type: 'delete',
				dataType: 'json',
				data: '',
				success: function(response){
					if(response.result != "success"){
						console.error(response.message);
						return;
					}
					
					if(response.data != -1 ){
						$(".admin-cat tr th a[data-no=" + response.data + "]").remove();
						return;
					}
				},
				error: function(xhr, status, e){
					console.error(status + " : " + e);
				}
			});
		});
		
		
		
});

fetchList();
</script>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/blogHeader.jsp" />
		<div id="wrapper">
			<div id="content" class="full-screen">
				<ul class="admin-menu">
					<li><a href="${pageContext.request.contextPath}/${authUser.id }/admin/basic">기본설정</a></li>
					<li class="selected">카테고리</li>
					<li><a href="${pageContext.request.contextPath}/${authUser.id }/admin/write">글작성</a></li>
				</ul>
		      	<table class="admin-cat">
		      		<tr>
		      			<th>번호</th>
		      			<th>카테고리명</th>
		      			<th>포스트 수</th>
		      			<th>설명</th>
		      			<th>삭제</th>      			
		      		</tr>
						  
				</table>
      	
      			<h4 class="n-c">새로운 카테고리 추가</h4>
      			<form id="add-form" action="" method="post">
			      	<table id="admin-cat-add">
			      		<tr>
			      			<td class="t">카테고리명</td>
			      			<td><input type="text" id="text-category" name="name"></td>
			      		</tr>
			      		<tr>
			      			<td class="t">설명</td>
			      			<td><input type="text" id="text-description" name="desc"></td>
			      		</tr>
			      		<tr>
			      			<td class="s">&nbsp;</td>
			      			<td><input type="submit" value="카테고리 추가"></td>
			      		</tr>
			      	
			      	</table>
		      	</form> 
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>