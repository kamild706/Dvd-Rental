package pl.t32.dvdrental.ejb;

import pl.t32.dvdrental.model.Dvd;

import javax.ejb.Local;

@Local
public interface DvdDao extends AbstractDao<Dvd, Long> {

}
