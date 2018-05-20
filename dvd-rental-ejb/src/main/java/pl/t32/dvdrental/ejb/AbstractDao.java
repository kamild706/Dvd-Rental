package pl.t32.dvdrental.ejb;

import java.util.List;

public interface AbstractDao<T, K> {
    void save(T t);
    void remove (K id);
    T update (T t);
    List<T> findAll();
}
