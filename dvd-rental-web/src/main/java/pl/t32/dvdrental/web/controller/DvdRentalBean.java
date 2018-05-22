package pl.t32.dvdrental.web.controller;

import org.primefaces.context.RequestContext;
import pl.t32.dvdrental.ejb.DvdDao;
import pl.t32.dvdrental.ejb.DvdRentalDao;
import pl.t32.dvdrental.model.Dvd;
import pl.t32.dvdrental.model.DvdRental;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

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

    public DvdRental getNewRental() {
        return newRental;
    }

    public void setNewRental(DvdRental newRental) {
        this.newRental = newRental;
    }
}
