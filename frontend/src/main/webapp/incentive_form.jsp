<%@ page import="java.util.List" %>
<%@ page import="de.uniba.dsg.dsam.model.Incentive" %>
<%@ page import="de.uniba.dsg.dsam.model.TrialPackage" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Add Incentive</title>

<!-- Bootstrap -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap-theme.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js"></script>
</head>
<body>
	<div class="container">
		<%
			String strId = request.getParameter("id");
			Incentive incentive = null;
			if(strId != null ){
				int id = Integer.valueOf(strId);
				List<Incentive> incentives = (List<Incentive>) request.getSession().getAttribute("incentives");
				for(Incentive incent: incentives){
					if(incent.getId() == id){
						incentive = incent;
						break;
					}
				}
		%>
		<h1>Update Incentive</h1>
			<% }else{ %>
		<h1>New Incentive</h1>
			<% } %>
		<p>&nbsp;</p>
		


		<form role="form" action="/frontend/incentives" method="post">

			<div class="form-group">
				<div class="input-group">
					<span class="input-group-addon">Incentive Name</span>
					<span class="text-danger">${messages.incentive_name}</span>
					<input name="incentive_name" type="text" class="form-control"
						   <% if(incentive != null){ %>
					value="<%= incentive.getName()%>">
					<input type="hidden" name="incentive_id" value="<%= incentive.getId()%>">
						   <% }%>

				</div>
			</div>
			<% if(incentive == null){ %>
			<div class="form-group">
				<div class="input-group">
					<span class="input-group-addon">Incentive type</span>

					<input name="incentive_type" type="radio" value="trial_package" class="form-control" checked>Trial package </br>
					<input name="incentive_type" type="radio" value="promotional_package" class="form-control">Promotional package

				</div>
			</div>
			<% } %>
			<a href="/frontend/incentives" class="btn btn-default">Cancel</a>
			<button type="submit" class="btn btn-success">Save</button>
		</form>					
		
	</div>
</body>
</html>