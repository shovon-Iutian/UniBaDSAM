package de.uniba.dsg.dsam.client;

import de.uniba.dsg.dsam.model.Beverage;
import de.uniba.dsg.dsam.model.CustomerOrder;
import de.uniba.dsg.dsam.model.Incentive;
import de.uniba.dsg.dsam.persistence.BeverageManagement;
import de.uniba.dsg.dsam.persistence.IncentiveManagement;
import de.uniba.dsg.dsam.persistence.OrderJMSQueueManagement;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

public class BeveragesServlet extends HttpServlet {

	@EJB
	private BeverageManagement<?, Beverage> beverageManagement;

	@EJB
	private IncentiveManagement<?, Incentive> incentiveManagement;

	@EJB
	private OrderJMSQueueManagement<CustomerOrder> orderJMSQueueManagement;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		try{
			List<Incentive> incentives = incentiveManagement.getAll();
			List<Beverage> beverages = beverageManagement.getAll();
			request.getSession().setAttribute("incentives", incentives);
			request.getSession().setAttribute("beverages", beverages);
			request.getRequestDispatcher("/beverages.jsp").forward(request, response);
		}
		catch (ServletException e) {
			e.printStackTrace();
		}


	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		try {
			Map<String,String> messages = new HashMap<String, String>();
			String name = req.getParameter("beverage_name").trim();
			String manufacturer = req.getParameter("beverage_manufacturer").trim();
			String price_value = req.getParameter("beverage_price").trim();
			String quantity_value = req.getParameter("beverage_quantity").trim();
			int incentiveId = Integer.valueOf(req.getParameter("incentive"));
			String id = req.getParameter("beverage_id");
			List<Beverage> beverages = beverageManagement.getAll();
			Beverage beverage;

			if(id != null){
				beverage = beverages.stream().filter(beverage1 -> beverage1.getId() == Integer.valueOf(id)).findAny().get();
				if(name== null || name.isEmpty()){
					messages.put("beverage_name", "Please enter a Beverage name");
				}
				else {
					beverage.setName(name);
				}
				if(manufacturer== null || manufacturer.isEmpty()){
					messages.put("manufacturer_name", "Please enter a Beverage name");
				}
				else {
					beverage.setManufacturer(manufacturer);
				}
				if(price_value==null || price_value.isEmpty()|| price_value.matches("^[a-zA-Z]*$")){
					messages.put("price_value", "Please enter a valid number");
				}
				else {
                    if (price_value.matches("-?\\d+(\\.\\d+)?")){
                        double price =  Double.parseDouble(price_value);
                        if(price <0 ){
                            messages.put("price", "Please enter a positive number");
                        }
                        else {
                            beverage.setPrice(price);
                        }
                    }
                    else{
                        messages.put("price", "Please enter a valid number");
                    }
				}
				if(quantity_value==null || quantity_value.isEmpty()|| quantity_value.matches("^[a-zA-Z]*$")){
					messages.put("quantity_value", "Please enter a valid number");
				}
				else {
					int quantity = Integer.valueOf(quantity_value);
					if(quantity < 0){
						messages.put("quantity", "Please enter a positive number");
					}
					else{
						beverage.setQuantity(quantity);
					}
				}
				if(incentiveId != -1){
					List<Incentive> incentives = incentiveManagement.getAll();
					Optional<Incentive> incentive = incentives.stream().filter(incntv -> incntv.getId() == incentiveId).
							findAny();
					incentive.ifPresent(beverage::setIncentive);
				}
				if(messages.isEmpty()){
					beverageManagement.update(beverage);
					messages.put("noErrors", "Beverage updated successfully");
					req.getSession().setAttribute("beverages", beverages);
					req.getSession().setAttribute("messages", messages);
					req.getRequestDispatcher("/beverages.jsp").forward(req, res);
				}
				else{
					req.setAttribute("messages", messages);
					req.getRequestDispatcher("/beverage_form.jsp?id="+id).forward(req, res);
				}
			}
			else {
				beverage = new Beverage();
				if(name== null || name.isEmpty()){
					messages.put("beverage_name", "Please enter a Beverage name");
				}
				else {
					beverage.setName(name);
				}
				if(manufacturer== null || manufacturer.isEmpty()){
					messages.put("manufacturer_name", "Please enter a Beverage name");
				}
				else {
					beverage.setManufacturer(manufacturer);
				}
				if(price_value==null || price_value.isEmpty()|| price_value.matches("^[a-zA-Z]*$")){
					messages.put("price_value", "Please enter a valid number");
				}
				else {
                    if (price_value.matches("-?\\d+(\\.\\d+)?")){
                        double price =  Double.parseDouble(price_value);
                        if(price <0 ){
                            messages.put("price", "Please enter a positive number");
                        }
                        else {
                            beverage.setPrice(price);
                        }
                    }
                    else{
                        messages.put("price", "Please enter a valid number");
                    }

				}
				if(quantity_value==null || quantity_value.isEmpty()|| quantity_value.matches("^[a-zA-Z]*$")){
					messages.put("quantity_value", "Please enter a valid number");
				}
				else {
					int quantity = Integer.valueOf(quantity_value);
					if(quantity < 0){
						messages.put("quantity", "Please enter a positive number");
					}
					else{
						beverage.setQuantity(quantity);
					}
				}
				if(incentiveId != -1){
					List<Incentive> incentives = incentiveManagement.getAll();
					Optional<Incentive> incentive = incentives.stream().filter(incntv -> incntv.getId() == incentiveId).
							findAny();
					incentive.ifPresent(beverage::setIncentive);
				}
				if(messages.isEmpty()){
					beverage = beverageManagement.create(beverage);
					beverages.add(beverage);
					messages.put("noErrors", "Beverage added successfully");
					req.getSession().setAttribute("beverages", beverages);
					req.getSession().setAttribute("messages", messages);
					req.getRequestDispatcher("/beverages.jsp").forward(req, res);
				}
				else {
					req.getSession().setAttribute("beverages", beverages);
					req.getSession().setAttribute("messages", messages);
					req.getRequestDispatcher("/beverage_form.jsp").forward(req, res);
				}

			}


		}
		catch (NumberFormatException e){
			System.out.println("error parsing the value of price/quantity");
		}
		catch (Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		try{
			int id = Integer.valueOf(req.getParameter("id"));
			int flag=0;
			List<Beverage> beverages = beverageManagement.getAll();
			List<CustomerOrder> customerOrders = orderJMSQueueManagement.getAll();
			for(CustomerOrder customerOrder: customerOrders){
				if(customerOrder.getOrderItems().getId()==id){
					res.getWriter().write("Not Delete");
					flag=1;
					break;
				}
			}
			if (flag==0){
				Beverage beverage = beverages.stream().filter(beverage1 -> beverage1.getId() == id).findAny().get();
				beverageManagement.deleteOne(beverage);
				res.getWriter().write("Delete");
			}
		}
		catch (NumberFormatException e){
			System.out.println("Invalid ID "+ e.getMessage());
		}


	}
}
