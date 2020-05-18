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
				Order
			</th>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td>
				<table class="border fixed">
					<tr class="tableheader">
					  <th class="no-side-border" colspan="2">Order</th>
					</tr>
					<tr>
						<td class="no-side-border-borderless">Order id</td>
						<td class="no-side-border-borderless">
							<c:out value="${displayedOrder.orderId}" />
						</td>
					</tr>					
					<tr>
						<td class="no-side-border-borderless">Order created</td>
						<td class="no-side-border-borderless">
							<c:out value="${displayedOrder.orderCreatedDateTime}" />
						</td>
					</tr>											
					<tr class="tableheader">
					  <th class="no-side-border" colspan="2">Customer</th>
					</tr>
					<tr>
						<td class="no-side-border-borderless">Customer id</td>
						<td class="no-side-border-borderless">
							<c:out value="${displayedOrder.customer.id}" />
						</td>
					</tr>
					<tr>
						<td class="no-side-border-borderless">Name</td>
						<td class="no-side-border-borderless">
							<c:out value="${displayedOrder.customer.name}" />
						</td>
					</tr>
					<tr>
						<td class="no-side-border-borderless">Telephone number</td>
						<td class="no-side-border-borderless">
							<c:out value="${displayedOrder.customer.phoneNumber}" />
						</td>
					</tr>
					<tr>
						<td class="no-side-border-borderless">Email address</td>
						<td class="no-side-border-borderless">
							<a href="mailto:${displayedOrder.customer.emailAddress}">
								<c:out value="${displayedOrder.customer.emailAddress}" />
							</a>
						</td>
					</tr>
					<tr>
						<td class="no-side-border-borderless">Address</td>
						<td class="no-side-border-borderless address">
							<c:out value="${displayedOrder.customer.address}" />
						</td>
					</tr>
					<tr class="tableheader">
					  <th class="no-side-border" colspan="2">Dates</th>
					</tr>
					<tr>
						<td class="no-side-border-borderless">From</td>
						<td class="no-side-border-borderless">
							<c:out value="${displayedOrder.dates.fromDate}" />
						</td>
					</tr>
					<tr>
						<td class="no-side-border-borderless">To</td>
						<td class="no-side-border-borderless">
							<c:out value="${displayedOrder.dates.toDate}" />
						</td>
					</tr>
					<tr class="tableheader">
					  <th class="no-side-border" colspan="2">Hotel</th>
					</tr>
					<tr>
						<td class="no-side-border-borderless">Hotel name</td>
						<td class="no-side-border-borderless">
							<c:out value="${displayedOrder.hotel.name}" />
						</td>
					</tr>
					<tr>
						<td class="no-side-border-borderless">Telephone number</td>
						<td class="no-side-border-borderless">
							<c:out value="${displayedOrder.hotel.phoneNumber}" />
						</td>
					</tr>
					<tr>
						<td class="no-side-border-borderless">Hotel email address</td>
						<td class="no-side-border-borderless">
							<a href="mailto:${displayedOrder.hotel.emailAddress}">
								<c:out value="${displayedOrder.hotel.emailAddress}" />
							</a>
						</td>
					</tr>
					<tr>
						<td class="no-side-border-borderless">Hotel address</td>
						<td class="no-side-border-borderless address">
							<c:out value="${displayedOrder.hotel.address}" />
						</td>
					</tr>					
					<tr class="tableheader">
					  <th class="no-side-border" colspan="2">Room</th>
					</tr>
					<tr>
						<td class="no-side-border-borderless">Room number</td>
						<td class="no-side-border-borderless">
							<c:out value="${displayedOrder.room.roomNumber}" />
						</td>
					</tr>
					<tr>
						<td class="no-side-border-borderless">Type</td>
						<td class="no-side-border-borderless">
							<c:out value="${displayedOrder.room.roomType}" />
						</td>
					</tr>
					<tr>
						<td class="no-side-border-borderless">Price per day</td>
						<td class="no-side-border-borderless">
							<c:out value="${displayedOrder.room.roomPrice}" />
						</td>
					</tr>
					<tr class="tableheader">
					  <th class="no-side-border" colspan="2">Price</th>
					</tr>							
					<tr>
						<td class="no-side-border-borderless">Total price</td>
						<td class="no-side-border-borderless">
							<c:out value="${displayedOrder.totalPrice}" />
						</td>
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