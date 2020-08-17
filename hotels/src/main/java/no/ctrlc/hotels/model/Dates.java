package no.ctrlc.hotels.model;

import java.time.LocalDate;

import javax.validation.GroupSequence;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;

import org.springframework.context.annotation.Scope;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import no.ctrlc.hotels.util.Utilities;

@Component("dates")
@Scope("prototype")
public class Dates implements Comparable<Dates> {

    @NotNull(message = "FromDate must not be null", groups = FirstStep.class)
    @FutureOrPresent(message = "FromDate must be future or present", groups = FirstStep.class)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fromDate;

    @NotNull(message = "ToDate must not be null", groups = FirstStep.class)
    @Future(message = "ToDate must be future", groups = FirstStep.class)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate toDate;

    private Boolean fromDateIsBeforeToDate;

    private Utilities utilities = new Utilities();

    public Dates() {
    }

    public void setFromDate( LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setToDate( LocalDate toDate) {
        this.toDate = toDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    @AssertTrue(message = "FromDate must be before ToDate", groups = SecondStep.class)
    public boolean isFromDateBeforeToDate() {
        try {
            fromDateIsBeforeToDate = utilities.ascendingDates(fromDate, toDate);
        } catch (IllegalArgumentException e) {
            fromDateIsBeforeToDate = false;
        }
        return fromDateIsBeforeToDate;
    }

    // validation group 1
    public interface FirstStep {
    }

    // validation group 2
    public interface SecondStep {
    }

    // Validating Total.class group will validate FirstStep group, and only if
    // FirstStep group not fails, it will validate the SecondStep group.
    @GroupSequence({ FirstStep.class, SecondStep.class })
    public interface Total {
    }

    @Override
    public int compareTo( Dates otherDates) throws NullPointerException, ClassCastException {
        // check for Null
        if (otherDates == null || otherDates.getFromDate() == null || otherDates.getToDate() == null || fromDate == null
                || toDate == null) {
            throw new NullPointerException();
        } else if (otherDates instanceof Dates == false) {
            throw new ClassCastException();
        }
        // perform comparisons
        int firstComparison = fromDate.compareTo(otherDates.getFromDate());
        int secondComparison = toDate.compareTo(otherDates.getToDate());

        if (firstComparison != 0) {
            return firstComparison;
        } else if (secondComparison != 0) {
            return secondComparison;
        } else {
            return 0;
        }
    }
}
