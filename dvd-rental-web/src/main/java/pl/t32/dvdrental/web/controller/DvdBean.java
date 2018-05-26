package pl.t32.dvdrental.web.controller;

import org.primefaces.context.RequestContext;
import pl.t32.dvdrental.ejb.DvdDao;
import pl.t32.dvdrental.model.*;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class DvdBean implements Serializable {

    @EJB
    private DvdDao dao;

    private Dvd newDvd = new Dvd();

    public List<Dvd> getDvds() {
        return dao.findAll();
    }

    public void onDvdRemove(Dvd d) {
        newDvd = d;
    }

    public void onDvdRemoved() {
        dao.remove(newDvd.getId());
        RequestContext.getCurrentInstance().execute("PF('DvdRemoveDlg').hide()");
    }

    public void onDvdAdd() {
        newDvd = new Dvd();
    }

    public void onDvdEdit(Dvd dvd) {
        newDvd = dvd;
    }

    public void onDvdEdited() {
        dao.update(newDvd);
        RequestContext.getCurrentInstance().execute("PF('DvdEditDlg').hide()");
    }

    public void onDvdAdded() {
        dao.save(newDvd);
        RequestContext.getCurrentInstance().execute("PF('DvdDlg').hide()");
    }

    public Dvd getNewDvd() {
        return newDvd;
    }

    public void setNewDvd(Dvd newDvd) {
        this.newDvd = newDvd;
    }

    public boolean canBeRented(UserCredentials user) {
        if (newDvd.getState() == DvdState.RESERVED || newDvd.getState() == DvdState.RENTED_AND_RESERVED)
            return false;
        if (newDvd.getState() == DvdState.AVAILABLE)
            return true;
        for (DvdRental rental : newDvd.getRentals()) {
            if (rental.getState() == RentalState.RENTED && rental.getCustomer().equals(user))
                return false;
        }
        return true;
    }
}
