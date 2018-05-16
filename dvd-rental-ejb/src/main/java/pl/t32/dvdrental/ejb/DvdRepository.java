package pl.t32.dvdrental.ejb;

import pl.t32.dvdrental.model.Dvd;

import javax.annotation.sql.DataSourceDefinition;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@DataSourceDefinition(
        name="java:global/DemoDataSource",
        className="org.apache.derby.jdbc.ClientDataSource",
        minPoolSize = 1,
        initialPoolSize = 1,
        portNumber = 1527,
        user = "app",
        password = "app",
        databaseName = "dvd-rental",
        properties = {"connectionAttributes=;create=true"}
)
@Stateless
public class DvdRepository implements DvdRepositoryDao {

    @PersistenceContext(unitName = "pl.t32.dvd-rental-ejb_ejb_1.0PU")
    private EntityManager em;

    public void save(Dvd dvd) {
        em.persist(dvd);
    }

    public Dvd update(Dvd dvd) {
        return em.merge(dvd);
    }

    public void remove(Long id) {
        em.remove(em.getReference(Dvd.class, id));
    }

    public List<Dvd> findAll() {
        TypedQuery<Dvd> query = em.createNamedQuery("Dvd.findAll", Dvd.class);
        return query.getResultList();
    }
}
