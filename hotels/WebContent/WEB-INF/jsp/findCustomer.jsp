
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
	<meta charset="utf-8"/>
	<title>Find customer</title>
	<style><%@include file="/WEB-INF/css/style.css"%></style>
</head>
<body>

			
		<table class="outer">
			<tr>
				<td
					style="font-family: 'arial'; font-size: 16px; font-weight: bold;">Find Customer
				</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>
					<table class="border">
						<tr class="tableheader">
						  <td class="th">Find customer</td>
						</tr>

						<tr>
							<td class="td">
								<form name="findById" method="GET" action="${pageContext.request.contextPath}/servlet/mc/findCustomer">
									<input type="hidden" name="hotelId"    value="${data.hotel.id}" />
									<input type="hidden" name="fromDate"   value="${data.fromDate}" />
									<input type="hidden" name="toDate"     value="${data.toDate}" />
									<input type="hidden" name="roomNumber" value="${data.roomNumber}" />
									<div class="line">
										<label class="three-columns-left" for="id">Id</label>
										<input class="three-columns-middle" type='text' name="customerId" type="text" value="${data.customer.id}" />
										<input class="three-columns-right" type="submit" value="Search by id" />
									</div>
									</form>
								</td>
						</tr>
						

						<tr>
							<td class="td">
								<form name="findByName" method="GET" action="${pageContext.request.contextPath}/servlet/mc/findCustomer">
									<input type="hidden" name="hotelId"    value="${data.hotel.id}" />
									<input type="hidden" name="fromDate"   value="${data.fromDate}" />
									<input type="hidden" name="toDate"     value="${data.toDate}" />
									<input type="hidden" name="roomNumber" value="${data.roomNumber}" />
									<div class="line">
										<label class="three-columns-left" for="customerId">Name</label>
										<input class="three-columns-middle" type='text' name="name" type="text" value="${data.customer.name}" />
										<input class="three-columns-right" type="submit" value="Search by name" />
									</div>
									</form>
								</td>
						</tr>
						
						<tr>
							<td class="td">
								<form name="findByPhoneNumber" method="GET" action="${pageContext.request.contextPath}/servlet/mc/findCustomer">
									<input type="hidden" name="hotelId"    value="${data.hotel.id}" />
									<input type="hidden" name="fromDate"   value="${data.fromDate}" />
									<input type="hidden" name="toDate"     value="${data.toDate}" />
									<input type="hidden" name="roomNumber" value="${data.roomNumber}" />
									<div class="line">
										<label class="three-columns-left" for="customerId">Phone number</label>
										<input class="three-columns-middle" type="tel" name="phoneNumber" pattern="^(\+)?\d{3,15}$" title="An optional '+' sign and 3-15 digits" value="${data.customer.phoneNumber}" />
										<input class="three-columns-right" type="submit" value="Search by phoneNumber" />
									</div>
									</form>
								</td>
						</tr>
						
						<tr>
							<td class="td">
								<form name="findByEmailAddress" method="GET" action="${pageContext.request.contextPath}/servlet/mc/findCustomer">
									<input type="hidden" name="hotelId"    value="${data.hotel.id}" />
									<input type="hidden" name="fromDate"   value="${data.fromDate}" />
									<input type="hidden" name="toDate"     value="${data.toDate}" />
									<input type="hidden" name="roomNumber" value="${data.roomNumber}" />
									<div class="line">
										<label class="three-columns-left" for="customerId">e-mail address</label>
										<input class="three-columns-middle" type="email" name="emailAddress" type="text" value="${data.customer.emailAddress}" />
										<input class="three-columns-right" type="submit" value="Search by e-mail Address" />
									</div>
									</form>
								</td>
						</tr>						
					</table>
				</td>
			</tr>
			<tr align="center">
				<td><input type="submit" value="Create new customer" /></td>
			</tr>
		</table>



	<!-- Display list of empty rooms if the list itself is not empty -->
	<c:if test="${not empty data.customers}">
		<table class="border outer">
			<tr class="tableheader">
			  	<th class="th">Id</th>
				<th class="th">Name</th>
				<th class="th">Phone Number</th>
				<th class="th">E-mail address</th>
				<th class="th">Action</th>
			</tr>
			<c:forEach items="${data.customers}" var="customer">
				<tr>
	
					<td class="td"><c:out value="${customer.id}" /></td>
					<td class="td"><c:out value="${customer.name}" /></td>
					<td class="td"><c:out value="${customer.phoneNumber}" /></td>
					<td class="td"><c:out value="${customer.emailAddress}" /></td>
					<td class="td">
						<form name="id" method="GET" action="${pageContext.request.contextPath}/servlet/mc/newOrder">
							<input type="hidden" name="hotelId"  value="${data.hotel.id}" />
							<input type="hidden" name="fromDate" value="${data.fromDate}" />
							<input type="hidden" name="toDate"   value="${data.toDate}" />
							<input type="hidden" name="roomNumber"   value="${data.roomNumber}" />
							<input type="hidden" name="customerId"   value="${customer.id}" />
							<input type="submit" value="Select customer" />
						</form>
					</td>
				</tr>
			</c:forEach>
		</table>
	</c:if>
</body>
</html>