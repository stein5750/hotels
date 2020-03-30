package no.ctrlc.hotels;

import java.util.UUID;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;


@Component
@Scope("prototype")
public class Customer {
	
	@NotNull(message="UUID cannot be null")
	private UUID id;

	@NotNull(message="Name cannot be null")
	@Size(min=3, max=100, message="Name should be between 3 and 100 characters")
	private String name;
	
	@NotNull(message="Address cannot be null")
	@Size(min=6, max=100, message="Address should be between 6 and 100 characters")
	private String address;

	@Email(message = "Email address must be valid")
    private String emailAddress;
	
	@Pattern(regexp="(^$|^(\\+)?\\d{3,15}$)" , message = "Phonenumber should be blank or an optional + and between 3 and 15 digits" )
	private String phoneNumber;
		
	public Customer() {}
	
	public void setId(UUID id) {this.id=id;}
	
	public UUID getId() {return id;}
	
	public void setName(String name) {this.name=name;}
	
	public String getName() {return name;}
	
	public void setAddress(String address) {this.address=address;}
	
	public String getAddress() {return address;}

	public void setEmailAddress(String emailAddress) {this.emailAddress=emailAddress;}
	
	public String getEmailAddress() {return emailAddress;}	
	
	public void setPhoneNumber(String phoneNumber) {this.phoneNumber=phoneNumber;}
	
	public String getPhoneNumber() {return phoneNumber;}	
	
	public String toString() {
		return "Customer:\n"+
				"id="+id+"\n"+
				"name="+name+"\n"+
				"address="+address+"\n"+
				"emailAddress="+emailAddress+"\n"+
				"phoneNumber="+phoneNumber+"\n";
	}
}