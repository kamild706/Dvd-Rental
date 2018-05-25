package pl.t32.dvdrental.ejb;

import pl.t32.dvdrental.model.DvdRental;
import pl.t32.dvdrental.model.UserCredentials;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
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
}
