package no.ctrlc.hotels;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component
@Scope("prototype")
public class Hotel {
	
	@NotNull(message="Hotelid Cannot be null")
	@Min(value=1,  message="Hotel id must be between 1 and 99")
	@Max(value=99, message="Hotel id must be between 1 and 99")
	Integer id;
	
	@NotNull(message="Hotelname cannot be null")
	@Pattern(regexp="(^Artic Hotel$|^Polar Hotel$|^Frost Hotel$)" , message = "Hotel name must be either Artic Hotel, Polar Hotel or Frost Hotel" )
	@Size(min=1,max=50, message="Hotelname must be between 1 and 50 characters")
	private String name;
	
	@NotNull(message="Hotel address cannot be null")
	@Size(min=6, max=100, message="Hotel address must be between 6 and 100 characters")
	private String address;
	
	@NotBlank(message="Hotels email address cannot be null nor empty")
	@Email(message = "Email address must be valid")
    private String emailAddress;
	
	@NotNull(message="HotelPhoneNumber cannot be null")
	@Pattern(regexp="(^(\\+)?\\d{3,15}$)" , message = "Hotel phonenumber must be an optional + and between 3 and 15 digits" )
	private String phoneNumber;
		

	public Integer getId() {
		return id;
	}

	
	public void setId(Integer id) {
		this.id = id;
	}

	
	public String getName() {
		return name;
	}

	
	public void setName(String name) {
		this.name = name;
	}


	public String getAddress() {
		return address;
	}

	
	public void setAddress(String address) {
		this.address = address;
	}


	public String getEmailAddress() {
		return emailAddress;
	}

	
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}


	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}


	public String toString() {
		return "Hotel name:"+name+" address:"+address+" email address:"+emailAddress+" phonenumber:"+phoneNumber;
	}
}