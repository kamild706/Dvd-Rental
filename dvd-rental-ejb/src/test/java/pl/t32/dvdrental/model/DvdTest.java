package pl.t32.dvdrental.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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

    @Test
    public void canUserRentShouldReturnTrueForUserWhoDoesntAlreadyRentedNorReservedThisDvd() {
        UserCredentials user = new UserCredentials();

        assertTrue(dvd.canUserRent(user));
    }

    @Test
    public void canUserRentShouldReturnFalseForUserWhoAlreadyRentedThisDvd() {
        UserCredentials user = new UserCredentials();
        user.setEmail("email");
        user.setUsername("username");
        DvdRental rental = new DvdRental();
        rental.setState(RentalState.RENTED);
        rental.setDvd(dvd);
        rental.setCustomer(user);
        dvd.addRental(rental);

        assertFalse(dvd.canUserRent(user));
    }

    @Test
    public void canUserRentShouldReturnFalseForUserWhoAlreadyReservedThisDvd() {
        UserCredentials user = new UserCredentials();
        user.setEmail("email");
        user.setUsername("username");
        DvdRental rental = new DvdRental();
        rental.setState(RentalState.RESERVED);
        rental.setDvd(dvd);
        rental.setCustomer(user);
        dvd.addRental(rental);

        assertFalse(dvd.canUserRent(user));
    }
}
