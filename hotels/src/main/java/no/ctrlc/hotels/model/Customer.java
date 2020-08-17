package no.ctrlc.hotels.model;

import java.util.UUID;

import javax.validation.GroupSequence;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class Customer {

    public Customer() {
    }

    @NotNull(message = "UUID cannot be null", groups = SecondStep.class)
    private UUID id;

    @NotNull(message = "Name cannot be null", groups = FirstStep.class)
    @Size(min = 3, max = 100, message = "Name should be between 3 and 100 characters", groups = FirstStep.class)
    private String name;

    @NotNull(message = "Address cannot be null", groups = FirstStep.class)
    @Size(min = 6, max = 100, message = "Address should be between 6 and 100 characters", groups = FirstStep.class)
    private String address;

    @Email(message = "Email address must be valid", groups = FirstStep.class)
    @NotBlank(message = "Email address must not be null or blank", groups = FirstStep.class) // @Email allows null and
                                                                                             // blank as valid email so
                                                                                             // we need to set this.
    private String emailAddress;

    @Pattern(regexp = "(^$|^(\\+)?\\d{3,15}$)", message = "Phonenumber should be blank or an optional + and between 3 and 15 digits", groups = FirstStep.class)
    private String phoneNumber;

    public void setId( UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public void setName( String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setAddress( String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setEmailAddress( String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setPhoneNumber( String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    // validation group 1 for validating before id i set by service
    public interface FirstStep {
    }

    // validation group 2 for validating after id is set by service
    public interface SecondStep {
    }

    // Validating Total.class group will validate FirstStep group, and only if
    // FirstStep group not fails, it will validate the SecondStep group.
    @GroupSequence({ FirstStep.class, SecondStep.class })
    public interface Total {
    }
}