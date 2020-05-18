package no.ctrlc.hotels.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Stream;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.executable.ExecutableValidator;

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


@RunWith(JUnitPlatform.class)
public class DatesTest {

	private Set<ConstraintViolation<Dates>> constraintViolations;
	private ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private Validator validator = factory.getValidator();
    private Dates dates;
	
    @BeforeEach
    private void init() {
       dates=beforeEachCreateValidDates();
    }	
 	
 	@Test
 	void testDates() {
 		constraintViolations = validator.validate( dates, Dates.Total.class);
 		assertTrue( constraintViolations.isEmpty() );
 	}	
	
	@ParameterizedTest
	@NullSource
	void testSetFromDateNull(LocalDate fromDate) {
		dates.setFromDate(fromDate);
		constraintViolations = validator.validate( dates, Dates.FirstStep.class );
		assertFalse( constraintViolations.isEmpty() );
		assertEquals( "FromDate must not be null", constraintViolations.iterator().next().getMessage());
	}
	
	
	@ParameterizedTest
	@ValueSource(
			longs={1,10,365,3650}
			)
	void testSetFromDatePast(long days) {
		LocalDate pastDate= LocalDate.now().minusDays(days);
		dates.setFromDate(pastDate);
		constraintViolations = validator.validate( dates, Dates.FirstStep.class );
		assertFalse( constraintViolations.isEmpty() );
		assertEquals( "FromDate must be future or present", constraintViolations.iterator().next().getMessage());
	}
	
	
	@ParameterizedTest
	@ValueSource(
			longs={0,1,10,365,3650}
			)
	void testSetFromDateFutureOrPresent(long days) {
		LocalDate date= LocalDate.now().plusDays(days);
		dates.setFromDate(date);
		constraintViolations = validator.validate( dates, Dates.FirstStep.class);
		assertTrue( constraintViolations.isEmpty() );
	}


	@Test
	void testGetFromDate() {
		LocalDate date=LocalDate.now();
		dates.setFromDate(date);
		LocalDate d = dates.getFromDate();
		assertEquals(date,d);
		assertSame(date,d);
	}
	

	@ParameterizedTest
	@NullSource
	void testSetToDateNull(LocalDate toDate) {
		dates.setToDate(toDate);
		constraintViolations = validator.validate( dates, Dates.FirstStep.class );
		assertFalse( constraintViolations.isEmpty() );
		assertEquals( "ToDate must not be null", constraintViolations.iterator().next().getMessage());		
	}
	
	
	@ParameterizedTest
	@ValueSource(
			longs={0,1,10,365,3650}
			)
	void testSetToDatePast(long days) {
		LocalDate pastDate= LocalDate.now().minusDays(days);
		dates.setToDate(pastDate);
		constraintViolations = validator.validate( dates, Dates.FirstStep.class );
		assertFalse( constraintViolations.isEmpty() );
		assertEquals( "ToDate must be future", constraintViolations.iterator().next().getMessage());
	}
	
	
	@ParameterizedTest
	@ValueSource(
			longs={1,10,365,3650}
			)
	void testSetToDateFutureOrPresent(long days) {
		LocalDate date= LocalDate.now().plusDays(days);
		dates.setToDate(date);
		constraintViolations = validator.validate( dates, Dates.FirstStep.class);
		assertTrue( constraintViolations.isEmpty() );
	}
	
	
	@Test
	void testGetToDate() {
		LocalDate date = LocalDate.now().plusDays(1);
		dates.setToDate(date);
		LocalDate d = dates.getToDate();
		assertEquals(date,d);
	}


	// testing return value from method
	@Test
	void testIsFromDateBeforeToDateTrue(){
		LocalDate fromDate = LocalDate.now().plusDays(1);
		LocalDate toDate   = LocalDate.now().plusDays(2);
		ReflectionTestUtils.setField( dates, "fromDate", fromDate );
		ReflectionTestUtils.setField( dates, "toDate", toDate );	
		constraintViolations = validator.validate( dates, Dates.SecondStep.class);
		assertTrue( constraintViolations.isEmpty() );
	}

	

	@Test
	void testIsFromDateBeforeToDateFalse1(){
		LocalDate fromDate = LocalDate.now().plusDays(1);
		LocalDate toDate   = LocalDate.now().plusDays(1);
		ReflectionTestUtils.setField( dates, "fromDate", fromDate );
		ReflectionTestUtils.setField( dates, "toDate", toDate );
		constraintViolations = validator.validate( dates, Dates.SecondStep.class);
		assertFalse( constraintViolations.isEmpty() );
		assertEquals( "FromDate must be before ToDate", constraintViolations.iterator().next().getMessage());
	}
	
	
	@Test
	void testIsFromDateBeforeToDateFalse2(){
		LocalDate fromDate = LocalDate.now().plusDays(2);
		LocalDate toDate   = LocalDate.now().plusDays(1);
		ReflectionTestUtils.setField( dates, "fromDate", fromDate );
		ReflectionTestUtils.setField( dates, "toDate", toDate );
		constraintViolations = validator.validate( dates, Dates.SecondStep.class);
		assertFalse( constraintViolations.isEmpty() );
		assertEquals( "FromDate must be before ToDate", constraintViolations.iterator().next().getMessage());		
	}
	
	
	// parameterized testing return value from method
	@ParameterizedTest
	@MethodSource("methodRecources")
	void testfromIsBeforeToDateMethodSourceTest(LocalDate fromDate, LocalDate toDate, Boolean expectedResult){

		ReflectionTestUtils.setField( dates, "fromDate", fromDate );
		ReflectionTestUtils.setField( dates, "toDate", toDate );
		
		// validate method 1/2
		assertEquals( expectedResult, dates.isFromDateBeforeToDate() );
		
	    // validate method 2/2
		Method method =null;
		try {
			method = Dates.class.getDeclaredMethod("isFromDateBeforeToDate");
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		boolean returnedValue=dates.isFromDateBeforeToDate();
		if (method != null) {
			ExecutableValidator executableValidator = validator.forExecutables();
			constraintViolations = executableValidator.validateReturnValue(dates,method, returnedValue);
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
		
	
	private Dates beforeEachCreateValidDates() {
		dates = new Dates();
		dates.setFromDate(LocalDate.now().plusDays(1));
		dates.setToDate(LocalDate.now().plusDays(2));
		return dates;
	}
	
}