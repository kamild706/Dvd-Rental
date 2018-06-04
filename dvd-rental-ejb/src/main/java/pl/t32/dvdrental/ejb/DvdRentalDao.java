package pl.t32.dvdrental.ejb;

import pl.t32.dvdrental.model.Dvd;
import pl.t32.dvdrental.model.DvdRental;
import pl.t32.dvdrental.model.UserCredentials;

import java.util.List;

public interface DvdRentalDao extends AbstractDao<DvdRental, Long> {

    List<DvdRental> findByUser(UserCredentials user);
    List<DvdRental> getPendingRentals(Dvd dvd);
}
