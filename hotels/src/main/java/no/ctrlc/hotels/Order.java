package no.ctrlc.hotels;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class Order {
		
	@NotNull(message="Order UUID cannot be null")
	private UUID orderUUID;
	
	@NotNull(message="OrderCreatedDateTime cannot be null")
	private LocalDateTime orderCreatedDateTime;
	
	@NotNull
	public Customer reservingCustomer;
	
	@NotNull  
	public Hotel reservedHotel;

	@NotNull
	public Room  reservedRoom;
	
	@NotNull( message = "FromDate must not be null")
	@FutureOrPresent( message = "FromDate must be future or present")
	private LocalDate fromDate;
	
	@NotNull( message = "ToDate must not be null")
	@Future( message = "ToDate must be future")
	private LocalDate toDate;
	
	@NotNull( message = "Total price must not be null")
	@Positive( message = "Total price must be positive")
	@Digits(integer=7, fraction=2, message="Price has a limit of 7+2 digits")
	private BigDecimal totalPrice; // total price at the time of the ordering


	public Order() {}
	
	public void setOrderUUID (UUID orderUUID) {this.orderUUID=orderUUID;}

	public UUID getOrderUUID() {return orderUUID;}
	
	public void setOrderCreatedDateTime(LocalDateTime orderCreatedDateTime) {this.orderCreatedDateTime=orderCreatedDateTime;}

	public LocalDateTime getOrderCreatedDateTime() {return orderCreatedDateTime;}
	
	public void setReservingCustomer(Customer reservingCustomer) {this.reservingCustomer=reservingCustomer;}
	
	public Customer getReservingCustomer() {return reservingCustomer;}	
	
	public void setReservedHotel(Hotel reservedHotel) {this.reservedHotel=reservedHotel;}

	public Hotel getReservedHotel() {return reservedHotel;}
			
	public void setReservedRoom(Room reservedRoom) {this.reservedRoom=reservedRoom;}

	public Room getReservedRoom() {return reservedRoom;}
			
	public void setFromDate(LocalDate fromDate) {
		this.fromDate=fromDate;
	}

	
	public LocalDate getFromDate() {return fromDate;}
	
	
	public void setToDate(LocalDate toDate) {
		this.toDate=toDate;
	}
	
	public LocalDate getToDate() {return toDate;}
	
	public void setTotalPrice(BigDecimal totalPrice) {this.totalPrice=totalPrice;}
	
	public BigDecimal getTotalPrice() {return totalPrice;}
	
	@AssertTrue(message="FromDate must be before ToDate")
	boolean fromDateIsBeforeToDate() {
		if (fromDate == null || toDate==null ) return false;
		return  fromDate.compareTo(toDate) < 0 ? true : false;
	}
	
	
	public void CalculatetotalPrice() {
		if ( fromDateIsBeforeToDate() == false || reservedRoom == null) return;
		long days = ChronoUnit.DAYS.between(fromDate, toDate);
		totalPrice = reservedRoom.getRoomPrice().multiply(new BigDecimal(days));
	}
	
	
	public String toString() {
		return "Order:\n"+
				"order id="+orderUUID+"\n"+
				"order created="+orderCreatedDateTime.toString()+"\n"+
				"customer:\n"+
				reservingCustomer.toString()+"\n"+
				"Hotel:\n"+
				reservedHotel.toString()+"\n"+
				"Room:\n"+
				reservedRoom.getRoomNumber()+"+\n"+
				"	from date="+getFromDate()+"\n"+
				"	to date="+getToDate()+"\n"+
				"	price for reservation="+getTotalPrice().toPlainString()+"\n";
	}
}



