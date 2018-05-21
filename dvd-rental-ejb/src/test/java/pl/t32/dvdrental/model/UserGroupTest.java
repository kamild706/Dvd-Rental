package pl.t32.dvdrental.model;

import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class UserGroupTest {

    @Test
    public void isAdminShouldBeTrueForAdmin() {
        UserCredentials user = new UserCredentials();
        user.setUsername("kamil");
        user.setPassword("password");
        user.setEmail("kamil@t32.pl");

        UserGroup group = new UserGroup();
        group.setGroupname("ADMIN");
        user.add(group);

        assertTrue(user.isAdmin());
    }
}
