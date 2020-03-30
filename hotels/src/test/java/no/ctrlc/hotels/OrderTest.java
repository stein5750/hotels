package no.ctrlc.hotels;

import static org.junit.jupiter.api.Assertions.*;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.executable.ExecutableValidator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.test.util.ReflectionTestUtils;

import no.ctrlc.hotels.Customer;
import no.ctrlc.hotels.Hotel;
import no.ctrlc.hotels.Order;
import no.ctrlc.hotels.Room;

@RunWith(JUnitPlatform.class)
class OrderTest {


	private Set<ConstraintViolation<Order>> constraintViolations;
	private ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private Validator validator = factory.getValidator();
    private Order order;
	
    
    @BeforeAll
    private static void setup() {}
   
    
   @BeforeEach
   private void init() {
      order=beforeEachCreateValidOrder();
   }	
	
	@Test
	void testOrder() {
		constraintViolations = validator.validate( order);
		assertTrue( constraintViolations.isEmpty() );
	}
	

	@ParameterizedTest
	@NullSource
	void testSetOrderUUID(UUID u) {
		order.setOrderUUID(u);
		constraintViolations = validator.validate( order);
		assertFalse( constraintViolations.isEmpty() );
		assertEquals( "Order UUID cannot be null", constraintViolations.iterator().next().getMessage());
	}


	@Test
	void testGetOrderUUID() {
		UUID u=UUID.fromString("e5302e92-d16b-4a8d-91ff-317dcf25efa3");
		order.setOrderUUID(u);
		assertEquals(u, order.getOrderUUID());
	}

	@ParameterizedTest
	@NullSource
	void testSetOrderCreatedDateTime(LocalDateTime dt) {
		order.setOrderCreatedDateTime(dt);
		constraintViolations = validator.validate( order);
		assertFalse( constraintViolations.isEmpty() );
		assertEquals( "OrderCreatedDateTime cannot be null", constraintViolations.iterator().next().getMessage());
	}

	
	@Test
	void testGetOrderCreatedDateTime() {
		LocalDateTime dt=LocalDateTime.of(2020, 11, 20, 21, 30);
		order.setOrderCreatedDateTime(dt);
		assertEquals(dt,order.getOrderCreatedDateTime());
	}
	
	@Test
	void testCustomerNotNull() {
		order.setReservingCustomer(null);
		constraintViolations = validator.validate( order);
		assertFalse( constraintViolations.isEmpty() );
		assertEquals( "must not be null", constraintViolations.iterator().next().getMessage());
	}
	
	@Test
	void testReservedHotelNotNull() {
		order.setReservedHotel(null);
		constraintViolations = validator.validate( order);
		assertFalse( constraintViolations.isEmpty() );
		assertEquals( "must not be null", constraintViolations.iterator().next().getMessage());
	}
	
	@Test
	void testReservedRoomNotNull() {
		order.setReservedRoom(null);
		constraintViolations = validator.validate( order);
		assertFalse( constraintViolations.isEmpty() );
		assertEquals( "must not be null", constraintViolations.iterator().next().getMessage());
	}

	
	@ParameterizedTest
	@NullSource
	void testSetFromDateNull(LocalDate fromDate) {
		order.setFromDate(fromDate);
		constraintViolations = validator.validate( order);
		assertFalse( constraintViolations.isEmpty() );
		assertEquals( "FromDate must not be null", constraintViolations.iterator().next().getMessage());
	}
	
	
	@ParameterizedTest
	@ValueSource(
			longs={1,10,365,3650}
			)
	void testSetFromDatePast(long days) {
		LocalDate pastDate= LocalDate.now().minusDays(days);
		order.setFromDate(pastDate);
		constraintViolations = validator.validate( order);
		assertFalse( constraintViolations.isEmpty() );
		assertEquals( "FromDate must be future or present", constraintViolations.iterator().next().getMessage());
	}
	
	
	@ParameterizedTest
	@ValueSource(
			longs={0,1,10,365,3650}
			)
	void testSetFromDateFutureOrPresent(long days) {
		LocalDate date= LocalDate.now().plusDays(days);
		order.setFromDate(date);
		constraintViolations = validator.validate( order);
		assertTrue( constraintViolations.isEmpty() );
	}


	@Test
	void testGetFromDate() {
		LocalDate date=LocalDate.now();
		order.setFromDate(date);
		LocalDate d = order.getFromDate();
		assertEquals(date,d);
		assertSame(date,d);
	}
	

	@ParameterizedTest
	@NullSource
	void testSetToDateNull(LocalDate toDate) {
		order.setToDate(toDate);
		constraintViolations = validator.validate( order);
		assertFalse( constraintViolations.isEmpty() );
		assertEquals( "ToDate must not be null", constraintViolations.iterator().next().getMessage());
		
	}
	
	
	@ParameterizedTest
	@ValueSource(
			longs={0,1,10,365,3650}
			)
	void testSetToDatePast(long days) {
		LocalDate pastDate= LocalDate.now().minusDays(days);
		order.setToDate(pastDate);
		constraintViolations = validator.validate( order);
		assertFalse( constraintViolations.isEmpty() );
		assertEquals( "ToDate must be future", constraintViolations.iterator().next().getMessage());
	}
	
	
	@ParameterizedTest
	@ValueSource(
			longs={1,10,365,3650}
			)
	void testSetToDateFutureOrPresent(long days) {
		LocalDate date= LocalDate.now().plusDays(days);
		order.setToDate(date);
		constraintViolations = validator.validate( order);
		assertTrue( constraintViolations.isEmpty() );
	}
	
	
	@Test
	void testGetToDate() {
		LocalDate date = LocalDate.now().plusDays(1);
		order.setToDate(date);
		LocalDate d = order.getToDate();
		assertEquals(date,d);
	}


	// testing return value from method
	@Test
	void testfromDateIsBeforeToDateTrue(){
	LocalDate fromDate = LocalDate.now().plusDays(1);
	LocalDate toDate   = LocalDate.now().plusDays(2);
	ReflectionTestUtils.setField( order, "fromDate", fromDate );
	ReflectionTestUtils.setField( order, "toDate", toDate );	
	assertTrue( order.fromDateIsBeforeToDate());
	}

	

	@Test
	void testfromDateIsBeforeToDateFalse1(){
	LocalDate fromDate = LocalDate.now().plusDays(1);
	LocalDate toDate   = LocalDate.now().plusDays(1);
	ReflectionTestUtils.setField( order, "fromDate", fromDate );
	ReflectionTestUtils.setField( order, "toDate", toDate );
	assertFalse( order.fromDateIsBeforeToDate());
	}
	
	
	@Test
	void testfromDateIsBeforeToDateFalse2(){
		LocalDate fromDate = LocalDate.now().plusDays(2);
		LocalDate toDate   = LocalDate.now().plusDays(1);
		ReflectionTestUtils.setField( order, "fromDate", fromDate );
		ReflectionTestUtils.setField( order, "toDate", toDate );
		assertFalse( order.fromDateIsBeforeToDate());
	}
	
	
	// parameterized testing return value from method
	@ParameterizedTest
	@MethodSource("methodRecources")
	void testfromIsBeforeToDateMethodSourceTest(LocalDate fromDate, LocalDate toDate, Boolean expectedResult){

		ReflectionTestUtils.setField( order, "fromDate", fromDate );
		ReflectionTestUtils.setField( order, "toDate", toDate );
		
		// validate method 1/2
		assertEquals( expectedResult, order.fromDateIsBeforeToDate() );
		
	    // validate method 2/2
		Method method =null;
		try {
			method = Order.class.getDeclaredMethod("fromDateIsBeforeToDate");
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		boolean returnedValue=order.fromDateIsBeforeToDate();
		if (method != null) {
			ExecutableValidator executableValidator = validator.forExecutables();
			constraintViolations = executableValidator.validateReturnValue(order,method, returnedValue);
			if ( ! constraintViolations.isEmpty()) {
				assertEquals( "FromDate must be before ToDate", constraintViolations.iterator().next().getMessage());	
			}
		}
	}
	
	
	private static Stream<Arguments> methodRecources() {
		LocalDate date = LocalDate.now().plusDays(1); // +1 for running tests at midnight
	    return Stream.of(
	      Arguments.of( date, date.plusDays(1), true),	 // fromDate is before toDate
	      Arguments.of( date, date, false),             // fromDate == toDate
	      Arguments.of( date.plusDays(1), date, false)  // fromdate is after toDate
	    );
	}
	
	

	
	@ParameterizedTest
	@NullSource
	void testSetTotalPriceNull(BigDecimal price) {
		order.setTotalPrice(price);
		constraintViolations = validator.validate( order);
		assertFalse( constraintViolations.isEmpty() );
		assertEquals( "Total price must not be null", constraintViolations.iterator().next().getMessage());
	}	
	
	
	@Test
	void testSetTotalPriceNegative() {
		order.setTotalPrice( new BigDecimal(-1));
		constraintViolations = validator.validate( order);
		assertFalse( constraintViolations.isEmpty() );
		assertEquals( "Total price must be positive", constraintViolations.iterator().next().getMessage());
	}	
	
	
	@ParameterizedTest
	@ValueSource(
		strings={
			"0.01","1","9999999.99"	
		}
	)
	void testSetTotalPriceValids(String price) {
		order.setTotalPrice(new BigDecimal(price));
		constraintViolations = validator.validate( order);
		assertTrue( constraintViolations.isEmpty() );
	}	
	
	
	@ParameterizedTest
	@ValueSource(
		strings={
				"99999990","0.990","99999990.990"			
		}
	)
	void testSetTotalPriceInvalids(String price) {
		order.setTotalPrice(new BigDecimal(price));
		constraintViolations = validator.validate( order);
		assertFalse( constraintViolations.isEmpty() );
		assertEquals( "Price has a limit of 7+2 digits", constraintViolations.iterator().next().getMessage());
	}	


	@Test
	void testCalculatetotalPrice() {
		int days= 1;
		BigDecimal price= new BigDecimal(1000);
		Room room = order.getReservedRoom();
		room.setRoomPrice(price);
		LocalDate fromDate = LocalDate.now();
		LocalDate toDate   = LocalDate.now().plusDays(days);
		order.setFromDate(fromDate);
		order.setToDate(toDate);
		order.CalculatetotalPrice();
		assertEquals( price.multiply(new BigDecimal(days)), order.getTotalPrice());
	}


	private Order beforeEachCreateValidOrder() {
		order=new Order();
		order.setOrderUUID(UUID.fromString("7b575239-b6e9-4f23-929f-bf04f1378f2d"));
		order.setOrderCreatedDateTime(LocalDateTime.now());
		order.setReservingCustomer(new Customer());
		order.setReservedHotel(new Hotel());
		order.setReservedRoom(new Room());
		order.setFromDate(LocalDate.now());
		order.setToDate(LocalDate.now().plusDays(10));
		order.setTotalPrice(new BigDecimal(1000));
		return order;
	}
}
