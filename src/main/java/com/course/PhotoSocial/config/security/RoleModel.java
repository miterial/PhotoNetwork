package com.course.PhotoSocial.config.security;

import com.course.PhotoSocial.model.UserModel;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "role")
public class RoleModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long role_id;

    @Column(nullable = false, unique = true)
    private String role_name;

    @ManyToMany(mappedBy = "roles")
    private List<UserModel> users;

    public RoleModel() {
    }

    public void setRole_id(long role_id) {
        this.role_id = role_id;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    public void setUsers(List<UserModel> users) {
        this.users = users;
    }

    public Long getRole_id() {
        return role_id;
    }

    public String getRole_name() {
        return role_name;
    }

    public List<UserModel> getUsers() {
        return users;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.role_id);
        hash = 67 * hash + Objects.hashCode(this.role_name);
        hash = 67 * hash + Objects.hashCode(this.users);
        return hash;
    }
}
