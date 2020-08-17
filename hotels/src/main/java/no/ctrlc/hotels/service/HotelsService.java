package no.ctrlc.hotels.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import no.ctrlc.hotels.dao.hotelsDao;
import no.ctrlc.hotels.model.Customer;
import no.ctrlc.hotels.model.Hotel;
import no.ctrlc.hotels.model.Order;
import no.ctrlc.hotels.model.Room;

@Service("hotelsService")
public class HotelsService {

    @Autowired
    private hotelsDao hotelsDao;

    // validators
    private ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private Validator validator = factory.getValidator();
    private Set<ConstraintViolation<Customer>> customerConstraintViolations;
    private Set<ConstraintViolation<Order>> orderConstraintViolations;

    public HotelsService() {

    }

    public List<Hotel> hotelsList() {
        return hotelsDao.hotelsList();
    }

    public Hotel getHotelById( Integer id) {
        return hotelsDao.getHotelById(id);
    }

    public boolean saveCustomer( Customer customer) {
        customer.setId(UUID.randomUUID());
        customerConstraintViolations = validator.validate(customer, Customer.Total.class);
        if (!customerConstraintViolations.isEmpty()) {
            return false;
        }
        try {
            hotelsDao.saveCustomer(customer);
        } catch (DataAccessException e) {
            return false;
        }
        return true;
    }

    // customer: get customer by id
    public Customer getCustomerById( UUID uuid) {
        return hotelsDao.getCustomerById(uuid);
    }

    // customer: get customer by non-unique name
    public List<Customer> getCustomerByName( String name) {
        return hotelsDao.getCustomerByName(name);
    }

    // customer: get customer by non-unique phone-number
    public List<Customer> getCustomerByPhoneNumber( String customerPhoneNumber) {
        return hotelsDao.getCustomerByPhoneNumber(customerPhoneNumber);
    }

    // customer: get customer by email
    public List<Customer> getCustomerByEmailAddress( String emailAddress) {
        return hotelsDao.getCustomerByEmailAddress(emailAddress);
    }

    public UUID saveOrder( Order order) {
        // set id
        order.setOrderId(UUID.randomUUID());
        // set time of creation for this order
        order.setOrderCreatedDateTime(LocalDateTime.now());
        // validate order
        orderConstraintViolations = validator.validate(order, Order.Total.class);
        if (!orderConstraintViolations.isEmpty()) {
            return null;
        }
        // save to database
        try {
            hotelsDao.saveOrder(order);
        } catch (DataAccessException e) {
            return null;
        }
        return order.getOrderId();
    }

    // order: get order by customerUUID
    public List<Order> getOrdersByCustomerId( UUID customerUUID) {
        return hotelsDao.getOrdersByCustomerId(customerUUID);
    }

    // order: get order by order id
    public Order getOrderByOrderId( UUID orderId) {
        return hotelsDao.getOrderByOrderId(orderId);
    }

    // order: delete order by orderUUID
    public boolean deleteOrderByOrderId( UUID orderUUID) {
        return hotelsDao.deleteOrderByOrderId(orderUUID);
    }

    // get available rooms
    public List<Room> getAvailableRooms( Integer hotelId, LocalDate fromDate, LocalDate toDate) {
        List<Room> rooms = hotelsDao.getAvailableRooms(hotelId, fromDate, toDate);
        return rooms;
    }

}