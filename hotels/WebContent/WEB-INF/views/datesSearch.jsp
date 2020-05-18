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
	<form:form 
		modelAttribute="dates" 
		name="datesForm" 
		method="POST" 
		action="${pageContext.request.contextPath}/servlet/dates/dates"
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
						  <td class="no-side-border" colspan="2">Dates</td>
						</tr>
						<tr>
							<td class="no-side-border left-column no-border-right">
								<label>From date:</label>
								<p>
									<form:errors path="fromDate" class="error" />
								</p>
								<p>
									<form:errors path="fromDateBeforeToDate" class="error" />
								</p>
							</td>	
							<td class="no-side-border right-column no-border-left">
								<form:input 
									path="fromDate" 											
									type="date" 
									required="required"
									class="date"
								/>
							</td>
						</tr>
						
						<tr>
							<td class="no-side-border left-column no-border-right">
								<label>To date:</label>
								<p>
									<form:errors path="toDate" class="error" />
								</p>	
							</td>
							<td class="no-side-border right-column no-border-left">
								<form:input 
									path="toDate" 											
									type="date" 
									required="required"
									class="date"
								/>
							</td>
						</tr>

					</table>
				</td>
			</tr>
			<tr align="center">
				<td>
					<div class="line">
						<input type="submit" name="action" value="Select" />
						<button onclick="location.href='${pageContext.request.contextPath}/servlet/home/home'" type="button">Home</button>
						<button type="button" name="back" onclick="history.back()">back</button>							
					</div>
				</td>
			</tr>
		</table>
	</form:form>



</body>
</html>