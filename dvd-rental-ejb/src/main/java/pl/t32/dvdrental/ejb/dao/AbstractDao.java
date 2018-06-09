package pl.t32.dvdrental.ejb.dao;

import javax.ejb.Local;
import java.util.List;

@Local
public interface AbstractDao<T, K> {
    void save(T t);
    void remove (K id);
    T update (T t);
    List<T> findAll();
}
