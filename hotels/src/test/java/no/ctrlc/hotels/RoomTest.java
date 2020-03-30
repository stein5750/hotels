package no.ctrlc.hotels;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.math.BigDecimal;
import java.util.Set;
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
import org.junit.runner.RunWith;

import no.ctrlc.hotels.Room;

@RunWith(JUnitPlatform.class)
class RoomTest {

	private Set<ConstraintViolation<Room>> constraintViolations;
	private ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private Validator validator = factory.getValidator();
    private Room room;
	
    @BeforeAll
    private static void setup() {}
    
    @BeforeEach
    private void init() {
      room=beforeEachCreateValidRoom();
    }	
    
	@Test
	void testRoom() {
		constraintViolations = validator.validate( room);
		assertTrue( constraintViolations.isEmpty() );
	}
    
	

	@ParameterizedTest
	@NullSource
	void testSetHotelIdNull(Integer i) {
		room.setHotelId(i);
		constraintViolations = validator.validate(room);
		assertFalse( constraintViolations.isEmpty());
		assertEquals ("HotelId cannot not be null",constraintViolations.iterator().next().getMessage());
	}
	
	@Test
	void testSetHotelIdMin() {
		room.setHotelId(0);
		constraintViolations = validator.validate(room);
		assertFalse( constraintViolations.isEmpty());
		assertEquals( "HotelId must be between 1 and 99",constraintViolations.iterator().next().getMessage());
	}
	
	
	@Test
	void testSetHotelIdMax() {
		room.setHotelId(100);
		constraintViolations = validator.validate(room);
		assertFalse( constraintViolations.isEmpty());
		assertEquals( "HotelId must be between 1 and 99",constraintViolations.iterator().next().getMessage());
	}
	
	@Test
	void testGetHotelId() {
		Integer hotelId=33;
		room.setHotelId(hotelId);
		constraintViolations = validator.validate(room);
		assertTrue( constraintViolations.isEmpty());
		assertEquals( hotelId, room.getHotelId());
	}
	
	   @ParameterizedTest
	   @NullSource
	   void testSetRoomNumbernull(Integer i) {
		   room.setRoomNumber(i);
		   constraintViolations = validator.validate(room);
		   assertFalse( constraintViolations.isEmpty());
		   assertEquals ("Roomnumber cannot not be null",constraintViolations.iterator().next().getMessage());
	   }

	@Test
	void testSetRoomNumberMin() {
		room.setRoomNumber(0);
		constraintViolations = validator.validate(room);
		assertFalse( constraintViolations.isEmpty());
		assertEquals( "Roomnumber must be between 1 and 9999",constraintViolations.iterator().next().getMessage());
	}
	
	
	@Test
	void testSetRoomNumberMax() {
		room.setRoomNumber(10000);
		constraintViolations = validator.validate(room);
		assertFalse( constraintViolations.isEmpty());
		assertEquals( "Roomnumber must be between 1 and 9999",constraintViolations.iterator().next().getMessage());
	}

	@Test
	void testGetRoomNumber() {
		Integer roomNumber=333;
		room.setRoomNumber(roomNumber);
		constraintViolations = validator.validate(room);
		assertTrue( constraintViolations.isEmpty());
		assertEquals( roomNumber , room.getRoomNumber());
	}
	

	@ParameterizedTest
	@NullSource
	void testSetTypeNull(String type) {
		room.setType(type);
		constraintViolations = validator.validate(room);
		assertFalse( constraintViolations.isEmpty());
		assertEquals("RoomType cannot not be null", constraintViolations.iterator().next().getMessage());
	}
	
	
	@ParameterizedTest
	@ValueSource(
		strings={"single","double","suite"}
	)
	void testSetTypeValid(String type) {
		room.setType(type);
		constraintViolations = validator.validate(room);
		assertTrue (constraintViolations.isEmpty());
	}
	
	
	@ParameterizedTest
	@ValueSource(
			strings={"","a","abc","123"}
			)
	void testSetTypeInvalid(String type) {
		room.setType(type);
		constraintViolations = validator.validate(room);
		assertFalse (constraintViolations.isEmpty());
		assertEquals("RoomType must be either single, double or suite. Not blank", constraintViolations.iterator().next().getMessage());
	}
	
	


	@Test
	void testGetType() {
		String roomType="double";
		room.setType(roomType);
		assertEquals(roomType, room.getRoomType());
	}

	@ParameterizedTest
	@NullSource
	void testSetPriceNull(BigDecimal price) {
		room.setRoomPrice(price);
		constraintViolations = validator.validate(room);
		assertFalse(constraintViolations.isEmpty());
		assertEquals("Price cannot be null",constraintViolations.iterator().next().getMessage());
	}
	
	
	@Test
	void testSetPriceNegative() {
		room.setRoomPrice(new BigDecimal("-1"));
		constraintViolations = validator.validate(room);
		assertFalse(constraintViolations.isEmpty());
		assertEquals("Price must be positive",constraintViolations.iterator().next().getMessage());
	}
	
	@ParameterizedTest
	@ValueSource(strings={"1","2.2","3.33","987654321.12"})
	void testSetPriceValid(String s) {
		BigDecimal price=new BigDecimal(s);
		room.setRoomPrice(price);
		constraintViolations = validator.validate(room);
		assertTrue(constraintViolations.isEmpty());
	}
	
	@ParameterizedTest
	@ValueSource(strings={"9876543210.12","987654321.123","98765432121"})
	void testSetPriceInvalid(String s) {
		BigDecimal price=new BigDecimal(s);
		room.setRoomPrice(price);
		constraintViolations = validator.validate(room);
		assertFalse(constraintViolations.isEmpty());
		assertEquals("Price has a limit of 9 digits including two decimals",constraintViolations.iterator().next().getMessage());
	}

	
	@Test
	void testGetPrice() {
		BigDecimal price=new BigDecimal("200");
		room.setRoomPrice(price);
		assertEquals(price,room.getRoomPrice());
	}
	
	// beforEach
	private Room beforeEachCreateValidRoom() {
		Room room=new Room();
		room.setHotelId(1);
		room.setRoomNumber(101);
		room.setType("suite");
		room.setRoomPrice(BigDecimal.valueOf(1000));
		return room;
	}

}
