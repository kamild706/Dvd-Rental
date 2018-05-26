package pl.t32.dvdrental.web.controller;

import org.junit.Test;
import pl.t32.dvdrental.model.DvdRental;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.Assert.assertFalse;

public class DvdRentalBeanTest {

    @Test
    public void isRentalValidShouldReturnFalseForPastRentedSinceDate() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime past = now.minusMinutes(1);
        Date rentedSince = Date.from(past.atZone(ZoneId.systemDefault()).toInstant());

        DvdRental rental = new DvdRental();
        rental.setRentedSince(rentedSince);
        rental.setRentedTo(new Date());

        DvdRentalBean bean = new DvdRentalBean();
        bean.setNewRental(rental);
        assertFalse(bean.isRentalValid());
    }

    @Test
    public void isRentalValidShouldReturnFalseForRentedToBeforeRentedSince() {
        LocalDateTime d1 = LocalDateTime.now().plusMinutes(1);
        LocalDateTime d2 = d1.plusMinutes(1);

        Date rentedSince = Date.from(d2.atZone(ZoneId.systemDefault()).toInstant());
        Date rentedTo = Date.from(d1.atZone(ZoneId.systemDefault()).toInstant());

        DvdRental rental = new DvdRental();
        rental.setRentedTo(rentedTo);
        rental.setRentedSince(rentedSince);

        DvdRentalBean bean = new DvdRentalBean();
        bean.setNewRental(rental);
        assertFalse(bean.isRentalValid());
    }
}
