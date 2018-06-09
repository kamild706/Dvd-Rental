package pl.t32.dvdrental.ejb.dao;


import pl.t32.dvdrental.model.UserCredentials;

import javax.ejb.Stateless;

@Stateless
public class UserCredentialsDaoImpl extends AbstractDaoImpl<UserCredentials, Long> implements UserCredentialsDao {

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
}
