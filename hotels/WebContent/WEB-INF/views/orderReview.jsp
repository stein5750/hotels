<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
	<meta charset="utf-8"/>
	<title>Booking</title>
	<style>
		<%@include file="/resources/css/style.css"%>
	</style>
</head>
<body>
	<table class="center">
		<tr>
			<th class="h1">
				Order review
			</th>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td>
				<table class="border fixed">
					<tr class="tableheader">
					  	<th class="no-side-border" colspan="2">Customer</th>
					</tr>
					<tr>
						<td class="no-side-border-borderless">Customer id</td>
						<td class="no-side-border-borderless">
							<c:out value="${order.customer.id}" />
						</td>
					</tr>
					<tr>
						<td class="no-side-border-borderless">Name</td>
						<td class="no-side-border-borderless">
							<c:out value="${order.customer.name}" />
						</td>
					</tr>
					<tr>
						<td class="no-side-border-borderless">Telephone number</td>
						<td class="no-side-border-borderless">
							<c:out value="${order.customer.phoneNumber}" />
						</td>
					</tr>
					<tr>
						<td class="no-side-border-borderless">Email address</td>
						<td class="no-side-border-borderless">
							<a href="mailto:${order.customer.emailAddress}">
								<c:out value="${order.customer.emailAddress}" />
							</a>
						</td>
					</tr>
					<tr>
						<td class="no-side-border-borderless">Address</td>
						<td class="no-side-border-borderless address">
							<c:out value="${order.customer.address}" />
						</td>
					</tr>
					<tr class="tableheader">
					  <th class="no-side-border" colspan="2">Dates</th>
					</tr>
					<tr>
						<td class="no-side-border-borderless">From</td>
						<td class="no-side-border-borderless">
							<c:out value="${order.dates.fromDate}" />
						</td>
					</tr>
					<tr>
						<td class="no-side-border-borderless">To</td>
						<td class="no-side-border-borderless">
							<c:out value="${order.dates.toDate}" />
						</td>
					</tr>
					<tr class="tableheader">
					  <th class="no-side-border" colspan="2">Hotel</th>
					</tr>
					<tr>
						<td class="no-side-border-borderless">Hotel name</td>
						<td class="no-side-border-borderless">
							<c:out value="${order.hotel.name}" />
						</td>
					</tr>
					<tr>
						<td class="no-side-border-borderless">Telephone number</td>
						<td class="no-side-border-borderless">
							<c:out value="${order.hotel.phoneNumber}" />
						</td>
					</tr>
					<tr>
						<td class="no-side-border-borderless">Hotel email address</td>
						<td class="no-side-border-borderless">
							<a href="mailto:${order.hotel.emailAddress}">
								<c:out value="${order.hotel.emailAddress}" />
							</a>
						</td>
					</tr>
					<tr>
						<td class="no-side-border-borderless">Hotel address</td>
						<td class="no-side-border-borderless address">
							<c:out value="${order.hotel.address}" />
						</td>
					</tr>					
					<tr class="tableheader">
					  <th class="no-side-border" colspan="2">Room</th>
					</tr>
					<tr>
						<td class="no-side-border-borderless">Room number</td>
						<td class="no-side-border-borderless">
							<c:out value="${order.room.roomNumber}" />
						</td>
					</tr>
					<tr>
						<td class="no-side-border-borderless">Type</td>
						<td class="no-side-border-borderless">
							<c:out value="${order.room.roomType}" />
						</td>
					</tr>
					<tr>
						<td class="no-side-border-borderless">Price per day</td>
						<td class="no-side-border-borderless">
							<c:out value="${order.room.roomPrice}" />
						</td>
					</tr>
					<tr class="tableheader">
					  <th class="no-side-border" colspan="2">Price</th>
					</tr>							
					<tr>
						<td class="no-side-border-borderless">Total price</td>
						<td class="no-side-border-borderless">
							<c:out value="${order.totalPrice}" />
						</td>
					</tr>							
				</table>
			</td>
		</tr>
		<tr align="center">
			<td>
				<div class="line">
					<form class="inlineForm" name="goOrderSave" method="POST" action="${pageContext.request.contextPath}/servlet/order/order">
						<input type="submit" name="action" value="Save order" />
					</form>
					<form class="inlineForm" name="goOrderEdit" method="POST" action="${pageContext.request.contextPath}/servlet/order/order">
						<input type="submit" name="action" value="Edit order" />
					</form>
					<form class="inlineForm" name="goOrderCancel" method="POST" action="${pageContext.request.contextPath}/servlet/order/order">
						<input type="submit" name="action" value="Cancel order" />
					</form>		
				</div>
			</td>
		</tr>
		<tr>
			<td>
				<div class="error">
					<!-- Showing field errors from the last validation before sending to database. -->
					${requestScope['error.fieldErrors']}
					<!-- Showing errors while saving to the database -->
					${requestScope['error.databaseError']}
				</div>
			</td>
		</tr>	
	</table>
</body>
</html>