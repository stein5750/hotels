
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
	<meta charset="utf-8"/>
	<title>Create customer</title>
	<style>
		<%@include file="/WEB-INF/css/style.css"%>
	</style>
</head>
<body>
	<form id="createCustomer" name="createCustomer" method="GET" action="${pageContext.request.contextPath}/servlet/mc/saveNewCustomer">
	<table class="center">
		<tr>
			<td class="heading1">
				Create Customer
			</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td>
				<table class="border" style="width:100%">
					<tr class="tableheader">
					  <td class="th">Create customer</td>
					</tr>
					<tr>
						<td class="td">
							<div class="line">
								<label class="two-columns-left">Name</label>
								<input class="two-columns-right" type='text' name="name" type="text" value="${data.name}"/>
							</div>
						</td>
					</tr>
					<tr>
						<td class="td">
							<div class="line">
								<label class="two-columns-left">Phone number</label>
								<input class="two-columns-right" type='text' name="phoneNumber" type="text" value="${data.phoneNumber}" />
							</div>
						</td>
					</tr>
					<tr>
						<td class="td">
							<div class="line">
								<label class="two-columns-left">E-mail address</label>
								<input class="two-columns-right" type='text' name="emailAddress" type="text" value="${data.emailAddress}" />
							</div>
						</td>							
					</tr>
					<tr>
						<td class="td">
							<div class="line">
								<label class="two-columns-left">Address</label>
							<textarea class="two-columns-right" name="address" form="createCustomer">${data.address}</textarea>
							</div>
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
					<input type="submit" value="Create new customer" />
				</div>
			</td>
		</tr>
	</table>
	</form>
</body>
</html>