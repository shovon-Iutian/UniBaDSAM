<%@ page import="java.util.*" %>
<%@ page import="de.uniba.dsg.dsam.model.Beverage" %>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Beverage Store Demo</title>

<!-- Bootstrap -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap-theme.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js"></script>
</head>
<body>
	<div class="container">
		<h1>Customer Order</h1>
		
		<p>&nbsp;</p>
		
		<form role="form" action="/frontend/orders" method="post">
			<div class="form-group">
				<div class="input-group">
					<span class="input-group-addon">Order Issued</span>
					<input name="issue_date" type="text" class="form-control">
				</div>
				<div class="input-group">
					<select class="custom-select" name="beverage_name">
						<option value="-1">Open this select menu</option>
						<%
							List<Beverage> beverages = (List<Beverage>) request.getSession().getAttribute("beverages");
							if(beverages != null){for(Beverage beverage: beverages) {
						%>
						<option value="<%= beverage.getId()%>" selected
							><%= beverage.getName().toUpperCase()%></option>
						<% }}%>
					</select>
				</div>
				<div class="input-group">
					<span class="input-group-addon">Item Amount</span>
					<input name="beverage_amount" type="number" class="form-control">
				</div>
			</div>
			<a href="/frontend/orders/order_form" class="btn btn-default">Cancel</a>
			<button type="submit" class="btn btn-success">Save</button>
		</form>					
	</div>
</body>
</html>
