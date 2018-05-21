package pl.t32.dvdrental.ejb;

import pl.t32.dvdrental.model.Dvd;

import javax.annotation.sql.DataSourceDefinition;
import javax.ejb.Stateless;
import java.util.logging.Logger;

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
public class DvdDaoImpl extends AbstractDaoImpl<Dvd, Long> implements DvdDao {

    public static final Logger LOG = Logger.getLogger(DvdDaoImpl.class.getName());

    public DvdDaoImpl() {
        super(Dvd.class);
    }

    @Override
    public void save(Dvd dvd) {
        super.save(dvd);
        LOG.info("New DVD has been saved: " + dvd);
    }

    @Override
    public Dvd update(Dvd dvd) {
        Dvd tmp = super.update(dvd);
        LOG.info("DVD has been updated: " + tmp);
        return tmp;
    }

    @Override
    public void remove(Long aLong) {
        super.remove(aLong);
        LOG.info("DVD of ID " + aLong + " has been deleted");
    }
}
