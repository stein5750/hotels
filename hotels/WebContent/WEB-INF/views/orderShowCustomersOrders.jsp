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
				Booking
			</th>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td>
				<c:choose>
					<c:when test="${empty orders}">					
						<table class="borderless" style="min-width: 565px;">
							<tr>
								<td>
						 			<p style="text-align:left">
										<c:out value="There are no Previous orders by ${customer.name}." />
									</p>
								</td>	
								<td>
									<div class="line" style="float:right;">
										<button onclick="location.href='${pageContext.request.contextPath}/servlet/home/home'" type="button">Home</button>
										<button type="button" name="back" onclick="history.back()">back</button>
									</div>
								</td>
							</tr>
						</table>	
					</c:when>
					<c:otherwise>
						<table class="borderless" style="min-width: 565px;">
							<tr>
								<td>
						 			<p style="text-align:left">
										<c:out value="Previous orders by ${customer.name}." />
									</p>
								</td>
								<td>
									<div class="line" style="float:right;">
										<button onclick="location.href='${pageContext.request.contextPath}/servlet/home/home'" type="button">Home</button>
										<button type="button" name="back" onclick="history.back()">back</button>
									</div>
								</td>									
							</tr>
						</table>
						<tr>
							<td>&nbsp;</td>
						</tr>
						<tr>
							<td>
								<table style="min-width: 565px;">		
									<tr>
										<td>
											<!-- Display list of orders -->
											<div class="scrollableVertically">
												<table class="border-collapse-with-div" >
													<tr class="tableheader">
														<th class="no-side-border no-sideborder">Created</th>
														<th class="no-side-border no-sideborder">Hotel</th>
														<th class="no-side-border no-sideborder">Room</th>
														<th class="no-side-border no-sideborder">From</th>
														<th class="no-side-border no-sideborder">To</th>
														<th class="no-side-border no-sideborder">Total price</th>
														<th class="no-side-border no-sideborder">Action</th>
													</tr>
													<c:forEach items="${requestScope.orders}" var="listedOrder">
														<tr>				
															<td class="no-side-border no-sideborder"><c:out value="${listedOrder.orderCreatedDateTime}" /></td>
															<td class="no-side-border no-sideborder"><c:out value="${listedOrder.hotel.name}" /></td>
															<td class="no-side-border no-sideborder"><c:out value="${listedOrder.room.roomNumber}" /></td>
															<td class="no-side-border no-sideborder"><c:out value="${listedOrder.dates.fromDate}" /></td>
															<td class="no-side-border"><c:out value="${listedOrder.dates.toDate}" /></td>
															<td class="no-side-border"><c:out value="${listedOrder.totalPrice}" /></td>
															<td class="no-side-border">
																<form class="inlineForm" name="goShowOrderButton" method="POST" action="${pageContext.request.contextPath}/servlet/order/order">
																	<input type="hidden" name="orderId" value="${listedOrder.orderId}" />
																	<input type="submit" name="action" value="Show order" />
																</form>
																<form class="inlineForm" name="deleteButton" method="POST" action="${pageContext.request.contextPath}/servlet/order/order">
																	<input type="hidden" name="orderId" value="${listedOrder.orderId}" />
																	<input type="submit" name="action" value="Delete" />
																</form>
															</td>
														</tr>
													</c:forEach>
												</table>
											</div>
										</td>
									</tr>
								</table>
							</td>
						</tr>	
					</c:otherwise>
				</c:choose>			
			</td>
		</tr>
	</table>
</body>
</html>