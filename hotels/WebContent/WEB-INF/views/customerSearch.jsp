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
				<table class="border" style="width:100%">
					<tr class="tableheader">
					  <td class="no-side-border">Search for customer</td>
					</tr>
					<tr>
						<td class="no-side-border">
							<form name="searchByName" method="GET" action="${pageContext.request.contextPath}/servlet/customer/customer">
								<div class="line">
									<label class="no-side-border three-elements-left" for="customerName">Name</label>
									<input class="no-side-border three-elements-middle" 
											type='text' 
											name="customerName" 
											value="${customerName}"
									/><!--TODO pattern="^$|^\\p{L}+[\\p{L}\\p{Z}\\p{P}]{0,}"/> -->
									<input class="no-side-border three-elements-right" type="submit" name="action" value="Search by name" />
								</div>
							</form>
							<div class="error">
								${requestScope['error.customerName']}
							</div>
						</td>
					</tr>
					
					<tr>
						<td class="no-side-border">
							<form name="searchByPhoneNumber" method="GET" action="${pageContext.request.contextPath}/servlet/customer/customer">
								<div class="line">
									<label class="no-side-border three-elements-left" for="customerPhoneNumber">Phone number</label>
									<input 
										class="no-side-border three-elements-middle" 
										type="tel" 
										name="customerPhoneNumber" 
										pattern="^(\+)?\d{3,15}$" title="An optional '+' sign and 3-15 digits" 
										value="${customerPhoneNumber}" 
									/>
									<input class="no-side-border three-elements-right" type="submit" name="action" value="Search by phoneNumber" />
								</div>
							</form>
							<div class="error">
								${requestScope['error.customerPhoneNumber']}
							</div>
						</td>
					</tr>
					
					<tr>
						<td class="no-side-border">
							<form name="searchByEmailAddress" method="GET" action="${pageContext.request.contextPath}/servlet/customer/customer">
								<div class="line">
									<label class="no-side-border three-elements-left" for="customerEmailAddress">e-mail address</label>
									<input 
										class="no-side-border three-elements-middle"
										type="email" 
										name="customerEmailAddress" 
										type="email" value="${customerEmailAddress}"
									/>
									<input class="no-side-border three-elements-right" type="submit" name="action" value="Search by email address" />
								</div>
							</form>
						</td>
					</tr>						
				</table>
			</td>
		</tr>
		<tr>
			<td>				
				<div class="line" style="float:right;">
					<button onclick="location.href='${pageContext.request.contextPath}/servlet/home/home'" type="button">Home</button>
					<button type="button" name="back" onclick="history.back()">back</button>
				</div>				
			</td>
		</tr>
		<tr>
			<td>
				<div style="float:left">
					Search results:
				</div>
			</td>
		</tr>
		<tr>
			<td>
				<!-- Display list of customers if the list itself is not empty -->
				<c:if test="${not empty requestScope.customers}">
					<div class="scrollableVertically">
						<table class="border-collapse-with-div" >
							<tr class="tableheader">
							  	<th class="no-side-border">Id</th>
								<th class="no-side-border">Name</th>
								<th class="no-side-border">Phone Number</th>
								<th class="no-side-border">E-mail address</th>
								<th class="no-side-border">Action</th>
							</tr>
							<c:forEach items="${requestScope.customers}" var="customer">
								<tr>
									<td class="no-side-border"><c:out value="${customer.id}" /></td>
									<td class="no-side-border"><c:out value="${customer.name}" /></td>
									<td class="no-side-border"><c:out value="${customer.phoneNumber}" /></td>
									<td class="no-side-border"><c:out value="${customer.emailAddress}" /></td>
									<td class="no-side-border">
										<form name="id" method="POST" action="${pageContext.request.contextPath}/servlet/customer/customer">
											<input type="hidden" name="customerId"   value="${customer.id}" />
											<input type="submit" name="action" value="Select" />
										</form>
									</td>
								</tr>
							</c:forEach>
						</table>
					</div>	
				</c:if>			
			</td>
		</tr>
	</table>

	
</body>
</html>