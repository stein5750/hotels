
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
	<meta charset="utf-8"/>
	<title>New order</title>
	<style>
		<%@include file="/WEB-INF/css/style.css"%>
	</style>
</head>
<body>
	<table class="center">
		<tr>
			<td class="heading1">
				New order
			</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td>
				<table class="border">
					<tr class="tableheader">
					  <td class="th" colspan="2">Customer</td>
					</tr>
					<tr>
						<td class="td">Id</td>
						<td class="td">${requestScope.customer.id}</td>
					</tr>
					<tr>
						<td class="td">Name</td>
						<td class="td">${requestScope.customer.name}</td>
					</tr>
					<tr>
					<tr>
						<td class="td">Phonenumber</td>
						<td class="td">${requestScope.customer.phoneNumber}</td>
					</tr>
					<tr>
						<td class="td">EmailAddress</td>
						<td class="td">
							<a href="mailto:${requestScope.customer.emailAddress}">${requestScope.customer.emailAddress}</a>
						</td>
					</tr>
					<tr>
						<td class="td">Address</td>
						<td class="td">${requestScope.customer.address}</td>
					</tr>
					<tr class="tableheader">
					  <td class="th" colspan="2">Hotel</td>
					</tr>
					<tr>
						<td class="td">Hotel name</td>
						<td class="td">${requestScope.hotel.name}</td>
					</tr>
					<tr>
						<td class="td">Hotel emailaddress</td>
						<td class="td">
							<a href="mailto:${requestScope.hotel.emailAddress}">${requestScope.hotel.emailAddress}</a>
						</td>
					</tr>
					<tr>
						<td class="td">Hotel address</td>
						<td class="td">${requestScope.hotel.address}</td>
					</tr>
					<tr class="tableheader">
					  <td class="th" colspan="2">Room</td>
					</tr>
					<tr>
						<td class="td">Room number</td>
						<td class="td">${requestScope.room.roomNumber}</td>
					</tr>
					<tr>
						<td class="td">Type</td>
						<td class="td">${requestScope.room.roomType}</td>
					</tr>
					<tr>
						<td class="td">Price per day</td>
						<td class="td">${requestScope.room.roomPrice}</td>
					</tr>
					<tr class="tableheader">
					  <td class="th" colspan="2">Dates</td>
					</tr>
					<tr>
						<td class="td">From</td>
						<td class="td">${requestScope.fromDate}</td>
					</tr>
					<tr>
						<td class="td">To</td>
						<td class="td">${requestScope.toDate}</td>
					</tr>
					<tr class="tableheader">
					  <td class="th" colspan="2">Price</td>
					</tr>							
					<tr>
						<td class="td">Total price</td>
						<td class="td">${requestScope.totalPrice}</td>
					</tr>							
				</table>
			</td>
		</tr>
		<tr align="center">
			<td>
				<div class="line">
					<button onclick="location.href='${pageContext.request.contextPath}/servlet/c/home'" type="button">Home</button>
					<button type="button" name="back" onclick="history.back()">back</button>
					<form style="display: inline-block;" name="save" method="GET" action="${pageContext.request.contextPath}/servlet/c/saveOrder">
						<input type="hidden" name="hotelId" value="${requestScope.hotel.id}" />
						<input type="hidden" name="fromDate" value="${requestScope.fromDate}" />
						<input type="hidden" name="toDate" value="${requestScope.toDate}" />
						<input type="hidden" name="roomNumber" value="${requestScope.room.roomNumber}" />
						<input type="hidden" name="customerId" value="${requestScope.customer.id}" />
						<input type="hidden" name="totalPrice" value="${requestScope.totalPrice}" />
						<input type="submit" value="Save order" />
					</form>
				</div>
			</td>
		</tr>
	</table>
</body>
</html>