package com.CommunityCompaintManagement.CommunityCompaintManagement.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name="announcments")
public class Announcment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name="admin_id")
    private Admin admin;

    @Column(nullable = false)
    private LocalDate postdate;

    @Column(nullable = false)
    private LocalTime posttime;

    public Announcment(int id, String title, String description, Admin admin) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.admin = admin;
        this.postdate = LocalDate.now();
        this.posttime = LocalTime.now();
    }

    public Announcment(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public LocalDate getPostdate() {
        return postdate;
    }

    public void setPostdate(LocalDate postdate) {
        this.postdate = postdate;
    }

    public LocalTime getPosttime() {
        return posttime;
    }

    public void setPosttime(LocalTime posttime) {
        this.posttime = posttime;
    }

    @PrePersist
    public void setAnnouncementDateTime(){
        this.postdate=LocalDate.now();
        this.posttime=LocalTime.now();
    }
}
