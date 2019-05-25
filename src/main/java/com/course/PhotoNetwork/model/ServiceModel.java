package com.course.PhotoNetwork.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "service")
public class ServiceModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private double price;

    @ManyToOne
    private UserModel master;

    @OneToMany
    private List<BookingModel> booking;

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

    public UserModel getMaster() {
        return master;
    }

    public void setMaster(UserModel master) {
        this.master = master;
    }

    public List<BookingModel> getBooking() {
        return booking;
    }

    public void setBooking(List<BookingModel> booking) {
        this.booking = booking;
    }
}
