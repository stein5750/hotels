<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
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
					<table class="border center one-column">
						<tr class="tableheader">
						  	<th class="" colspan="2">Hotel details</th>
						</tr>
						<tr class="">
							<td class="border">
								<h3>Name:</h3>
								<p>${hotel.name}</p>
								<br/>
								<h3>Phone number:</h3>
								<p>${hotel.phoneNumber}</p>
								<br/>							
								<h3>Email address:</h3>
								<p>
									<a href="mailto:${hotel.emailAddress}">${hotel.emailAddress}</a>
								</p>
								<br/>							
								<h3>Address:</h3>
								<p>${hotel.address}</p>
								<br/>							
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
					</div>
				</td>
			</tr>
		</table>
	</body>
</html>