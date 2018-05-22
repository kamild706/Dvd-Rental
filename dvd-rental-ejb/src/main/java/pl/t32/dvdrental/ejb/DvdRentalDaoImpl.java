package pl.t32.dvdrental.ejb;

import pl.t32.dvdrental.model.DvdRental;

import javax.ejb.Stateless;

@Stateless
public class DvdRentalDaoImpl extends AbstractDaoImpl<DvdRental, Long> implements DvdRentalDao {
    public DvdRentalDaoImpl() {
        super(DvdRental.class);
    }
}
