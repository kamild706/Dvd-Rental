/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.t32.dvdrental.ejb;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.TimeZone;
import java.util.logging.Logger;

public abstract class AbstractDaoImpl<T, ID> {
    @PersistenceContext(unitName = "pl.t32.dvd-rental-ejb_ejb_1.0PU")
    protected EntityManager em;
    private Class<T> entityClass;
    private static final Logger logger = Logger.getLogger(AbstractDaoImpl.class.getName());

    static {
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Warsaw"));
    }

    public AbstractDaoImpl() {
    }

    public AbstractDaoImpl(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public void save(T t) {
        em.persist(t);
        logger.fine("New " + t.getClass().getName() + " has been created: " + t);
    }

    public T update(T t) {
        T tmp = em.merge(t);
        logger.fine(t.getClass().getName() + " has been updated: " + t);
        return tmp;
    }

    public void remove(ID id) {
        T tmp = em.getReference(entityClass, id);
        em.remove(tmp);
        logger.fine(tmp.getClass().getName() + " of ID: " + id + " has been deleted");
    }

    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return em.createQuery(cq).getResultList();
    }
}
