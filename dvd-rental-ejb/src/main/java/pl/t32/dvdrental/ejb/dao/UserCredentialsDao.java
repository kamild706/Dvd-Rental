package pl.t32.dvdrental.ejb.dao;

import pl.t32.dvdrental.model.UserCredentials;

import javax.ejb.Local;

@Local
public interface UserCredentialsDao extends AbstractDao<UserCredentials, Long> {

    UserCredentials findByUsernameAndPassword(String username, String password);
    boolean isUsernameAvailable(String username);
}
