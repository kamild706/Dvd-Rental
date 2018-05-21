package pl.t32.dvdrental.ejb;


import pl.t32.dvdrental.model.UserCredentials;

import javax.ejb.Stateless;
import java.util.logging.Logger;

@Stateless
public class UserCredentialsDaoImpl extends AbstractDaoImpl<UserCredentials, Long> implements UserCredentialsDao {

    public static final Logger LOG = Logger.getLogger(UserCredentialsDaoImpl.class.getName());

    public UserCredentialsDaoImpl() {
        super(UserCredentials.class);
    }

    public UserCredentials findByUsernameAndPassword(String username, String password) {
        return em.createNamedQuery("UserCredentials.findByUsernameAndPassword", UserCredentials.class)
                .setParameter("un", username)
                .setParameter("pw", password)
                .getResultList().stream().findFirst().orElse(null);
    }

    @Override
    public boolean isUsernameAvailable(String username) {
        UserCredentials temp = em.createNamedQuery("UserCredentials.findByUsername", UserCredentials.class)
                .setParameter("un", username)
                .getResultList().stream().findFirst().orElse(null);
        return temp == null;
    }

    @Override
    public void save(UserCredentials userCredentials) {
        super.save(userCredentials);
        LOG.info("New User has been saved: " + userCredentials);
    }

    @Override
    public UserCredentials update(UserCredentials userCredentials) {
        UserCredentials user = super.update(userCredentials);
        LOG.info("User has been updated: " + user);
        return user;
    }

    @Override
    public void remove(Long aLong) {
        super.remove(aLong);
        LOG.info("User of ID " + aLong + " has been deleted");
    }
}
