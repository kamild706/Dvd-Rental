package pl.t32.dvdrental.web.controller;

import org.primefaces.context.RequestContext;
import pl.t32.dvdrental.ejb.DvdDao;
import pl.t32.dvdrental.ejb.jpa.JpaDvdDao;
import pl.t32.dvdrental.model.Dvd;

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

    public void onRemoveDvd(Dvd d) {
        dao.remove(d.getId());
    }

    public void onDvdAdd() {
        newDvd = new Dvd();
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
}
