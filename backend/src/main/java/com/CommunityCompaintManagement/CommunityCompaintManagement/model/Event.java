package com.CommunityCompaintManagement.CommunityCompaintManagement.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private Date heldDate;
    @Column(nullable = false)
    private LocalDateTime postDateTime;

    @ManyToOne
    @JoinColumn(name="postedByAdminId")
    private Admin admin;

    private String ImageName;
    @Lob//to store large binary object
    @Column(name="imageData")
    @JsonProperty("imageData")
    private byte[] imageData;

    @Column(nullable = false)
    private Date lastDateToRegister;
    @Column(nullable = false)
    private double registrationFee;

    public Event(Long id, String title, String description, Date heldDate, LocalDateTime postDateTime, Admin admin, String imageName, byte[] imageData, Date lastDateToRegister, double registrationFee) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.heldDate = heldDate;
        this.postDateTime = postDateTime;
        this.admin = admin;
        ImageName = imageName;
        this.imageData = imageData;
        this.lastDateToRegister = lastDateToRegister;
        this.registrationFee = registrationFee;
    }

    public Event(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Date getHeldDate() {
        return heldDate;
    }

    public void setHeldDate(Date heldDate) {
        this.heldDate = heldDate;
    }

    public LocalDateTime getPostDateTime() {
        return postDateTime;
    }

    public void setPostDateTime(LocalDateTime postDateTime) {
        this.postDateTime = postDateTime;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public String getImageName() {
        return ImageName;
    }

    public void setImageName(String imageName) {
        ImageName = imageName;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    public Date getLastDateToRegister() {
        return lastDateToRegister;
    }

    public void setLastDateToRegister(Date lastDateToRegister) {
        this.lastDateToRegister = lastDateToRegister;
    }

    public double getRegistrationFee() {
        return registrationFee;
    }

    public void setRegistrationFee(double registrationFee) {
        this.registrationFee = registrationFee;
    }

    @PrePersist
    public void setEventDateAndTime(){
        postDateTime=LocalDateTime.now();
    }

}
