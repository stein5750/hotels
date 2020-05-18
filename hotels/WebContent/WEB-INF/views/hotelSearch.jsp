<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
	<title>Booking</title>
	<style>
		<%@include file="/resources/css/style.css"%>
	</style>
</head>
<body>
	<table class="center">
		<tr>
			<th class="h1">
				Booking
			</th>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td>
				<div class="scrollableVertically">
					<table class="border-collapse-with-div" >
						<tr class="tableheader">
							<th class="no-side-border" colspan="2">Hotel</th>						
						</tr>
						<c:forEach items="${requestScope.hotels}" var="hotel">
							<tr>
								<td class="no-side-border left-column no-border-right"><c:out value="${hotel.name}" /></td>
								<td class="no-side-border no-border-left">
									<form class="inlineForm" name="hotelDetails" method="GET" action="${pageContext.request.contextPath}/servlet/hotel/hotel">
										<input type="hidden" name="hotelId"  value="${hotel.id}" />
										<input type="submit" name="action" value="Details" />
									</form>
									<form class="inlineForm" name="selectHotel" method="POST" action="${pageContext.request.contextPath}/servlet/hotel/hotel">
										<input type="hidden" name="hotelId"   value="${hotel.id}" />
										<input type="submit" name="action" value="Select" />
									</form>
								</td>
							</tr>
						</c:forEach>
					</table>
				</div>
			</td>
		</tr>
		<tr align="center">
			<td>
				<div class="line">
					<button onclick="location.href='${pageContext.request.contextPath}/servlet/home/home'" type="button">Home</button>
					<button type="button" name="back" onclick="history.back()">back</button>
				</div>
			</td>
		</tr>
	</table>
</body>
</html>