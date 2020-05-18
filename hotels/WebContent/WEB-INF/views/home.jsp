<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
	<meta charset="utf-8"/>
	<title>Booking</title>
	<style>
		<%@include file="/resources/css/style.css"%>
	</style>
	<script>
		<%@include file="/resources/js/visibility.js"%>
	</script>
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
				<table class="border">
					<tr class="tableheader">
					  	<td class="no-side-border" colspan="2">Booking</td>
					</tr>
					<tr>
						<td class="no-side-border left-column no-border-right">
							<h3>Customer:</h3>
							<p>${order.customer.name}</p>
						</td>
						<td class="no-side-border no-border-left">
							<form name="goCustomerSearch" method="GET" action="${pageContext.request.contextPath}/servlet/customer/customer">
								<input
									class="stretchToWidth" 
									type="submit" 
									name="action" 
									value="Search" 
								/>
							</form>	
							<form name="goCustomerCreate" method="GET" action="${pageContext.request.contextPath}/servlet/customer/customer">
								<input
									class="stretchToWidth" 
									type="submit" 
									name="action" 
									value="Create" 
								/>
							</form>								
							<form name="goCustomerShowDetails" method="POST" action="${pageContext.request.contextPath}/servlet/customer/customer">
								<input
									id="customerShowDetailsInput" 
									class="stretchToWidth" 
									type="submit" 
									name="action" 
									value="Details" 
								/>
							</form>
							<!-- Disable input if not all criterias are met. jsp > js > css -->
							<c:if test="${empty order.customer}">
								<script>
									disableInput("customerShowDetailsInput");
								</script>
							</c:if>											
							<form name="goCustomerShowPreviousOrdersForCustomer" method="POST" action="${pageContext.request.contextPath}/servlet/order/order">
								<input
									id="customerShowOrdersInput" 
									class="stretchToWidth" 
									type="submit" 
									name="action" 
									value="Orders" 
								/>
							</form>	
							<!-- Disable input if not all criterias are met. jsp > js > css -->
							<c:if test="${empty order.customer}">
								<script>
									disableInput("customerShowOrdersInput");
								</script>
							</c:if>										
							<div class="error">
								${requestScope['error.noCustomerSelected']}
							</div>
						</td>
					</tr>
					
					<tr>					
						<td class="no-side-border left-columnn no-border-right">
							<h3>Dates:</h3>
							<p>
								From date:
								<c:out value="${order.dates.fromDate}">Please select a date</c:out>
							</p>
							<p>
								To date:
								<c:out value="${order.dates.toDate}">Please select a date</c:out>
							</p>
						</td>
						<td class="no-side-border no-border-left">
							<form name="goDatesSelcarchForm" method="GET" action="${pageContext.request.contextPath}/servlet/dates/dates">
								<input
									class="stretchToWidth" 
									type='submit' 
									name="action" 
									value="Search" 
								/>
							</form>	
						</td>
					</tr>
					
					<tr>
						<td class="no-side-border left-column no-border-right">
							<h3>Hotel:</h3>
							<c:out value="${order.hotel.name}">Please select a hotel</c:out>
							<div class="error">
								${requestScope['error.noHotelSelected']}
							</div>	
						</td>
										
						<td class="no-side-border no-border-left">
							<form name="goHotelShowListForm" method="GET" action="${pageContext.request.contextPath}/servlet/hotel/hotel">
								<input
									class="stretchToWidth" 
									type='submit' 
									name="action" 
									value="Search" 
								/>
							</form>																				
							<form name="goHotelDetails" method="GET" method="GET" action="${pageContext.request.contextPath}/servlet/hotel/hotel">
								<input type="hidden" name="hotelId"  value="${order.hotel.id}" />
								<input 
									id="hotelDetailsInput"
									class="stretchToWidth"
									type="submit"
									name="action"
									value="Details" />															
							</form>
							<!-- display input button for showing details about a hotel only if a hotel is selected. jsp > js > css -->
							<c:if test="${empty order.hotel}">
								<script>
									disableInput("hotelDetailsInput");
								</script>
							</c:if>		
						</td>
					</tr>
					
					<tr>
						<td class="no-side-border left-column no-border-right">
							<h3>Room:</h3>
							<p>${order.room.roomNumber}</p>
						</td>		
						<td class="no-side-border no-border-left">
							<form name="goRoomSearchForm" method="POST" action="${pageContext.request.contextPath}/servlet/room/room">
								<input
									id="roomSearchInput" 
									class="stretchToWidth" 
									type="submit" 
									name="action" 
									value="Search" 
								/>
							</form>	
							<!-- display input button for showing details about a hotel only if a hotel is selected. jsp > js > css -->
							<c:if test="${empty order.hotel || empty order.dates}">
								<script>
									disableInput("roomSearchInput");
								</script>
							</c:if>	
							<form name="goRoomShowDetails" method="POST" action="${pageContext.request.contextPath}/servlet/room/room">
								<input 
									id="roomDetailsInput" 
									class="stretchToWidth" 
									type="submit" 
									name="action" 
									value="Details" 
								/>
							</form>	
							<!-- display input button for showing details about a room only if a room is selected. jsp > js > css -->
							<c:if test="${empty order.room}">
								<script>
									disableInput("roomDetailsInput");
								</script>
							</c:if>	
						</td>
					</tr>
					
					<tr>
					<td class="no-side-border left-column no-border-right">
						<h3>Total price:</h3>
						<p>${order.totalPrice}</p>
					</td>
						<td class="no-side-border no-border-left">
						</td>
					</tr>										
				</table>
			</td>
		</tr>		
		<tr align="center">
			<td>
				<div class="line">
					<form class="inlineForm" name="goOrderCreate" method="POST" action="${pageContext.request.contextPath}/servlet/order/order">
						<input
							id="orderCreateInput" 
							class="" 
							type="submit" 
							name="action" 
							value="Create order" 
						/>
					</form>			
					<!-- Disable input if not all criterias are met. jsp > js > css -->
					<c:if test="${empty order.customer || empty order.hotel || empty order.dates || empty order.room}">
						<script>
							disableInput("orderCreateInput");
						</script>
					</c:if>	
											
					<form class="inlineForm" name="clearAll" method="POST" action="${pageContext.request.contextPath}/servlet/home/home">
						<input
							id="" 
							class="" 
							type="submit" 
							name="action" 
							value="Clear all" 
						/>
					</form>			
				</div>
			</td>
		</tr>
		<tr>
			<td>
				<div class="error">
					<!-- Showing field errors from validation before going to orderReview. -->
					${requestScope['error.fieldErrors']}
					${requestScope['error.preRequirement']}
				</div>
			</td>
		</tr>
	</table>
</body>
</html>