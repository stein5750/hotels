package no.ctrlc.hotels;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class Room {
	
	@NotNull(message="HotelId cannot not be null")
	@Min(value=1, message="HotelId must be between 1 and 99")
	@Max(value=99, message="HotelId must be between 1 and 99")
	private Integer hotelId; 
	
	@NotNull(message="Roomnumber cannot not be null")
	@Min(value=1, message="Roomnumber must be between 1 and 9999")
	@Max(value=999, message="Roomnumber must be between 1 and 9999")
	private Integer roomNumber; 

	@NotNull(message="RoomType cannot not be null")
	@Pattern(regexp="(^single$|^double$|^suite$)" , message = "RoomType must be either single, double or suite. Not blank" )
	private String roomType; // single, double, suite
	
	@NotNull(message="Price cannot be null")
	@Positive(message="Price must be positive")
	@Digits(integer=9, fraction=2, message="Price has a limit of 9 digits including two decimals")
	private BigDecimal roomPrice;
	
	public void setHotelId(Integer hotelId) { this.hotelId=hotelId;}

	public Integer getHotelId() { return hotelId;}
	
	public void setRoomNumber(Integer roomNumber) { this.roomNumber=roomNumber;}

	public Integer getRoomNumber() { return roomNumber;}
	
	public void setType(String type) {	this.roomType=type;}

	public String getRoomType() { return roomType;}
	
	public void setRoomPrice(BigDecimal price) { this.roomPrice=price;}
	
	public BigDecimal getRoomPrice() { return roomPrice;}
	
	public String toString() {
		return "hotelId:"+hotelId+" roomNumber:"+roomNumber+" type:"+roomType+" price per day:"+roomPrice;
	}
}
