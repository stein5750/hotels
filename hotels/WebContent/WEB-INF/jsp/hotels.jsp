<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
	<title>Hotels</title>
	<style><%@include file="/WEB-INF/css/style.css"%></style>
</head>
<body>
		<table class="outer">
			<tr>
				<td
					style="font-family: 'arial'; font-size: 16px; font-weight: bold;">Hotels</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>
					<table class="border">
						<tr class="tableheader">
						  <th class="th">Hotel Id</th>
							<th class="th">Hotel name</th>
							<th class="th">Contact info</th>
							<th class="th">Action</th>
						</tr>
						<c:forEach items="${list}" var="hotel">
							<tr>
								<td class="td"><c:out value="${hotel.id}" /></td>
								<td class="td"><c:out value="${hotel.name}" /></td>
								<td class="td">
									<a href="${pageContext.request.contextPath}/servlet/mc/?action=hotelInfo&hotelId=${hotel.id}" 
									style="color: green">Contact info</a>
								</td>
								<td class="td">	
									<a href="${pageContext.request.contextPath}/servlet/mc/rooms?hotelId=${hotel.id}" 
									style="color: green">Find available Rooms</a>
								</td>
							</tr>
						</c:forEach>
					</table>
				</td>
			</tr>
		</table>

</body>
</html>