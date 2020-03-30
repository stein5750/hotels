package no.ctrlc.hotels;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Set;
import java.util.Stack;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import no.ctrlc.hotels.Hotel;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

@RunWith(JUnitPlatform.class)
class HotelTest {
	
	private Hotel hotel;
	private Set<ConstraintViolation<Hotel>> constraintViolations;
	private ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private Validator validator = factory.getValidator();
    //private Hotel hotel;
	
    @BeforeAll
    private static void setup() {}
    
    @BeforeEach
    private void init() {
      hotel=beforeEachCreateValidHotel();
    }	
	
	@Test
	void testHotel() {
		constraintViolations = validator.validate( hotel);
		assertTrue( constraintViolations.isEmpty() );
		
	}

		
	@Test
	void testGetHotelId() {
		Integer hotelId=22;
		hotel.setId(hotelId);
			assertEquals(hotelId,hotel.getId());
	}

	@ParameterizedTest
	@NullSource
	public void testSetHotelIdNull(Integer hotelId) {
	   hotel.setId(hotelId);
	   constraintViolations = validator.validate(hotel);
	   assertFalse( constraintViolations.isEmpty() );
	   assertEquals("Hotelid Cannot be null", constraintViolations.iterator().next().getMessage());
   }
	
	@ParameterizedTest
	@ValueSource(
		ints={1,10,99}
	)
	void testSetHotelIdValid(Integer hotelId) {
		hotel.setId(hotelId);
		constraintViolations = validator.validate(hotel);
		assertTrue (constraintViolations.isEmpty());
	}
	
	@ParameterizedTest
	@ValueSource(
			ints={0,100}
			)
	void testSetHotelIdInvalid(Integer hotelId) {
		hotel.setId(hotelId);
		constraintViolations = validator.validate(hotel);
		assertFalse (constraintViolations.isEmpty());
		assertEquals("Hotel id must be between 1 and 99", constraintViolations.iterator().next().getMessage());
	}
	
	
	@Test
	void testGetHotelName() {
		String name="Polar Hotel";
		hotel.setName(name);
			assertEquals(name,hotel.getName());
		}

	@ParameterizedTest
	@NullSource
	public void testSetHotelNameNotNull(String input) {
	   hotel.setName(input);
	   constraintViolations = validator.validate(hotel);
	   assertFalse( constraintViolations.isEmpty() );
	   assertEquals("Hotelname cannot be null", constraintViolations.iterator().next().getMessage());
   }
	
	@ParameterizedTest
	@ValueSource(
		strings={"Artic Hotel","Polar Hotel","Frost Hotel"}
	)
	void testSetHotelNameValid(String hotelName) {
		hotel.setName(hotelName);
		constraintViolations = validator.validate(hotel);
		assertTrue (constraintViolations.isEmpty());
	}
	
	
	@ParameterizedTest
	@ValueSource(
			strings={"a","abc","123","Abcd Efgh"}
			)
	void testSetHotelNameInvalid(String hotelName) {
		hotel.setName(hotelName);
		constraintViolations = validator.validate(hotel);
		assertFalse (constraintViolations.isEmpty());
		assertEquals("Hotel name must be either Artic Hotel, Polar Hotel or Frost Hotel", constraintViolations.iterator().next().getMessage());
	}
		
	
	// testing for min (@min=1)
	@Test
	public void testSetHotelNameMin() {
		hotel.setName("");
		constraintViolations = validator.validate( hotel);
		assertFalse( constraintViolations.isEmpty());
		Stack<String> stack = new Stack<String> ();
		   for (ConstraintViolation<Hotel> item:constraintViolations) {
			   stack.add(item.getMessageTemplate());
		   }
		   assertTrue(stack.contains("Hotelname must be between 1 and 50 characters"));	
	}
	
	   // testing for max (@max=50)
	   @Test
	   public void testSetHotelNameMax() {
		   hotel.setName("Aaaaaaaaa Bbbbbbbbb Ccccccccc Ddddddddd Eeeeeeeee F");
		   constraintViolations = validator.validate( hotel);
		   assertFalse( constraintViolations.isEmpty());
			Stack<String> stack = new Stack<String> ();
			   for (ConstraintViolation<Hotel> item:constraintViolations) {
				   stack.add(item.getMessageTemplate());
			   }
			   assertTrue(stack.contains("Hotelname must be between 1 and 50 characters"));
	   }	

	
	@Test
	void testGetHotelAddress() {
		String address="Duckburg 5";
		hotel.setAddress(address);
		assertEquals(address,hotel.getAddress());
	}

	@ParameterizedTest
	@NullSource
	public void testSetHotelAddressNotNull(String input) {
	   hotel.setAddress(input);
	   constraintViolations = validator.validate(hotel);
	   assertFalse( constraintViolations.isEmpty() );
	   assertEquals("Hotel address cannot be null", constraintViolations.iterator().next().getMessage());
   }

	
	// testing for min (@min=1)
	@Test
	public void testSetHotelAddressMin() {
		hotel.setAddress("Abcd5");
		constraintViolations = validator.validate( hotel);
		assertFalse( constraintViolations.isEmpty());
		assertEquals ("Hotel address must be between 6 and 100 characters",constraintViolations.iterator().next().getMessage());
	}	
	
	
	   // testing for max (@max=50)
	   @Test
	   public void testSetHotelAddressMax() {
		   hotel.setAddress("Aaaaaaaaa Bbbbbbbbb Ccccccccc Ddddddddd Eeeeeeeee Aaaaaaaaa Bbbbbbbbb Ccccccccc Ddddddddd Eeeeeeeee F");
		   constraintViolations = validator.validate( hotel);
		   assertFalse( constraintViolations.isEmpty());
		   assertEquals ("Hotel address must be between 6 and 100 characters",constraintViolations.iterator().next().getMessage());
	   }
	
	
	@Test
	void testGetHotelEmailAddress() {
		String emailAddress="service@polarhotel.com";
		hotel.setEmailAddress(emailAddress);
		assertEquals(emailAddress,hotel.getEmailAddress());
	}
	
	
	@ParameterizedTest
	@NullSource
	@EmptySource
   public void testSetHotelEmailAddressNullNorEmpty(String input) {
	   hotel.setEmailAddress(input);
	   constraintViolations = validator.validate(hotel);
	   assertFalse( constraintViolations.isEmpty() );
	   assertEquals("Hotels email address cannot be null nor empty", constraintViolations.iterator().next().getMessage());
   }
	
	
	@ParameterizedTest
	@ValueSource(strings={
			"a@b", // per definition a@b is a valid email address.
			"test@test.no",
			"test.test@test.no",
			"test.test.test@test.no",
			"test@test.test.no",
			"test@test.com",
			"test123@test23.no",
			"123test.test456.test234@test456.com"
			})
	void testSetHotelEmailAddressValid(String hotelEmailAddress) {
		hotel.setEmailAddress(hotelEmailAddress);
		constraintViolations = validator.validate( hotel);
		assertTrue( constraintViolations.isEmpty());
	}
	   
   
	@ParameterizedTest
	@ValueSource(
		strings={
			".test@test.no",
			"test@test.",
			"testtest.no",
			"test.test.no",
			"test@@test.no",
			"test.@test.no",
			"test@test.no@",
			"@",
			"@test.no",
			"test@",
		}
	)
	void testSetHotelEmailAddressInvalid(String hotelEmailAddress) {
		hotel.setEmailAddress(hotelEmailAddress);
		constraintViolations = validator.validate( hotel);
		assertFalse( constraintViolations.isEmpty());
		assertEquals ("Email address must be valid",constraintViolations.iterator().next().getMessage());
	}


	
	@Test
	void testGetHotelPhoneNumber() {
		String hotelPhoneNumber="55512345";
		hotel.setPhoneNumber(hotelPhoneNumber);
		assertEquals(hotelPhoneNumber,hotel.getPhoneNumber());
	}

	
	@ParameterizedTest
	@NullSource
   public void testSetHotelPhoneNumberNull(String input) {
	   hotel.setPhoneNumber(input);
	   constraintViolations = validator.validate(hotel);
	   assertFalse( constraintViolations.isEmpty() );
	   assertEquals("HotelPhoneNumber cannot be null", constraintViolations.iterator().next().getMessage());
   }	
	
	
	@ParameterizedTest
	@ValueSource(
		strings={
			"123",
			"+123",
			"123456789012345",
			"+123456789012345"
		}
	)
	void testSetHotelPhoneNumberValid(String hotelPhoneNumber) {
		hotel.setPhoneNumber(hotelPhoneNumber);
		constraintViolations = validator.validate( hotel);
		assertTrue( constraintViolations.isEmpty()); 
	}
	   
   
	@ParameterizedTest
	@ValueSource(
		strings={
			"+",
			"12",
			"++555",
			"555+",
			"555 555",
			"1234567901234567890",
			"abcde",
			"123-456",
			" 12345",
			"12345 "
		}
	)
	void testSetHotelPhoneNumberInvalid(String hotelPhoneNumber) {
		hotel.setPhoneNumber(hotelPhoneNumber);
		constraintViolations = validator.validate( hotel);
		assertFalse( constraintViolations.isEmpty());
		assertEquals ("Hotel phonenumber must be an optional + and between 3 and 15 digits",constraintViolations.iterator().next().getMessage());
	}	

	//beforeEach
	private Hotel beforeEachCreateValidHotel() {
		Hotel hotel = new Hotel();
		hotel.setId(1);
		hotel.setName("Artic Hotel");
		hotel.setAddress("Atlantic Road 123, 0123 Vest");
		hotel.setEmailAddress("artichotel@hotels.no");
		hotel.setPhoneNumber("5551234567");
		return hotel;
	}


	
}
