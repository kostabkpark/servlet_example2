<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
	<style>
		.error {
			background-color: red;
		}
	</style>

</head>
<body>
	<h2>뉴스 목록</h2>
	<hr>
	<ul>
		<c:forEach items="${newsList}" var="news" varStatus="status">
			<li>
				<a href="/news?action=view&aid=${news.aid}">[${status.count}]</a>
				<a href="/news?action=view&aid=${news.aid}">${news.title}</a>
				<span>${news.date}</span>
				<a href="/news?action=delNews&aid=${news.aid}">삭제</a>
			</li>		
		</c:forEach>
	</ul>
	<c:if test="${error != null}">
		<div class="error">에러 발생 : ${error}</div>
	</c:if>

	<hr>
	<h2>뉴스 등록</h2>
	<hr>
	<form action="/news?action=addNews" method="post" enctype="multipart/form-data">
		<label>뉴스 제목</label>
		<input type="text" name="title"><br>
		<label>뉴스 내용</label>
		<textarea rows="5" cols="20" name="content"></textarea><br>
		<input type="file" name="img" accept="image/*">
		<input type="submit" value="전송">
	</form>
</body>
</html>