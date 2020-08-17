package no.ctrlc.hotels.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;
import java.util.UUID;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

@RunWith(JUnitPlatform.class)
@SelectPackages("no.ctrlc.bookingsystem")
@ContextConfiguration("/Hotels/src/main/resources/META-INF/Beans.xml")
class CustomerTest {

    private Set<ConstraintViolation<Customer>> constraintViolations;
    private ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private Validator validator = factory.getValidator();
    private static Customer customer;

    @BeforeAll
    private static void setup() {
    }

    @BeforeEach
    private void init() {
        customer = beforeEachCreateValidCustomer();
    }

    @Test
    void testCustomer() {
        constraintViolations = validator.validate(customer, Customer.Total.class);
        assertTrue(constraintViolations.isEmpty());
    }

    @Test
    void testGetId() {
        UUID u = UUID.fromString("48863637-d0f0-478f-bb98-c4e33680d7fc");
        customer.setId(u);
        assertEquals(u, customer.getId());
    }

    @ParameterizedTest
    @NullSource
    void testSetId( UUID u) {
        customer.setId(u);
        constraintViolations = validator.validate(customer, Customer.Total.class);
        assertFalse(constraintViolations.isEmpty());
        assertEquals("UUID cannot be null", constraintViolations.iterator().next().getMessage());
    }

    @Test
    void testGetName() {
        String name = "Bruce Wayne";
        customer.setName(name);
        assertEquals(name, customer.getName());
    }

    @ParameterizedTest
    @NullSource
    public void testSetNameNotNull( String input) {
        customer.setName(input);
        constraintViolations = validator.validate(customer, Customer.Total.class);
        assertFalse(constraintViolations.isEmpty());
        assertEquals("Name cannot be null", constraintViolations.iterator().next().getMessage());
    }

    // testing for min (@min=3)
    @Test
    public void testSetNameMin() {
        customer.setName("Aa");
        constraintViolations = validator.validate(customer, Customer.Total.class);
        assertFalse(constraintViolations.isEmpty());
        assertEquals("Name should be between 3 and 100 characters",
                constraintViolations.iterator().next().getMessage());
    }

    // testing for max (@max=100)
    @Test
    public void testSetNameMax() {
        customer.setName(
                "Aaaaaaaaaa Bbbbbbbbbb Ccccccccccc Dddddddddd Eeeeeeeeeee Ffffffffff Eeeeeeeeeee Ffffffffff Gggggggggg");
        constraintViolations = validator.validate(customer, Customer.Total.class);
        assertFalse(constraintViolations.isEmpty());
        assertEquals("Name should be between 3 and 100 characters",
                constraintViolations.iterator().next().getMessage());
    }

    @Test
    void testGetAddress() {
        String address = "Gotham City";
        customer.setAddress(address);
        assertEquals(address, customer.getAddress());
    }

    @ParameterizedTest
    @NullSource
    void testSetAddressNotNull( String input) {
        customer.setAddress(input);
        constraintViolations = validator.validate(customer, Customer.Total.class);
        assertFalse(constraintViolations.isEmpty());
        assertEquals("Address cannot be null", constraintViolations.iterator().next().getMessage());
    }

    @Test
    void testSetAddressMin() {
        customer.setAddress("Abcde");
        constraintViolations = validator.validate(customer, Customer.Total.class);
        assertFalse(constraintViolations.isEmpty());
        assertEquals("Address should be between 6 and 100 characters",
                constraintViolations.iterator().next().getMessage());
    }

    @Test
    void testSetAddressMax() {
        customer.setAddress(
                "Aaaaaaaaa Bbbbbbbbb Ccccccccc Ddddddddd Eeeeeeeee Fffffffff Eeeeeeeee Fffffffff Ggggggggg hhhhhhhhh0");
        constraintViolations = validator.validate(customer, Customer.Total.class);
        assertTrue(constraintViolations.isEmpty());
    }

    @Test
    void testSetAddressMaxInvalid() {
        customer.setAddress(
                "Aaaaaaaaa Bbbbbbbbb Ccccccccc Ddddddddd Eeeeeeeee Fffffffff Eeeeeeeee Fffffffff Ggggggggg hhhhhhhhh 1");
        constraintViolations = validator.validate(customer, Customer.Total.class);
        assertFalse(constraintViolations.isEmpty());
        assertEquals("Address should be between 6 and 100 characters",
                constraintViolations.iterator().next().getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = { "a@b", // per definition a@b is a valid email address.
            "test@test.no", "test.test@test.no", "test.test.test@test.no", "test@test.test.no", "test@test.com",
            "test123@test23.no", "123test.test456.test234@test456.com" })
    void testSetEmailAddressValid( String emailAddress) {
        customer.setEmailAddress(emailAddress);
        constraintViolations = validator.validate(customer, Customer.Total.class);
        assertTrue(constraintViolations.isEmpty());
    }

    @Test
    void testSetEmailAddressNull() {
        customer.setEmailAddress(null);
        constraintViolations = validator.validate(customer, Customer.Total.class);
        assertFalse(constraintViolations.isEmpty());
        assertEquals("Email address must not be null or blank", constraintViolations.iterator().next().getMessage());
    }

    @Test
    void testSetEmailAddressBlank() {
        customer.setEmailAddress("");
        constraintViolations = validator.validate(customer, Customer.Total.class);
        assertFalse(constraintViolations.isEmpty());
        assertEquals("Email address must not be null or blank", constraintViolations.iterator().next().getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = { ".test@test.no", "test@test.", "testtest.no", "test.test.no", "test@@test.no",
            "test.@test.no", "test@test.no@", "@", "@test.no", "test@" })
    void testSetEmailAddressInvalid( String emailAddress) {
        customer.setEmailAddress(emailAddress);
        constraintViolations = validator.validate(customer, Customer.Total.class);
        assertFalse(constraintViolations.isEmpty());
        assertEquals("Email address must be valid", constraintViolations.iterator().next().getMessage());
    }

    @Test
    void testGetEmailAddress() {
        String emailAddress = "email@emailhost.com";
        customer.setEmailAddress(emailAddress);
        assertEquals(emailAddress, customer.getEmailAddress());
    }

    @ParameterizedTest
    @ValueSource(strings = { "", "123", "+123", "123456789012345", "+123456789012345" })
    void testSetPhoneNumberValid( String phoneNumber) {
        customer.setPhoneNumber(phoneNumber);
        constraintViolations = validator.validate(customer, Customer.Total.class);
        assertTrue(constraintViolations.isEmpty());
    }

    @ParameterizedTest
    @ValueSource(strings = { "+", "12", "++555", "555+", "555 555", "1234567901234567890", "abcde", "123-456", " 12345",
            "12345 " })
    void testSetPhoneNumberInvalid( String phoneNumber) {
        customer.setPhoneNumber(phoneNumber);
        constraintViolations = validator.validate(customer, Customer.Total.class);
        assertFalse(constraintViolations.isEmpty());
        assertEquals("Phonenumber should be blank or an optional + and between 3 and 15 digits",
                constraintViolations.iterator().next().getMessage());
    }

    @Test
    void testGetPhoneNumber() {
        String phoneNumber = "55512345";
        customer.setPhoneNumber(phoneNumber);
        assertEquals(phoneNumber, customer.getPhoneNumber());
    }

    // beforEach
    private Customer beforeEachCreateValidCustomer() {
        Customer customer = new Customer();
        customer.setId(UUID.fromString("48863637-d0f0-478f-bb98-c4e33680d7fc"));
        customer.setName("Bruce Wayne");
        customer.setEmailAddress("bruce.wayne@gotham.city");
        customer.setAddress("Wayne Manor, 1007 Mountain Drive, Gotham");
        customer.setPhoneNumber("5551234567");
        return customer;
    }

}
