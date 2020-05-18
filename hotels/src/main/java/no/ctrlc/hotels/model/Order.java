package no.ctrlc.hotels.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.validation.GroupSequence;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import no.ctrlc.hotels.util.Utilities;

@Component("order")
@Scope("prototype")
public class Order {
	
	@NotNull(message="Order id must not be null", groups=SecondStep.class )
	private UUID orderId;
	
	@NotNull(message="OrderCreatedDateTime must not be null", groups=SecondStep.class )
	private LocalDateTime orderCreatedDateTime;
	
	@Valid // recursive validation if it's not null
	@NotNull( message="customer must not be null", groups=FirstStep.class )
	public Customer customer;
	
	@Valid // recursive validation if it's not null
	@NotNull( message="hotel must not be null", groups = {DatesAndHotel.class, FirstStep.class} )	
	public Hotel hotel;

	@Valid // recursive validation if it's not null
	@NotNull( message="dates must not be null", groups = {DatesAndHotel.class, FirstStep.class} )
	public Dates dates;
	
	@Valid // recursive validation if it's not null
	@NotNull( message="room must not be null", groups=FirstStep.class )
	public Room  room;
	
	@NotNull( message = "Total price must not be null", groups=FirstStep.class )
	@Positive( message = "Total price must be positive", groups=FirstStep.class )
	@Digits(integer=7, fraction=2, message="Price has a limit of 7+2 digits", groups=FirstStep.class )
	private BigDecimal totalPrice; // total price when the order was created.

	private Utilities utilities = new Utilities();
	
	public Order() {}
	
	
	public void setOrderId (UUID orderUUID) {this.orderId=orderUUID;}

	
	public UUID getOrderId() {return orderId;}
	
	
	public void setOrderCreatedDateTime(LocalDateTime orderCreatedDateTime) {this.orderCreatedDateTime=orderCreatedDateTime;}

	
	public LocalDateTime getOrderCreatedDateTime() {return orderCreatedDateTime;}
	
	
	public void setCustomer(Customer customer) {this.customer=customer;}
	
	
	public Customer getCustomer() {return customer;}	
	
	
	public void setHotel(Hotel hotel) {this.hotel=hotel;}

	
	public Hotel getHotel() {return hotel;}
	
	
	public void setRoom(Room room) {
		this.room=room;
	}
	
	
	public Room getRoom() {return room;}
	
	
	public Dates getDates() { return dates;}

	
	public void setDates(Dates dates) { 
		this.dates = dates;
	}			

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	
	public BigDecimal getTotalPrice() {return totalPrice;}
	
	
	public void calulateTotalPrice() {	
		try {	
			totalPrice = utilities.calculateTotalPrice( dates, room.getRoomPrice() );
		}
		catch (IllegalArgumentException e) {
			setTotalPrice(null);
		}
		return;
	}
	
	
	// validation group 1 for validating before id i set by service
	public interface FirstStep {}
	
	// Special validation of group DatesAndHotel for validating that we have valid dates 
	// and a valid hotel before we can order room
	public interface DatesAndHotel {}	
	
	// validation group 2 for validating after id is set by service
	public interface SecondStep {}

	// Validating Total.class group will validate FirstStep group, and only if FirstStep group not fails, it will validate the SecondStep group.
	@GroupSequence({FirstStep.class, DatesAndHotel.class, SecondStep.class})
	public interface Total {}	
}