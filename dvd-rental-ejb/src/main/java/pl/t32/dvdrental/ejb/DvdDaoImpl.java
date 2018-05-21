package pl.t32.dvdrental.ejb;

import pl.t32.dvdrental.model.Dvd;

import javax.annotation.sql.DataSourceDefinition;
import javax.ejb.Stateless;

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
    public DvdDaoImpl() {
        super(Dvd.class);
    }
}
