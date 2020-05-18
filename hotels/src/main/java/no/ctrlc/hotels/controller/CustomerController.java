package no.ctrlc.hotels.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import no.ctrlc.hotels.model.Customer;
import no.ctrlc.hotels.model.Order;
import no.ctrlc.hotels.service.HotelsService;
import no.ctrlc.hotels.util.Utilities;


@Controller
@RequestMapping("/customer")
@SessionAttributes(names = { "order" })
public class CustomerController {

	@Autowired
	HotelsService hotelsService;
	
	@Autowired 
	Utilities utilities;

	
		@RequestMapping( 
				path="/customer",
				params = {"action=Search"},
				method = RequestMethod.GET
		)
		public String customerSearch() {
			return "customerSearch";
		}		
		

		@RequestMapping( 
				path="/customer", 
				params = {"action=Search by name", "customerName"}, 
				method = RequestMethod.GET
		)
		public String searchCustomerByName(
				@ModelAttribute("customerName") String customerName,
				Model model)
			{
			// search for customer by non unique name
			List<Customer> customers = hotelsService.getCustomerByName(customerName);
			model.addAttribute("customers", customers);
			return "customerSearch";
		}			
		

		@RequestMapping( 
				path="/customer", 
				params = {"action=Search by phoneNumber", "customerPhoneNumber"}, 
				method = RequestMethod.GET
		)
		public String searchCustomerByPhoneNumber(
				@ModelAttribute("customerPhoneNumber") String customerPhoneNumber,
				Model model)
			{
			// search for customer by non unique name
			List<Customer> customers = hotelsService.getCustomerByPhoneNumber(customerPhoneNumber);
			model.addAttribute("customers", customers);
			return "customerSearch";
		}			
		
		@RequestMapping( 
				path="/customer", 
				params = {"action=Search by email address", "customerEmailAddress"}, 
				method = RequestMethod.GET
		)
		public String searchCustomerByEmailAddress(
				@ModelAttribute("customerEmailAddress") String customerEmailAddress,
				Model model)
			{
			// search for customer by non unique name
			List<Customer> customers = hotelsService.getCustomerByEmailAddress(customerEmailAddress);
			model.addAttribute("customers", customers);
			return "customerSearch";
		}		
		
		
	@RequestMapping( 
		path="/customer", 
		params = { "action=Select","customerId"}, 
		method = RequestMethod.POST
		)
		public String selectCustomer( 
			@RequestParam("customerId") UUID customerId,
			@ModelAttribute Order order
		) {
			Customer customer = hotelsService.getCustomerById(customerId);
			// add/replace customer to order
			order.setCustomer(customer);  
			// go to home page				
			return "redirect:/servlet/home/home";	
		}		
		

	@RequestMapping(
		path="/customer", 
		params="action=Details",
		method = RequestMethod.POST
	)
	public String customerShowDetails() {
			return "customerShowDetails";
	}
				

		// Create customer
		@RequestMapping( 
				path = "/customer", 
				params = "action=Create",
				method = RequestMethod.GET
				)
		public String createCustomer() {			
			return "customerCreate";
		}	
	

		@RequestMapping(
			path="/customer", 
			params = "action=Save",
			method = RequestMethod.POST
		)
		public String saveCustomer(
			@Validated(Customer.FirstStep.class) @ModelAttribute("customer") Customer customer, 
			BindingResult bindingResult,
			@ModelAttribute("order") Order order,
			Model model
			) {		
			if (bindingResult.hasErrors()) {
				return "customerCreate";
			}
			// else
			boolean result =  hotelsService.saveCustomer(customer);
			if (! result) {				
				// Add error to model
				model.addAttribute("error.databaseError", new String("An error occured while saving to database."));
				// return to same view with error messages
				return "customerCreate";
			}else {
				// add customer to order and return view
				order.setCustomer(customer);
				return "customerShowDetails";
			}
		}
		
}