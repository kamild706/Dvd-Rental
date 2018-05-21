package pl.t32.dvdrental.web.controller;

import org.junit.Before;
import org.junit.Test;
import pl.t32.dvdrental.model.UserCredentials;
import pl.t32.dvdrental.model.UserGroup;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UserManageBeanTest {

    private UserManageBean bean;
    private UserCredentials user;

    @Before
    public void setup() {
        bean = new UserManageBean();
        user = new UserCredentials();
    }

    @Test
    public void addAdminPrivilegesShouldAddAdminGroup() {
        bean.addAdminPrivileges(user);
        assertTrue(user.isAdmin());
    }

    @Test
    public void revokeAdminPrivilegesShouldRemoveAdminGroup() {
        UserGroup group = new UserGroup();
        group.setGroupname("ADMIN");
        user.add(group);
        bean.revokeAdminPrivileges(user);
        assertFalse(user.isAdmin());
    }
}
