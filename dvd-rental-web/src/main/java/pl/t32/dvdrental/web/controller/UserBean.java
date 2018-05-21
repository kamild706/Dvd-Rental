package pl.t32.dvdrental.web.controller;


import pl.t32.dvdrental.model.UserCredentials;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class UserBean implements Serializable {

    private UserCredentials user;

    public boolean isLogged() {
        return user != null;
    }

    public UserCredentials getUser() {
        return user;
    }

    public void setUser(UserCredentials user) {
        this.user = user;
    }

    public boolean isAdmin() {
        return user != null && user.isAdmin();
    }
}
