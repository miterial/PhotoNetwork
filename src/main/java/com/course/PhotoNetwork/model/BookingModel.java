package com.course.PhotoNetwork.model;

import com.course.PhotoNetwork.model.types.BookingEnum;
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
    private UserModel master;

    @ManyToOne(optional = false)
    private UserModel customer;

    @ManyToOne
    private ServiceModel service;

    private Date bookingDate;

    private BookingEnum status;

    private boolean finishedByMaster;
    private boolean finishedByClient;
    private boolean deletedByMaster;
    private boolean deletedByClient;

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

    public BookingEnum getStatus() {
        return status;
    }

    public void setStatus(BookingEnum status) {
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isFinishedByMaster() {
        return finishedByMaster;
    }

    public void setFinishedByMaster(boolean finishedByMaster) {
        this.finishedByMaster = finishedByMaster;
    }

    public boolean isFinishedByClient() {
        return finishedByClient;
    }

    public void setFinishedByClient(boolean finishedByClient) {
        this.finishedByClient = finishedByClient;
    }

    public boolean isDeletedByMaster() {
        return deletedByMaster;
    }

    public void setDeletedByMaster(boolean deletedByMaster) {
        this.deletedByMaster = deletedByMaster;
    }

    public boolean isDeletedByClient() {
        return deletedByClient;
    }

    public void setDeletedByClient(boolean deletedByClient) {
        this.deletedByClient = deletedByClient;
    }
}
