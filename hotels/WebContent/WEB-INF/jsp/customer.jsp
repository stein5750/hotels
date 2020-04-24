
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
	<meta charset="utf-8"/>
	<title>Customer</title>
	<style>
		<%@include file="/WEB-INF/css/style.css"%>
	</style>
</head>
<body>
	<table class="center">
		<tr>
			<td class="heading1">
				Customer
			</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td>
				<table class="border center" style="width: 100%;">
					<tr class="tableheader">
						<th class="th">Name</th>
						<th class="th">Phone Number</th>
						<th class="th">E-mail address</th>
						<th class="th">Address</th>						
					</tr>
					<tr>
						<td class="td"><c:out value="${customer.name}" /></td>
						<td class="td"><c:out value="${customer.phoneNumber}" /></td>
						<td class="td"><c:out value="${customer.emailAddress}" /></td>
						<td class="td"><c:out value="${customer.address}" /></td>
					</tr>
				</table>
			</td>
		</tr>
		<tr align="center">
			<td>
				<div class="line">
					<button onclick="location.href='${pageContext.request.contextPath}/servlet/c/home'" type="button">Home</button>
					<button type="button" name="back" onclick="history.back()">back</button>
					<form style="display: inline-block;" name="newOrderButton" method="GET" action="${pageContext.request.contextPath}/servlet/c/hotels">
							<input type="hidden" name="customerId" value="${customer.id}" />						
							<input type="submit" value="Select customer" />
					</form>
				</div>
			</td>
		</tr>
	</table>
	<br />
	
	<c:choose>
		<c:when test="${empty requestScope.orders}">
			 <p style="text-align:center">
				There are no Previous orders by the customer.
			</p>
		</c:when>
		<c:otherwise>
			<table class="center">
				<tr>
					<td style="font-family: 'arial'; font-size: 16px; font-weight: bold;">
						Other orders by the customer
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>
						<!-- Display list of orders -->
						<table class="border center">
							<tr class="tableheader">
								<th class="th">Created</th>
								<th class="th">Hotel</th>
								<th class="th">Room</th>
								<th class="th">From</th>
								<th class="th">To</th>
								<th class="th">Total price</th>
								<th class="th">Action</th>
							</tr>
							<c:forEach items="${requestScope.orders}" var="order">
								<tr>				
									<td class="td"><c:out value="${order.orderCreatedDateTime}" /></td>
									<td class="td"><c:out value="${order.reservedHotel.name}" /></td>
									<td class="td"><c:out value="${order.reservedRoom.roomNumber}" /></td>
									<td class="td"><c:out value="${order.fromDate}" /></td>
									<td class="td"><c:out value="${order.toDate}" /></td>
									<td class="td"><c:out value="${order.totalPrice}" /></td>
									<td class="td">
										<form name="deleteButton" method="GET" action="${pageContext.request.contextPath}/servlet/c/deleteOrder">
											<input type="hidden" name="customerId" value="${customer.id}" />
											<input type="hidden" name="orderId" value="${order.orderId}" />
											<input type="submit" value="Delete" />
										</form>
									</td>
								</tr>
							</c:forEach>
						</table>
					</td>
				</tr>
			</table>
		</c:otherwise>
	</c:choose>
</body>
</html>