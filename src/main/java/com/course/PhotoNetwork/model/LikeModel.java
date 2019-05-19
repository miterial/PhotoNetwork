package com.course.PhotoNetwork.model;

import javax.persistence.*;

@Entity
@Table(name = "likes")
public class LikeModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    private PhotoModel photo;

    @ManyToOne
    private UserModel user;

    public LikeModel() {
    }

    public LikeModel(PhotoModel photo, UserModel user) {
        this.photo = photo;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public PhotoModel getPhoto() {
        return photo;
    }

    public UserModel getUser() {
        return user;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setPhoto(PhotoModel photo) {
        this.photo = photo;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }


}
