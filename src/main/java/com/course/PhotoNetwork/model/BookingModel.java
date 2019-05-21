package com.course.PhotoNetwork.model;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "booking")
public class BookingModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(optional = false)
    @Cascade({org.hibernate.annotations.CascadeType.DETACH,org.hibernate.annotations.CascadeType.MERGE,org.hibernate.annotations.CascadeType.REFRESH,org.hibernate.annotations.CascadeType.PERSIST, org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    private UserModel master;

    @ManyToOne(optional = false)
    @Cascade({org.hibernate.annotations.CascadeType.DETACH,org.hibernate.annotations.CascadeType.MERGE,org.hibernate.annotations.CascadeType.REFRESH,org.hibernate.annotations.CascadeType.PERSIST, org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    private UserModel customer;

    @ManyToOne
    private ServiceModel service;

    private Date bookingDate;

    private boolean confirmPaymentClient;
    private boolean confirmPaymentMaster;

    private boolean confirmEndingClient;
    private boolean confirmEndingMaster;

    public BookingModel() {
    }

    public UserModel getCustomer() {
        return customer;
    }

    public void setCustomer(UserModel customer) {
        this.customer = customer;
    }

    public UserModel getMaster() {
        return master;
    }

    public void setMaster(UserModel master) {
        this.master = master;
    }

    public ServiceModel getService() {
        return service;
    }

    public void setService(ServiceModel service) {
        this.service = service;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    public boolean isConfirmPaymentClient() {
        return confirmPaymentClient;
    }

    public void setConfirmPaymentClient(boolean confirmPaymentClient) {
        this.confirmPaymentClient = confirmPaymentClient;
    }

    public boolean isConfirmPaymentMaster() {
        return confirmPaymentMaster;
    }

    public void setConfirmPaymentMaster(boolean confirmPaymentMaster) {
        this.confirmPaymentMaster = confirmPaymentMaster;
    }

    public boolean isConfirmEndingClient() {
        return confirmEndingClient;
    }

    public void setConfirmEndingClient(boolean confirmEndingClient) {
        this.confirmEndingClient = confirmEndingClient;
    }

    public boolean isConfirmEndingMaster() {
        return confirmEndingMaster;
    }

    public void setConfirmEndingMaster(boolean confirmEndingMaster) {
        this.confirmEndingMaster = confirmEndingMaster;
    }
}
