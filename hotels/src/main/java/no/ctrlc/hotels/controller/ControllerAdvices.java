package no.ctrlc.hotels.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import no.ctrlc.hotels.model.Customer;
import no.ctrlc.hotels.model.Dates;
import no.ctrlc.hotels.model.Hotel;
import no.ctrlc.hotels.model.Order;

/* Common actions for all controllers */
@ControllerAdvice
public class ControllerAdvices {

    @Autowired
    private ApplicationContext appContext;

    // Exception handling
    @ExceptionHandler
    public ModelAndView handleException( Exception e) {
        Map<String, String> modelData = new HashMap<String, String>();
        modelData.put("error.exception", e.toString());
        return new ModelAndView("errorPage", modelData);
    }

    // Set Date format for data binding as this must be set explicitly.
    @InitBinder
    public void configureInitiBinder( WebDataBinder webDataBinder) {
        webDataBinder.registerCustomEditor(Date.class,
                new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true, 10));
    }

    @ModelAttribute(name = "customer")
    public Customer addCustomerObjectToModel() {
        Customer customer = (Customer) appContext.getBean("customer");
        return customer;
    }

    @InitBinder(value = "customer")
    public void initBinder_Customer( WebDataBinder webDataBinder) {
        // Security, disable the possibility to change customer id.
        webDataBinder.setDisallowedFields("id");
    }

    @ModelAttribute(name = "order")
    public Order addOrderObjectToModel() {
        Order order = (Order) appContext.getBean("order");
        return order;
    }

    @InitBinder(value = "order")
    public void initBinder_Order( WebDataBinder webDataBinder) {
        // Security, disable the possibility to change order id, customer id and total
        // price.
        webDataBinder.setDisallowedFields("orderId");
        webDataBinder.setDisallowedFields("customerId");
        webDataBinder.setDisallowedFields("totalPrice");
    }

    @ModelAttribute(name = "hotel")
    public Hotel addHotelObjectToModel() {
        Hotel hotel = (Hotel) appContext.getBean("hotel");
        return hotel;
    }

    @ModelAttribute(name = "dates")
    public Dates addDatesObjectToModel() {
        return (Dates) appContext.getBean("dates");
    }

}