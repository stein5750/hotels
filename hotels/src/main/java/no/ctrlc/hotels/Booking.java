package no.ctrlc.hotels;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component("bookingApp")
public class Booking implements ApplicationContextAware{
	
	@Autowired 
	Tools tools;
	
	private ApplicationContext ctx;
	
	@Autowired 
	private BookingDb bookingDb;
	
	public Booking() {}
	
	public void setApplicationContext(ApplicationContext context) {
		this.ctx=context;
	}

	public List<Hotel> hotelsList() {
		return bookingDb.hotelsList();
	}
	
	public Hotel getHotelById(Integer id) {
		return bookingDb.getHotelById(id);
	}
	
	
	// customer: create customer
	public Customer createCustomer(String name, String phoneNumber, String emailAddress, String address) {
		// create Customer
		Customer customer = (Customer) ctx.getBean("customer");
		customer.setId(UUID.randomUUID());
		customer.setName(name);
		customer.setPhoneNumber(phoneNumber);
		customer.setEmailAddress(emailAddress);
		customer.setAddress(address);
		bookingDb.saveCustomer(customer);
		return customer;
	}			

	
	// customer: find customer by id
	public Customer findCustomerById(UUID uuid){
		return bookingDb.findCustomerById(uuid);
	}
	
	
	// customer: find customer by non-unique name
	public List<Customer> findCustomerByName(String name){
		return bookingDb.findCustomerByName(name);
	}
	
	// customer: find customer by non-unique phone-number
	public List<Customer> findCustomerByPhoneNumber(String customerPhoneNumber){
		return bookingDb.findCustomerByPhoneNumber(customerPhoneNumber);
	}
	
	
	// customer: find customer by email
	public List<Customer> findCustomerByEmailAddress(String emailAddress){
		return bookingDb.findCustomerByEmailAddress(emailAddress);
	}

	
	// order: create order
	public UUID createOrder(UUID customerUUID, Integer hotelId, int roomNumber, LocalDate fromDate, LocalDate toDate) {
		Hotel hotel=bookingDb.getHotelById(hotelId);
		if (hotel==null) return null;
		Room room=bookingDb.getRoomIfAvailable(hotelId, roomNumber, fromDate, toDate);
 		if (room == null) return null;
 		Customer customer = bookingDb.findCustomerById( customerUUID);
		if (customer == null) return null;
		// recalculating totalprice to prevent it from beeing set irregularly by user
		BigDecimal totalPrice = tools.CalculateTotalPrice(fromDate, toDate, room.getRoomPrice());
		UUID orderId = UUID.randomUUID();
		Order order = (Order) ctx.getBean("order");
		order.setOrderId(orderId);
		order.setOrderCreatedDateTime(LocalDateTime.now());
		order.setReservingCustomer(customer);
		order.setReservedHotel(hotel);
		order.setReservedRoom(room);
		order.setFromDate(fromDate);
		order.setToDate(toDate);
		order.setTotalPrice(totalPrice);
		bookingDb.saveOrder(order);
		return orderId;
	}
	
	
	// order: find order by customerUUID
	public List<Order> findOrdersByCustomerId(UUID customerUUID){
		return bookingDb.findOrdersByCustomerId(customerUUID);
	}	

	
	// order: delete order by orderUUID
	public boolean deleteOrderByOrderId(UUID orderUUID) {
		return bookingDb.deleteOrderByOrderId(orderUUID);
	}
	
	
	// find available rooms
	public List<Room> findAvailableRooms(Integer hotelId, LocalDate fromDate, LocalDate toDate) {
		List<Room> rooms = bookingDb.findAvailableRooms(hotelId, fromDate, toDate);
		return rooms;
	}
	
	// get room
	public Room getRoom(Integer hotelId, int roomNumber) {
		return bookingDb.getRoom(hotelId, roomNumber);
	}
}

