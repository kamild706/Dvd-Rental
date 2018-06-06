/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.t32.dvdrental.web.controller;


import pl.t32.dvdrental.ejb.UserCredentialsDao;
import pl.t32.dvdrental.model.UserCredentials;
import pl.t32.dvdrental.model.UserGroup;
import pl.t32.dvdrental.web.util.JSF;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Logger;

@Named
@ViewScoped
public class LoginBean implements Serializable {

    public static final Logger logger = Logger.getLogger(LoginBean.class.getName());

    @Inject
    private UserBean userBean;

    @EJB
    private UserCredentialsDao userCredentialsDao;

    private String username;
    private String password;
    private String email;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void login() throws IOException {
        UserCredentials user = userCredentialsDao.findByUsernameAndPassword(username, password);
        if (user != null) {
            userBean.setUser(user);
            logger.fine("User " + user.getUsername() + " has logged in");
            JSF.redirect("/");
        } else {
            logger.fine("Login unsuccessful, wrong credentials provided");
            JSF.addErrorMessage("Invalid credentials");
        }
    }

    public void signup() throws IOException {
        if (userCredentialsDao.isUsernameAvailable(username)) {
            UserCredentials user = new UserCredentials();
            user.setEmail(email);
            user.setPassword(password);
            user.setUsername(username);

            UserGroup group = new UserGroup();
            group.setGroupname("USERS");
            user.add(group);

            userCredentialsDao.save(user);
            login();
        } else {
            JSF.addErrorMessage("This username is taken");
        }
    }

    public void logout() throws IOException {
        JSF.invalidateSession();
        JSF.redirect("/");
    }
}
