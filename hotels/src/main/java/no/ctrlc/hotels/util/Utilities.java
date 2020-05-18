package no.ctrlc.hotels.util;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import no.ctrlc.hotels.model.Dates;


/* Contains methods used in multiple classes */ 

@Component("utilities")
public class Utilities {

	
	/* check if fromDate is a date befor toDate
	 * as it would be problematic to check out of the hotel before you check in,
	 * and it would be problematic to calculate a price for the stay.
	 */
	public boolean ascendingDates(LocalDate fromDate, LocalDate toDate) {
		if (fromDate == null || toDate==null ) throw new IllegalArgumentException("Arguments can not be null");
		return  fromDate.isBefore(toDate);
	}

	/* (toDate-fromDate) x price per day */
	public BigDecimal calculateTotalPrice( Dates date, BigDecimal price) {
		LocalDate fromDate =  date.getFromDate();
		LocalDate toDate = date.getToDate();
		if (fromDate == null || toDate==null || price == null) throw new IllegalArgumentException("arguments can not be null");
		if ( ascendingDates(fromDate, toDate) == false) throw new IllegalArgumentException("fromDate must be before toDate");
		long days = ChronoUnit.DAYS.between(fromDate, toDate);
		return price.multiply(new BigDecimal(days));
	}
	

	/* Get field errors from bindingresult and return them as a single string */
	public String getFieldErrors(BindingResult bindingResult) {
		List<FieldError> fieldErrors = bindingResult.getFieldErrors();
		String errors="<b>An error occured:</b><br/>";		
	    for (FieldError error : fieldErrors ) {
	        errors += " "+error.getObjectName() + " - " + error.getDefaultMessage() +"<br/>";
	    }
	    return errors;
	}
}