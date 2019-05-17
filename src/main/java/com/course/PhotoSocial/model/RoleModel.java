package com.course.PhotoSocial.model;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "role")
public class RoleModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false, unique = true)
    private String rolename;

    @ManyToMany(mappedBy = "roles")
    private List<UserModel> users;

    public RoleModel() {
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public void setUsers(List<UserModel> users) {
        this.users = users;
    }

    public Long getId() {
        return id;
    }

    public String getRolename() {
        return rolename;
    }

    public List<UserModel> getUsers() {
        return users;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.id);
        hash = 67 * hash + Objects.hashCode(this.rolename);
        hash = 67 * hash + Objects.hashCode(this.users);
        return hash;
    }
}
