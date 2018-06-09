package pl.t32.dvdrental.ejb.dao;

import pl.t32.dvdrental.model.Dvd;
import pl.t32.dvdrental.model.DvdRental;
import pl.t32.dvdrental.model.UserCredentials;

import javax.ejb.Local;
import java.util.List;

@Local
public interface DvdRentalDao extends AbstractDao<DvdRental, Long> {

    List<DvdRental> findByUser(UserCredentials user);
    List<DvdRental> getPendingRentals(Dvd dvd);
    List<DvdRental> getExpiredRentals();
}
