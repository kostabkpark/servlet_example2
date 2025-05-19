<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div>
		<h2>${news.title}</h2>
		<hr>
		<p><img src="../../${news.img}" alt="image"></p>
		<p>${news.date}</p>
		<p>${news.content}</p>
	</div>

</body>
</html>