package pl.t32.dvdrental.web.controller;

import org.primefaces.context.RequestContext;
import pl.t32.dvdrental.ejb.UserCredentialsDao;
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

    public static final Logger LOG = Logger.getLogger(UserManageBean.class.getName());

    @EJB
    private UserCredentialsDao dao;

    public List<UserCredentials> getUsers() {
        return dao.findAll();
    }

    public void onRemoveUser(UserCredentials uc) {
        dao.remove(uc.getId());
    }

    public void addAdminPrivileges(UserCredentials uc) {
        if (!uc.isAdmin()) {
            UserGroup group = new UserGroup();
            group.setGroupname("ADMIN");
            uc.add(group);

            dao.update(uc);
        }
    }

    public void revokeAdminPrivileges(UserCredentials uc) {
        UserGroup group = new UserGroup();
        group.setGroupname("ADMIN");
        uc.getUserGroups().remove(group);

        dao.update(uc);
    }
}
