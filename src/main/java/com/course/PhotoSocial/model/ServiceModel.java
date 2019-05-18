package com.course.PhotoSocial.model;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "service")
public class ServiceModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    /**
     * @see ServiceEnum
     */
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private double price;

    @ManyToOne(optional = false)
    @Cascade({org.hibernate.annotations.CascadeType.DETACH,org.hibernate.annotations.CascadeType.MERGE,org.hibernate.annotations.CascadeType.REFRESH,org.hibernate.annotations.CascadeType.PERSIST, org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    private UserModel user;

    public ServiceModel() { }

    public ServiceModel(long id, String name, double price, UserModel user) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }
}
