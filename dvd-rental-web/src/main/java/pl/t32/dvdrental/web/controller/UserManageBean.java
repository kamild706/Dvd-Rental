package pl.t32.dvdrental.web.controller;

import pl.t32.dvdrental.ejb.dao.UserCredentialsDao;
import pl.t32.dvdrental.model.UserCredentials;
import pl.t32.dvdrental.model.UserGroup;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

@Named
@ViewScoped
public class UserManageBean implements Serializable {

    public static final Logger logger = Logger.getLogger(UserManageBean.class.getName());

    @EJB
    private UserCredentialsDao dao;

    public List<UserCredentials> getUsers() {
        return dao.findAll();
    }

    public void onRemoveUser(UserCredentials uc) {
        dao.remove(uc.getId());
    }

    public void handleAddAdminPrivileges(UserCredentials uc) {
        addAdminPrivileges(uc);
        dao.update(uc);
    }

    public void addAdminPrivileges(UserCredentials uc) {
        if (!uc.isAdmin()) {
            UserGroup group = new UserGroup();
            group.setGroupname("ADMIN");
            uc.add(group);

            logger.fine("User " + uc.getUsername() + " added to ADMIN group");
        }
    }

    public void handleRevokeAdminPrivileges(UserCredentials uc) {
        revokeAdminPrivileges(uc);
        dao.update(uc);
    }

    public void revokeAdminPrivileges(UserCredentials uc) {
        UserGroup group = new UserGroup();
        group.setGroupname("ADMIN");
        uc.remove(group);

        logger.fine("User " + uc.getUsername() + " removed from ADMIN group");
    }
}
