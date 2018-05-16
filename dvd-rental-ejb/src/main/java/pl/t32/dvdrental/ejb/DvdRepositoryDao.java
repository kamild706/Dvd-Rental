package pl.t32.dvdrental.ejb;

import pl.t32.dvdrental.model.Dvd;

import java.util.List;

public interface DvdRepositoryDao {
    void save(Dvd dvd);
    void remove (Long id);
    Dvd update (Dvd dvd);
    List<Dvd> findAll();
}
