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
				<table class="border center">
					<tr class="tableheader">
					 	<th class="no-side-border">Hotel</th>
					  	<th class="no-side-border">Room number</th>
						<th class="no-side-border">Room type</th>
						<th class="no-side-border">Room price per day</th>
					</tr>
					<tr>
						<td class="no-side-border"><c:out value="${order.hotel.name}" /></td>
						<td class="no-side-border"><c:out value="${order.room.roomNumber}" /></td>
						<td class="no-side-border"><c:out value="${order.room.roomType}" /></td>
						<td class="no-side-border"><c:out value="${order.room.roomPrice}" /></td>
					</tr>
				</table>
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