<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
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
		<form:form modelAttribute="customer" 
			name="newCustomerDetails" 
			method="POST" 
			action="${pageContext.request.contextPath}/servlet/customer/customer"
		>
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
							  <td class="no-side-border">Create customer</td>
							</tr>
							<tr>
								<td class="no-side-border">
									<div class="line">
										<label class="two-columns-left">Name</label>
										<form:input 
											path="name" 											
											type="text" 
											minlength="3"
											maxlength="100"
											required="required"
											class="two-columns-right" 
										/>
										<form:errors path="name" class="error" />
									</div>
								</td>
							</tr>
							<tr>
								<td class="no-side-border">
									<div class="line">
										<label class="two-columns-left">Phone number</label>
										<form:input 
											path="phoneNumber" 
											type="tel"
											pattern="^(\+)?\d{3,15}$" 
											title="An optional '+' sign and 3-15 digits"
											required="required"
											class="two-columns-right"
										/>
										<form:errors path="phoneNumber" class="error" />
									</div>
								</td>
							</tr>
							<tr>
								<td class="no-side-border">
									<div class="line">
										<label class="two-columns-left">E-mail address</label>
										<form:input 
											path="emailAddress"
											type="email"
											required="required"
											class="two-columns-right" 
										/>
											<form:errors path="emailAddress"  class="error" /> 
									</div>
								</td>							
							</tr>
							<tr>
								<td class="no-side-border">
									<div class="line">
										<label class="two-columns-left">Address</label>
										<form:textarea 
											path="address"
											type="text" 
											minlength="6"
											maxlength="100"
											required="required"
											class="two-columns-right" 
										/>
										<form:errors path="address"  class="error" />
									</div>
								</td>							
							</tr>
						</table>
					</td>
				</tr>
				<tr align="center">
					<td>
						<div class="line">
							<button onclick="location.href='${pageContext.request.contextPath}/servlet/home/home'" type="button">Home</button>
							<button type="button" name="back" onclick="history.back()">back</button>
							<input type="submit" name="action" value="Save" />							
						</div>
					</td>
				</tr>
				<tr>
					<td>
						<div class="error">
							<!-- Showing errors while saving to the database -->
							${requestScope['error.databaseError']}
						</div>
					</td>
				</tr>	
			</table>
		</form:form>
	</body>
</html>