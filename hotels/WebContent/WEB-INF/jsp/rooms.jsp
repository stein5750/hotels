
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
	<meta charset="utf-8"/>
	<title>Rooms</title>
	<style><%@include file="/WEB-INF/css/style.css"%></style>
</head>
<body>
	<form name="dates" method="GET" action="${pageContext.request.contextPath}/servlet/mc/rooms">
		<input type="hidden" name="hotelId" value="${data.hotel.id}" />
		
		<table class="outer">
			<tr>
				<td
					style="font-family: 'arial'; font-size: 16px; font-weight: bold;">Rooms
				</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>
					<table class="border" style="width:100%">
						<tr class="tableheader">
						  <td class="th"><c:out value="${data.hotel.name}" /></td>
						</tr>
						<tr>
							<td class="td">
								<div class="line">
									<label class="two-columns-left">From date:</label>
									<input class="two-columns-right" type='datetime-local' name="fromDate" type="text" value="${data.fromDate}"/>
								</div>
							</td>
						</tr>
						<tr>
							<td class="td">
								<div class="line">
									<label class="two-columns-left">To date:</label>
									<input class="two-columns-right" type='datetime-local' name="toDate" type="text" value="${data.toDate}" />
								</div>
							</td>
						</tr>

					</table>
				</td>
			</tr>
			<tr align="center">
				<td><input type="submit" value="Find available rooms" /></td>
			</tr>
		</table>
	</form>


	<!-- Display list of empty rooms if the list itself is not empty -->
	<c:if test="${not empty data.rooms}">
		<table class="border outer">
			<tr class="tableheader">
			  <th class="th">Room number</th>
				<th class="th">Room type</th>
				<th class="th">Room price per day</th>
				<th class="th">Action</th>
			</tr>
			<c:forEach items="${data.rooms}" var="room">
				<tr>
					<td class="td"><c:out value="${room.roomNumber}" /></td>
					<td class="td"><c:out value="${room.roomType}" /></td>
					<td class="td"><c:out value="${room.roomPrice}" /></td>
					<td class="td">
						<a href="${pageContext.request.contextPath}/servlet/mc/findCustomer?hotelId=${data.hotel.id}&fromDate=${data.fromDate}&toDate=${data.toDate}&roomNumber=${room.roomNumber}" 
						style="color: green">order room</a>
					</td>
				</tr>
			</c:forEach>
		</table>
	</c:if>
</body>
</html>