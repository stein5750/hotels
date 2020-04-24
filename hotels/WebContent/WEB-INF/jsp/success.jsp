<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
	<head>
		<title>Success</title>
		<style>
			<%@include file="/WEB-INF/css/style.css"%>
		</style>
	</head>
	<body>
			<table class="center">
				<tr>
					<td class="heading1">
						Order was successfully saved
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>
						<table class="border" style="width:100%;">
							<tr class="tableheader">
							  <th class="th">Order id</th>
							</tr>
							<tr>
								<td class="td">
									<c:out value="${requestScope.orderId}" />							
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr align="center">
					<td>
						<form name="homeButton" method="GET" action="${pageContext.request.contextPath}/servlet/c/home">
							<input type="submit" value="home" />
						</form>
					</td>
				</tr>
			</table>
	
	</body>
</html>