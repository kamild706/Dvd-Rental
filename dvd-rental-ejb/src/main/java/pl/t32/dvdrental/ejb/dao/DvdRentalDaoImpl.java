package pl.t32.dvdrental.ejb.dao;

import pl.t32.dvdrental.model.Dvd;
import pl.t32.dvdrental.model.DvdRental;
import pl.t32.dvdrental.model.UserCredentials;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.List;

@Stateless
public class DvdRentalDaoImpl extends AbstractDaoImpl<DvdRental, Long> implements DvdRentalDao {
    public DvdRentalDaoImpl() {
        super(DvdRental.class);
    }

    @Override
    public List<DvdRental> findByUser(UserCredentials user) {
        TypedQuery<DvdRental> query = em.createNamedQuery("DvdRental.findByUser", DvdRental.class);
        query.setParameter("customer", user);
        return query.getResultList();
    }

    @Override
    public List<DvdRental> getPendingRentals(Dvd dvd) {
        TypedQuery<DvdRental> query = em.createNamedQuery("DvdRental.getPendingRentals", DvdRental.class);
        query.setParameter("dvd", dvd);
        query.setParameter("now", LocalDateTime.now());
        return query.getResultList();
    }

    @Override
    public List<DvdRental> getExpiredRentals() {
        TypedQuery<DvdRental> query = em.createNamedQuery("DvdRental.getExpiredRentals", DvdRental.class);
        query.setParameter("date", LocalDateTime.now().minusMinutes(3));
        return query.getResultList();
    }
}
