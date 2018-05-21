package pl.t32.dvdrental.model;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@NamedQueries({
        @NamedQuery(name = "UserCredentials.findByUsernameAndPassword",
                query = "select uc from UserCredentials uc where uc.username=:un and uc.password=:pw"),
        @NamedQuery(name = "UserCredentials.findByUsername",
                query = "select uc from UserCredentials uc where uc.username=:un")
})
@Entity
public class UserCredentials {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String email;
    @OneToMany(mappedBy = "userCredentials", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<UserGroup> userGroups = new LinkedList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public void add(UserGroup userGroup) {
        userGroup.setUserCredentials(this);
        this.userGroups.add(userGroup);
    }

    public List<UserGroup> getUserGroups() {
        return userGroups;
    }

    public boolean isAdmin() {
        UserGroup group = new UserGroup();
        group.setGroupname("ADMIN");
        return userGroups.contains(group);
    }
}
