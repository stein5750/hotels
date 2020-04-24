
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<html>
<head>
	<meta charset="utf-8"/>
	<title>Find customer</title>
	<style>
		<%@include file="/WEB-INF/css/style.css"%>
	</style>
</head>
<body>
	<table class="center">
		<tr>
			<td class="heading1">
				Find Customer
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
							<form name="findByName" method="GET" action="${pageContext.request.contextPath}/servlet/c/findCustomer">
								<div class="line">
									<label class="three-columns-left" for="customerId">Name</label>
									<input class="three-columns-middle" 
											type='text' 
											name="customerName" 
											value="${requestScope.customerName}"
											/><!-- pattern="^$|^\\p{L}+[\\p{L}\\p{Z}\\p{P}]{0,}"/> -->
									<input class="three-columns-right" type="submit" value="Search by name" />
								</div>
								</form>
									<div class="error">
										${requestScope['error.customerName']}
									</div>
							</td>
					</tr>
					
					<tr>
						<td class="td">
							<form name="findByPhoneNumber" method="GET" action="${pageContext.request.contextPath}/servlet/c/findCustomer">
								<div class="line">
									<label class="three-columns-left" for="customerId">Phone number</label>
									<input class="three-columns-middle" type="tel" name="customerPhoneNumber" pattern="^(\+)?\d{3,15}$" title="An optional '+' sign and 3-15 digits" value="${requestScope.customerPhoneNumber}" />
									<input class="three-columns-right" type="submit" value="Search by phoneNumber" />
								</div>
								</form>
							</td>
					</tr>
					
					<tr>
						<td class="td">
							<form name="findByEmailAddress" method="GET" action="${pageContext.request.contextPath}/servlet/c/findCustomer">
								<div class="line">
									<label class="three-columns-left" for="customerId">e-mail address</label>
									<input class="three-columns-middle" type="email" name="customerEmailAddress" type="text" value="${requestScope.customerEmailAddress}" />
									<input class="three-columns-right" type="submit" value="Search by e-mail Address" />
								</div>
								</form>
							</td>
					</tr>						
				</table>
			</td>
		</tr>
		<tr align="center">
			<td>
				<form name="createCustomer" method="GET" action="${pageContext.request.contextPath}/servlet/c/createCustomer">
					<input type="submit" value="Create new customer" />
				</form>
			</td>
		</tr>
	</table>

	<!-- Display list of customers if the list itself is not empty -->
	<c:if test="${not empty requestScope.customers}">
		<table class="border center">
			<tr class="tableheader">
			  	<th class="th">Id</th>
				<th class="th">Name</th>
				<th class="th">Phone Number</th>
				<th class="th">E-mail address</th>
				<th class="th">Action</th>
			</tr>
			<c:forEach items="${requestScope.customers}" var="customer">
				<tr>
	
					<td class="td"><c:out value="${customer.id}" /></td>
					<td class="td"><c:out value="${customer.name}" /></td>
					<td class="td"><c:out value="${customer.phoneNumber}" /></td>
					<td class="td"><c:out value="${customer.emailAddress}" /></td>
					<td class="td">
						<form name="id" method="GET" action="${pageContext.request.contextPath}/servlet/c/customer">
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