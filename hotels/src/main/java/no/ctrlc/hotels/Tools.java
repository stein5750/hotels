package no.ctrlc.hotels;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import org.springframework.stereotype.Component;

/* Contains methods used in multiple classes */ 
@Component
public class Tools {

	boolean ascendingDates(LocalDate fromDate, LocalDate toDate) {
		if (fromDate == null || toDate==null ) throw new IllegalArgumentException("Arguments can not be null");
		return  fromDate.isBefore(toDate);
	}

	
	public BigDecimal CalculateTotalPrice( LocalDate fromDate, LocalDate toDate, BigDecimal price) {
		if (fromDate == null || toDate==null || price == null) throw new IllegalArgumentException("arguments can not be null");
		if ( ascendingDates(fromDate, toDate) == false) throw new IllegalArgumentException("fromDate must be before toDate");
		long days = ChronoUnit.DAYS.between(fromDate, toDate);
		return price.multiply(new BigDecimal(days));
	}
}
