package pl.t32.dvdrental.ejb;

import pl.t32.dvdrental.model.UserCredentials;

public interface UserCredentialsDao extends AbstractDao<UserCredentials, Long> {

    UserCredentials findByUsernameAndPassword(String username, String password);
    boolean isUsernameAvailable(String username);
}
