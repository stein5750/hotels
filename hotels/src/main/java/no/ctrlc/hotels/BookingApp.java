package no.ctrlc.hotels;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component("bookingApp")
public class BookingApp implements ApplicationContextAware{
	
	private ApplicationContext ctx;
	private BookingDb bookingDb;
	
	public BookingApp() {}
	
	public void setApplicationContext(ApplicationContext context) {
		this.ctx=context;
	}
	
	@Autowired
	public void setBookingDb(BookingDb bookingDb) {
		this.bookingDb=bookingDb;
	}

	public List<Hotel> hotelsList() {
		return bookingDb.hotelsList();
	}
	
	public Hotel getHotelById(Integer id) {
		return bookingDb.getHotelById(id);
	}
	
	
	// customer: create customer
	public Customer createCustomer(String name, String address, String emailAddress, String phoneNumber) {
		// create Customer
		Customer customer = (Customer) ctx.getBean("customer");
		customer.setId(UUID.randomUUID());
		customer.setName(name);
		customer.setAddress(address);
		customer.setEmailAddress(emailAddress);
		customer.setPhoneNumber(phoneNumber);
		bookingDb.saveCustomer(customer);
		return customer;
	}			

	
	// customer: find customer by id
	public Customer findCustomerById(UUID uuid){
		return bookingDb.findCustomerByUUID(uuid);
	}
	
	
	// customer: find customer by non-unique name
	public List<Customer> findCustomerByName(String name){
		return bookingDb.findCustomerByName(name);
	}
	
	// customer: find customer by non-unique phone-number
	public List<Customer> findCustomerByPhoneNumber(String phoneNumber){
		return bookingDb.findCustomerByPhoneNumber(phoneNumber);
	}
	
	
	// customer: find customer by email
	public List<Customer> findCustomerByEmailAddress(String emailAddress){
		return bookingDb.findCustomerByEmailAddress(emailAddress);
	}
	
	
	// customer: find all customers
	public List<Customer> findAllCustomers(){
		return bookingDb.findAllCustomers();
	}
	
	// customer: count customers
	public int customerCount() {
		return bookingDb.customerCount();
	}
	
	// order: create order
	public Order createOrder(UUID customerUUID, Integer hotelId, int roomNumber, LocalDate fromDate, LocalDate toDate) {
		Hotel hotel=bookingDb.getHotelById(hotelId);
		if (hotel==null) return null;
		Room room=bookingDb.getRoomIfAvailable(hotelId, roomNumber, fromDate, toDate);
 		if (room == null) return null;
 		Customer customer = bookingDb.findCustomerByUUID( customerUUID);
		if (customer == null) return null;
		
		Order order = (Order) ctx.getBean("order");
		order.setOrderUUID(UUID.randomUUID());
		order.setOrderCreatedDateTime(LocalDateTime.now());
		order.setReservingCustomer(customer);
		order.setReservedHotel(hotel);
		order.setReservedRoom(room);
		order.setFromDate(fromDate);
		order.setToDate(toDate);
		order.CalculatetotalPrice();
		bookingDb.saveOrder(order);
		return order;
	}
	
	
	// order: find order by orderUUID
	public Order findOrderByOrderUUID(UUID orderUUID){
		return bookingDb.findOrderByOrderUUID(orderUUID);
	}
	
	// order: find order by customerUUID
	public List<Order> findOrdersByCustomerUUID(UUID customerUUID){
		return bookingDb.findOrdersByCustomerUUID(customerUUID);
	}	
	
	// order: find all orders
	public List<Order> findAllOrders(){
		return bookingDb.findAllOrders();
	}

	
	// order: delete order by orderUUID
	public boolean deleteOrderByOrderUUID(UUID orderUUID) {
		return bookingDb.deleteOrderByOrderUUID(orderUUID);
	}
	
	

	// order: count orders
	public int orderCount() {
		return bookingDb.orderCount();
	}
	
	// find available rooms
	public List<Room> findAvailableRooms(Integer hotelId, LocalDate fromDate, LocalDate toDate) {
		List<Room> rooms = bookingDb.findAvailableRooms(hotelId, fromDate, toDate);
		return rooms;
	}
	
	// find if a room is available
	public boolean roomIsAvailable(Integer hotelId, int roomNumber, LocalDate fromDate, LocalDate toDate) {
		return bookingDb.roomIsAvailable(hotelId, roomNumber, fromDate, toDate);
	}
	
	// find if a room is available
	public Room getRoomIfAvailable(Integer hotelId, int roomNumber, LocalDate fromDate, LocalDate toDate) {
		return bookingDb.getRoomIfAvailable(hotelId, roomNumber, fromDate, toDate);
	}
}

