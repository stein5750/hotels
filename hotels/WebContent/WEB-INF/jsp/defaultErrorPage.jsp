<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Error</title>
</head>
<body>
	<h1>An error occurred</h1>
	<p>
		Error:<br />
		${requestScope.errorMsg}
	</p>
	<div class="line">
		<button onclick="location.href='${pageContext.request.contextPath}/servlet/mc/home'" type="button">Home</button>
		<button type="button" name="back" onclick="history.back()">back</button>
	</div>
</body>
</html>