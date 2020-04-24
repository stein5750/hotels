
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
	<meta charset="utf-8"/>
	<title>Rooms</title>
	<style>
		<%@include file="/WEB-INF/css/style.css"%>
	</style>
</head>
<body>
	<form name="dates" method="GET" action="${pageContext.request.contextPath}/servlet/c/rooms">
		<table class="center">
			<tr>
				<td class="heading1">
					Available rooms
				</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>
					<table class="border" style="width:100%">
						<tr class="tableheader">
						  <td class="th"><c:out value="${requestScope.hotelName}" /></td>
						</tr>
						<tr>
							<td class="td">
								<div class="line">
									<label class="two-columns-left">From date:</label>
									<input class="two-columns-right" type='datetime-local' name="fromDate" type="text" value="${requestScope.fromDate}"/>
								</div>
								<div class="error">
									${requestScope['error.fromDate']}
									${requestScope['error.fromDateIsPast']}
								</div>
							</td>
						</tr>
						
						<tr>
							<td class="td">
								<div class="line">
									<label class="two-columns-left">To date:</label>
									<input class="two-columns-right" type='datetime-local' name="toDate" type="text" value="${requestScope.toDate}" />
								</div>
								<div class="error">
									${requestScope['error.toDate']}
								</div>
							</td>
						</tr>

					</table>
				</td>
			</tr>
			<tr align="center">
				<td>
					<div class="line">
						<button onclick="location.href='${pageContext.request.contextPath}/servlet/c/home'" type="button">Home</button>
						<button type="button" name="back" onclick="history.back()">back</button>
							<input type="hidden" name="hotelId" value="${requestScope.hotelId}" />
							<input type="hidden" name="customerId" value="${requestScope.customerId}" />
							<input type="submit" value="Find available rooms" />

					</div>
				</td>
			</tr>
		</table>
	</form>


	<!-- Display list of empty rooms if the list itself is not empty -->
	<c:if test="${not empty requestScope.roomList}">
		<table class="border center">
			<tr class="tableheader">
			  <th class="th">Room number</th>
				<th class="th">Room type</th>
				<th class="th">Room price per day</th>
				<th class="th">Action</th>
			</tr>
			<c:forEach items="${requestScope.roomList}" var="room">
				<tr>
					<td class="td"><c:out value="${room.roomNumber}" /></td>
					<td class="td"><c:out value="${room.roomType}" /></td>
					<td class="td"><c:out value="${room.roomPrice}" /></td>
					<td class="td">
						<form name="orderRoom" method="GET" action="${pageContext.request.contextPath}/servlet/c/newOrder">
							<input type="hidden" name="customerId" value="${requestScope.customerId}" />
							<input type="hidden" name="hotelId" value="${requestScope.hotelId}" />
							<input type="hidden" name="fromDate" value="${requestScope.fromDate}" />
							<input type="hidden" name="toDate" value="${requestScope.toDate}" />
							<input type="hidden" name="roomNumber" value="${room.roomNumber}" />
							<input type="submit" value="Select room" />
						</form>
					</td>
				</tr>
			</c:forEach>
		</table>
	</c:if>
</body>
</html>