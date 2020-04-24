<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
	<title>Hotels</title>
	<style>
		<%@include file="/WEB-INF/css/style.css"%>
	</style>
</head>
<body>
	<table class="center">
		<tr>
			<td class="heading1">
				Hotels
			</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td>
				<table class="border">
					<tr class="tableheader">
					  <th class="th">Hotel Id</th>
						<th class="th">Hotel name</th>
						<th class="th">Action</th>
					</tr>
					<c:forEach items="${requestScope.hotels}" var="hotel">
						<tr>
							<td class="td"><c:out value="${hotel.id}" /></td>
							<td class="td"><c:out value="${hotel.name}" /></td>
							<td class="td">
								<form name="hotelDetails" method="GET" action="${pageContext.request.contextPath}/servlet/c/hotel">
									<input type="hidden" name="customerId" value="${requestScope.customerId}" />
									<input type="hidden" name="hotelId"    value="${hotel.id}" />
									<input type="submit" value="Hotel details" />
								</form>
							</td>
						</tr>
					</c:forEach>
				</table>
			</td>
		</tr>
		<tr align="center">
			<td>
				<div class="line">
					<button onclick="location.href='${pageContext.request.contextPath}/servlet/c/home'" type="button">Home</button>
					<button type="button" name="back" onclick="history.back()">back</button>
				</div>
			</td>
		</tr>
	</table>
</body>
</html>