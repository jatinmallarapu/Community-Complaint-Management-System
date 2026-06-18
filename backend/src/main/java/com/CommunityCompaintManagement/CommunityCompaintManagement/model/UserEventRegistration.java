package com.CommunityCompaintManagement.CommunityCompaintManagement.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class UserEventRegistration {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String eventTitle;

    @ManyToOne
    @JoinColumn(name="user_id")
    User user;


    private LocalDateTime registrationDateTime;

    public UserEventRegistration(int id, String eventTitle, User user, LocalDateTime registrationDateTime) {
        this.id = id;
        this.eventTitle = eventTitle;
        this.user = user;
        this.registrationDateTime = registrationDateTime;
    }

    public UserEventRegistration(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getRegistrationDateTime() {
        return registrationDateTime;
    }

    public void setRegistrationDateTime(LocalDateTime registrationDateTime) {
        this.registrationDateTime = registrationDateTime;
    }

    @PrePersist
    public void setRegistrationDateTime(){
        this.registrationDateTime=LocalDateTime.now();
    }
}

