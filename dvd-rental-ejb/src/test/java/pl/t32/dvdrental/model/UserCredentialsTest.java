package pl.t32.dvdrental.model;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UserCredentialsTest {

    @Test
    public void isAdminShouldReturnTrueForAdmin() {
        UserCredentials user = new UserCredentials();
        user.setUsername("kamil");
        user.setPassword("password");
        user.setEmail("kamil@t32.pl");

        UserGroup group = new UserGroup();
        group.setGroupname("ADMIN");

        UserGroup group1 = new UserGroup();
        group1.setGroupname("USER");

        user.add(group);
        user.add(group1);

        assertTrue(user.isAdmin());
    }

    @Test
    public void isAdminShouldReturnFalseForNotAdmin() {
        UserCredentials user = new UserCredentials();
        user.setUsername("kamil");
        user.setPassword("password");
        user.setEmail("kamil@t32.pl");

        UserGroup group = new UserGroup();
        group.setGroupname("USER");
        user.add(group);

        assertFalse(user.isAdmin());
    }

    @Test
    public void isGroupAddedToUser() {
        UserCredentials user = new UserCredentials();
        UserGroup group = new UserGroup();
        group.setGroupname("group name");
        user.add(group);
        List<UserGroup> groups = user.getUserGroups();
        assertTrue(groups.contains(group));
    }
}
