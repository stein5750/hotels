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
import org.springframework.web.bind.support.SessionStatus;

import no.ctrlc.hotels.model.Order;
import no.ctrlc.hotels.service.HotelsService;
import no.ctrlc.hotels.util.Utilities;


@Controller
@RequestMapping("/order")
@SessionAttributes(names = { "order" })
public class OrderController {

	@Autowired 
	Utilities utilities; 
	
	@Autowired
	HotelsService hotelsService;


	
	
	@RequestMapping( 
			path="/order", 
			params = "action=Create order", 
			method = RequestMethod.POST
			)
	public String createOrder( 
		@Validated(Order.FirstStep.class) @ModelAttribute("order") Order order, 
		BindingResult bindingResult,			
		Model model
	) {		
		if (bindingResult.hasErrors()) {
			// Add errors to model
			model.addAttribute("error.fieldErrors", utilities.getFieldErrors(bindingResult) );
			// return to same view with error messages
			return "home";
		} else {
			// Return view
			return "orderReview";
		}
	}			
		
	
	@RequestMapping( 
			path="/order", 
			params = "action=Edit order", 
			method = RequestMethod.POST
			)
	public String EditOrder() {
		// return view
		return "redirect:/servlet/home/home";
	}
	
	
	@RequestMapping(
			path="/order",
			params = "action=Cancel order", 
			method = RequestMethod.POST
			) 
	public String CancelOrder( SessionStatus sessionStatus ) {	
		// clear all spring model objects
		sessionStatus.setComplete();	
		// return view
		return "redirect:/servlet/home/home";
		}	 
	
	
	@RequestMapping( 
			path="/order", 
			params = "action=Save order", 
			method = RequestMethod.POST
			)
	public String saveOrder( 
		@Validated(Order.FirstStep.class) @ModelAttribute("order") Order order, 
		BindingResult bindingResult,			
		Model model,
		SessionStatus sessionStatus
		) {		
			if (bindingResult.hasErrors()) {
				// Add errors to model
				model.addAttribute("error.fieldErrors", utilities.getFieldErrors(bindingResult) );
				// return to same wiev with error messages
			return "orderReview";
		}			
		// Save order;
		UUID savedOrderId = hotelsService.saveOrder(order);		
		// if saved successfully
		if ( savedOrderId == null ) {
			// Add error to model
			model.addAttribute("error.databaseError", new String("An error occured while saving to database."));
			// return to same view with error messages
			return "orderReview";
		}
		else {
			// clear session
			sessionStatus.setComplete();
			// add order id
			model.addAttribute("orderId", savedOrderId);
			return "orderSaved";
		}	
	}


	@RequestMapping( 
			path="/order", 
			params = { "action=Show order"}, 
			method = RequestMethod.POST
			)
	public String showOrder( @RequestParam UUID orderId, Model model) {
		Order order = hotelsService.getOrderByOrderId( orderId );
		model.addAttribute("displayedOrder", order);
		return "orderShow";
	}			
	
	
	@RequestMapping( 
			path="/order", 
			params = { "action=Delete"}, 
			method = RequestMethod.POST
			)
	public String showOrder( 
			@ModelAttribute("order") Order order, 
			@RequestParam UUID orderId, 
			Model model
			) {
		hotelsService.deleteOrderByOrderId( orderId );
		// This customers orders
		List<Order> orders = hotelsService.getOrdersByCustomerId(order.getCustomer().getId());
		model.addAttribute("orders", orders);
		// Return view
		return "orderShowCustomersOrders";
	}		


	@RequestMapping(
		path="/order",
		params="action=Orders",
		method = RequestMethod.POST
	)
	public String customerShowOrders(
			@ModelAttribute("order") Order order, 
			Model model) {
		if ( order.getCustomer() == null) {
			model.addAttribute("error.preRequirement", new String("Choose a customer before trying to show customers orders"));
			return "home";
		}
		// This customers orders
		UUID customerId = order.getCustomer().getId();
		List<Order> orders = hotelsService.getOrdersByCustomerId(customerId);
		model.addAttribute("orders", orders);
		return "orderShowCustomersOrders";
	}	
	
}