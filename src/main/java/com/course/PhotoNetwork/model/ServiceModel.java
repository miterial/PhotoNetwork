package com.course.PhotoNetwork.model;

import com.course.PhotoNetwork.model.types.ServiceEnum;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Date;
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

    @OneToOne(optional = false)
    @Cascade({org.hibernate.annotations.CascadeType.DETACH,org.hibernate.annotations.CascadeType.MERGE,org.hibernate.annotations.CascadeType.REFRESH,org.hibernate.annotations.CascadeType.PERSIST, org.hibernate.annotations.CascadeType.SAVE_UPDATE})
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
