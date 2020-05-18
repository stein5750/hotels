<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
	<head>
		<title>Success</title>
		<style>
			<%@include file="/resources/css/style.css"%>
		</style>
	</head>
	<body>
			<table class="center">
				<tr>
					<th class="h1">
						Order was successfully saved
					</th>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>
						<table class="border" style="width:100%;">
							<tr class="tableheader">
							  <th class="no-side-border">Order id</th>
							</tr>
							<tr>
								<td class="no-side-border">
									<c:out value="${requestScope.orderId}" />							
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr align="center">
					<td>
						<form class="inlineForm" name="goShowOrderButton" method="POST" action="${pageContext.request.contextPath}/servlet/order/order">
							<input type="hidden" name="orderId" value="${requestScope.orderId}" />
							<input type="submit" name="action" value="Show order" />
						</form>
						<button onclick="location.href='${pageContext.request.contextPath}/servlet/home/home'" type="button">Home</button>
					</td>
				</tr>
			</table>
	
	</body>
</html>