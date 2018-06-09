package pl.t32.dvdrental.model;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class DvdRentalTest {

    DvdRental rental;

    @Before
    public void setUp() {
        rental = new DvdRental();
    }

    @Test
    public void isDateInRentalPeriodShouldReturnTrueForDateBetweenBeginningAndEndOfPeriod() {
        rental.setRentedSince(LocalDateTime.now());
        rental.setRentedTo(LocalDateTime.now().plusHours(6));
        boolean result = rental.isDateInRentalPeriod(LocalDateTime.now().plusHours(3));
        assertTrue(result);
    }

    @Test
    public void isDateInRentalPeriodShouldReturnFalseForDateBeforeBeginningOfPeriod() {
        rental.setRentedSince(LocalDateTime.now());
        rental.setRentedTo(LocalDateTime.now().plusHours(6));
        boolean result = rental.isDateInRentalPeriod(LocalDateTime.now().minusHours(3));
        assertFalse(result);
    }

    @Test
    public void isDateInRentalPeriodShouldReturnFalseForDateAfterEndOfPeriod() {
        rental.setRentedSince(LocalDateTime.now());
        rental.setRentedTo(LocalDateTime.now().plusHours(6));
        boolean result = rental.isDateInRentalPeriod(LocalDateTime.now().plusHours(7));
        assertFalse(result);
    }
}
