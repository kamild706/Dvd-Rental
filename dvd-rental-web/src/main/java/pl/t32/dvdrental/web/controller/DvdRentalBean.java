package pl.t32.dvdrental.web.controller;

import org.primefaces.context.RequestContext;
import pl.t32.dvdrental.ejb.DvdDao;
import pl.t32.dvdrental.ejb.DvdRentalDao;
import pl.t32.dvdrental.model.Dvd;
import pl.t32.dvdrental.model.DvdRental;
import pl.t32.dvdrental.model.RentalState;
import pl.t32.dvdrental.model.UserCredentials;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class DvdRentalBean implements Serializable {

    @EJB
    private DvdRentalDao dao;

    @EJB
    private DvdDao dvdDao;

    @Inject
    private UserBean userBean;

    private DvdRental newRental;

    public void onRentalAdd(Dvd dvd) {
        newRental = new DvdRental();
        newRental.setDvd(dvd);
    }

    public void onRentalAdded() {
        newRental.setCustomer(userBean.getUser());
        newRental.getDvd().addRental(newRental);
        dao.save(newRental);
        dvdDao.update(newRental.getDvd());
        RequestContext.getCurrentInstance().execute("PF('RentalDlg').hide()");
    }

    public void onRentalRemove(DvdRental rental) {
        newRental = rental;
    }

    public void onRentalRemoved() {
        dao.remove(newRental.getId());
        newRental.getDvd().removeRental(newRental);
        RequestContext.getCurrentInstance().execute("PF('RentalRemoveDlg').hide()");
    }

    public List<DvdRental> getUserRentals(UserCredentials user) {
        return dao.findByUser(user);
    }

    public DvdRental getNewRental() {
        return newRental;
    }

    public void setNewRental(DvdRental newRental) {
        this.newRental = newRental;
    }

    public List<DvdRental> getRentals() {
        return dao.findAll();
    }

    public void issueDvd(DvdRental rental) {
        rental.setState(RentalState.RENTED);
        dao.update(rental);
    }

    public void returnDvd(DvdRental rental) {
        rental.setState(RentalState.RETURNED);
        dao.update(rental);
    }
}
