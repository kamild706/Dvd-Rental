package pl.t32.dvdrental.web.controller;


import org.primefaces.context.RequestContext;
import pl.t32.dvdrental.ejb.DvdDao;
import pl.t32.dvdrental.ejb.DvdRentalDao;
import pl.t32.dvdrental.ejb.MailSender;
import pl.t32.dvdrental.model.Dvd;
import pl.t32.dvdrental.model.DvdRental;
import pl.t32.dvdrental.model.RentalState;
import pl.t32.dvdrental.model.UserCredentials;
import pl.t32.dvdrental.web.util.JSF;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Named
@ViewScoped
public class DvdRentalBean implements Serializable {

    final static Logger logger = Logger.getLogger(DvdRentalBean.class.getName());

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

    public boolean areDatesValid() {
        if (newRental.getRentedSince().isAfter(newRental.getRentedTo()))
            return false;

        if (LocalDateTime.now().isAfter(newRental.getRentedSince()))
            return false;

        return true;
    }

    public boolean isRentalCollision() {
        Dvd dvd = newRental.getDvd();
        return dvd.getRentals().stream().anyMatch(r -> r.getState() != RentalState.RETURNED &&
                (r.dateInRentalPeriod(newRental.getRentedTo()) || r.dateInRentalPeriod(newRental.getRentedSince())
                        || newRental.dateInRentalPeriod(r.getRentedSince())));
    }

    public void onRentalAdded() {
        if (!newRental.getDvd().canUserRent(userBean.getUser())) {
            JSF.addErrorMessage("You already rented this Dvd");
            return;
        }
        if (!areDatesValid()) {
            JSF.addErrorMessage("Rented since must be earlier than rented to, and later than now");
            return;
        }
        if (isRentalCollision()) {
            JSF.addErrorMessage("Given dates collide with existing reservations");
            return;
        }

        newRental.setCustomer(userBean.getUser());
        Dvd dvd = newRental.getDvd();
        dvd.addRental(newRental);
        dao.save(newRental);
        dvdDao.update(dvd);

        RequestContext.getCurrentInstance().execute("PF('RentalDlg').hide()");
    }

    public void onRentalRemove(DvdRental rental) {
        newRental = rental;
    }

    public void onRentalRemoved() {
        if (newRental.getState() == RentalState.RENTED) {
            JSF.addErrorMessage("You cannot delete issued order.");
            return;
        }
        dao.remove(newRental.getId());
        Dvd dvd = newRental.getDvd();
        dvd.removeRental(newRental);
        dvdDao.update(dvd);

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
        dvdDao.update(rental.getDvd());
        logger.fine("Dvd " + rental.getDvd() + " has been issued");
    }

    public void returnDvd(DvdRental rental) {
        rental.setState(RentalState.RETURNED);
        dao.update(rental);
        dvdDao.update(rental.getDvd());
        logger.fine("Dvd" + rental.getDvd() + " has been returned");
    }

    public List<DvdRental> getPendingRentals() {
        if (newRental == null) return new ArrayList<>();
        return dao.getPendingRentals(newRental.getDvd());
    }

    public boolean canBeIssued(DvdRental rental) {
        Dvd dvd = rental.getDvd();
        boolean isDvdRented = dvd.getRentals().stream().anyMatch(r -> r.getState() == RentalState.RENTED);
        if (isDvdRented)
            return false;

        return rental.dateInRentalPeriod(LocalDateTime.now()) && rental.getState() == RentalState.RESERVED;
    }

    public boolean canBeReturned(DvdRental rental) {
        return rental.getState() == RentalState.RENTED;
    }
}
