/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.t32.dvdrental.ejb;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public abstract class AbstractDaoImpl<T, ID> {
    @PersistenceContext(unitName = "pl.t32.dvd-rental-ejb_ejb_1.0PU")
    protected EntityManager em;
    private Class<T> entityClass;

    public AbstractDaoImpl() {
    }

    public AbstractDaoImpl(Class<T> entityClass) {
        this.entityClass = entityClass;
    }


    public void save(T t) {
        em.persist(t);
    }

    public T update(T t) {
        return em.merge(t);
    }

    public void remove(ID id) {
        em.remove(em.getReference(entityClass, id));
    }

    public T findById(ID id) {
        return em.find(entityClass, id);
    }

    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return em.createQuery(cq).getResultList();
    }
}
