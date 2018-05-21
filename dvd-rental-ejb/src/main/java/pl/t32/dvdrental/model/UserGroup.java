package pl.t32.dvdrental.model;

import javax.persistence.*;

@Entity
public class UserGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String groupname;
    @ManyToOne
    private UserCredentials userCredentials;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public UserCredentials getUserCredentials() {
        return userCredentials;
    }

    public void setUserCredentials(UserCredentials userCredentials) {
        this.userCredentials = userCredentials;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof UserGroup) {
            UserGroup group = (UserGroup) obj;
            return groupname.equals(group.groupname);
        }
        return false;
    }

    @Override
    public String toString() {
        return "UserGroup{" +
                "id=" + id +
                ", groupname='" + groupname + '\'' +
                '}';
    }
}
