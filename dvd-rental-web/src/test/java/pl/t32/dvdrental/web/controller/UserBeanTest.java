package pl.t32.dvdrental.web.controller;

import org.junit.Before;
import org.junit.Test;
import pl.t32.dvdrental.model.UserCredentials;
import pl.t32.dvdrental.model.UserGroup;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UserBeanTest {

    private UserBean bean;
    private UserCredentials user;

    @Before
    public void setup() {
        bean = new UserBean();
        user = new UserCredentials();
    }

    @Test
    public void isAdminShouldReturnTrueForAdmin() {
        UserGroup group = new UserGroup();
        group.setGroupname("ADMIN");

        UserGroup group2 = new UserGroup();
        group2.setGroupname("USER");

        user.add(group);
        user.add(group2);

        bean.setUser(user);
        assertTrue(bean.isAdmin());
    }

    @Test
    public void isAdminShouldReturnFalseForNotAdmin() {
        UserGroup group = new UserGroup();
        group.setGroupname("USER");

        user.add(group);
        bean.setUser(user);
        assertFalse(bean.isAdmin());
    }

    @Test
    public void isLoggedShouldReturnTrueForLoggedUser() {
        bean.setUser(user);
        assertTrue(bean.isLogged());
    }

    @Test
    public void isLoggedShouldReturnFalseForNotLoggedUser() {
        assertFalse(bean.isLogged());
    }
}
