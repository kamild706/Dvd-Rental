package pl.t32.dvdrental.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DvdTest {

    private Dvd dvd;

    @Before
    public void setup() {
        dvd = new Dvd();
    }

    @Test
    public void getStateShouldReturnAvailableForDvdWithoutReservations() {
        assertEquals(dvd.getState(), DvdState.AVAILABLE);
    }

    @Test
    public void getStateShouldReturnAvailableForDvdWithReservations() {
        DvdRental rental = new DvdRental();
        rental.setState(RentalState.RESERVED);
        dvd.addRental(rental);

        assertEquals(dvd.getState(), DvdState.AVAILABLE);
    }

    @Test
    public void getStateShouldReturnRentedForDvdWithRentedRental() {
        DvdRental rental = new DvdRental();
        rental.setState(RentalState.RENTED);
        dvd.addRental(rental);

        assertEquals(dvd.getState(), DvdState.RENTED);
    }
}
