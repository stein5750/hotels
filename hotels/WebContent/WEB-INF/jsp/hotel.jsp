<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
	<title>${requestScope.hotel.name}</title>
	<style>
		<%@include file="/WEB-INF/css/style.css"%>
	</style>
</head>
	<body>
		<table class="center">
			<tr>
				<td class="heading1">
					${requestScope.hotel.name}
				</td>
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
							<th class="th">Hotel address</th>
							<th class="th">Hotel email</th>
							<th class="th">Hotel phonenumber</th>
							<th class="th">Action</th>
						</tr>
						<tr>
							<td class="td"><c:out value="${requestScope.hotel.id}" /></td>
							<td class="td"><c:out value="${requestScope.hotel.name}" /></td>
							<td class="td"><c:out value="${requestScope.hotel.address}" /></td>
							<td class="td">
								<a href="mailto:${requestScope.hotel.emailAddress}">${requestScope.hotel.emailAddress}</a>
							</td>
							<td class="td"><c:out value="${requestScope.hotel.phoneNumber}" /></td>
							<td class="td">
								<form name="selectHotel" method="GET" action="${pageContext.request.contextPath}/servlet/mc/rooms">
									<input type="hidden" name="customerId"   value="${requestScope.customerId}" />
									<input type="hidden" name="hotelId"   value="${hotel.id}" />
									<input type="submit" value="Select hotel" />
								</form>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr align="center">
				<td>
					<div class="line">
						<button onclick="location.href='${pageContext.request.contextPath}/servlet/mc/home'" type="button">Home</button>
						<button type="button" name="back" onclick="history.back()">back</button>
					</div>
				</td>
			</tr>
		</table>
	</body>
</html>