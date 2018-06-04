package pl.t32.dvdrental.web.controller;

import org.junit.Before;
import org.junit.Test;
import pl.t32.dvdrental.model.Dvd;
import pl.t32.dvdrental.model.DvdRental;

import java.time.LocalDateTime;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@SuppressWarnings("Duplicates")
public class DvdRentalBeanTest {

    Dvd dvd;
    DvdRentalBean bean;
    DvdRental rental1;
    DvdRental rental2;

    @Before
    public void setUp() throws Exception {
        rental1 = new DvdRental();
        rental2 = new DvdRental();
        bean = new DvdRentalBean();
        dvd = new Dvd();
    }

    @Test
    public void areDatesValidShouldReturnFalseForRentedSinceFromPast() {
        LocalDateTime rentedSince = LocalDateTime.now().minusSeconds(1);
        LocalDateTime rentedTo = LocalDateTime.now().plusSeconds(100);

        rental1.setRentedSince(rentedSince);
        rental1.setRentedTo(rentedTo);

        bean.setNewRental(rental1);
        assertFalse(bean.areDatesValid());
    }

    @Test
    public void areDatesValidShouldReturnFalseForRentedToBeforeRentedSince() {
        LocalDateTime rentedSince = LocalDateTime.now().plusHours(1);
        LocalDateTime rentedTo = rentedSince.minusMinutes(1);

        rental1.setRentedTo(rentedTo);
        rental1.setRentedSince(rentedSince);

        bean.setNewRental(rental1);
        assertFalse(bean.areDatesValid());
    }

    @Test
    public void isRentalCollisionShouldReturnTrueWhenTwoRentalsCollide1() {
        /*
          When rental2 starts during rental1
         */
        LocalDateTime rentedSince = LocalDateTime.now();
        LocalDateTime rentedTo = rentedSince.plusHours(5);

        rental1.setRentedSince(rentedSince);
        rental1.setRentedTo(rentedTo);

        rentedSince = rentedSince.plusHours(2);
        rentedTo = rentedSince.plusHours(5);

        rental2.setRentedSince(rentedSince);
        rental2.setRentedTo(rentedTo);

        dvd.addRental(rental1);
        rental2.setDvd(dvd);
        bean.setNewRental(rental2);

        assertTrue(bean.isRentalCollision());
    }

    @Test
    public void isRentalCollisionShouldReturnTrueWhenTwoRentalsCollide2() {
        /*
            When rental2 ends during rental1
         */
        LocalDateTime rentedSince = LocalDateTime.now().plusDays(1);
        LocalDateTime rentedTo = rentedSince.plusHours(10);

        rental1.setRentedSince(rentedSince);
        rental1.setRentedTo(rentedTo);

        rentedSince = rentedSince.minusHours(2);
        rentedTo = rentedSince.plusHours(5);

        rental2.setRentedSince(rentedSince);
        rental2.setRentedTo(rentedTo);

        dvd.addRental(rental1);

        rental2.setDvd(dvd);
        bean.setNewRental(rental2);

        assertTrue(bean.isRentalCollision());
    }

    @Test
    public void isRentalCollisionShouldReturnTrueWhenTwoRentalsCollide3() {
        /*
            When rental2 starts before rental1 and finishes after rental1
         */
        LocalDateTime rentedSince = LocalDateTime.now().plusDays(1);
        LocalDateTime rentedTo = rentedSince.plusHours(10);

        rental1.setRentedSince(rentedSince);
        rental1.setRentedTo(rentedTo);

        rentedSince = rentedSince.minusHours(2);
        rentedTo = rentedTo.plusHours(5);

        rental2.setRentedSince(rentedSince);
        rental2.setRentedTo(rentedTo);

        dvd.addRental(rental1);

        rental2.setDvd(dvd);
        bean.setNewRental(rental2);

        assertTrue(bean.isRentalCollision());
    }
}
