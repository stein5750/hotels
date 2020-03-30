<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
	<title>Hotel contact info</title>
	<style><%@include file="/WEB-INF/css/style.css"%></style>
</head>
<body>

		<form name="Hotelinfo" method="GET" action="${pageContext.request.contextPath}/servlet/mc/redirect">
		<table align="left" style="padding-left: 300px;">
			<tr>
				<td
					style="font-family: 'arial'; font-size: 16px; font-weight: bold;">Hotel contact info</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>
					<table class="border" cellpadding="10">
						<tr class="tableheader">
						  	<th class="th">Hotel Id</th>
							<th class="th">Hotel name</th>
							<th class="th">Hotel address</th>
							<th class="th">Hotel email</th>
							<th class="th">Hotel phonenumber</th>
							<th class="th">Action</th>
						</tr>
						<tr>
							<td class="td"><c:out value="${hotel.id}" /></td>
							<td class="td"><c:out value="${hotel.name}" /></td>
							<td class="td"><c:out value="${hotel.address}" /></td>
							<td class="td">
								<a href="mailto:${hotel.emailAddress}">${hotel.emailAddress}</a>
							</td>
							<td class="td"><c:out value="${hotel.phoneNumber}" /></td>
							<td class="td"><a
									href="${pageContext.request.contextPath}/servlet/mc/rooms?hotelId=${hotel.id}"
									style="color: green">Find available Rooms</a></td>
							</tr>
					</table>
				</td>
			</tr>
			<tr align="center">
				<td><input type="submit" value="homepage" /></td>
			</tr>
		</table>
	</form>
</body>
</html>