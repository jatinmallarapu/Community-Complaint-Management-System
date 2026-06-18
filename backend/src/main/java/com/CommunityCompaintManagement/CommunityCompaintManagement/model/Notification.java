package com.CommunityCompaintManagement.CommunityCompaintManagement.model;

import jakarta.persistence.*;
import org.aspectj.weaver.ast.Not;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name="notification")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String message;

    @ManyToOne
    @JoinColumn(name="touser_id")
    private User user;


    @ManyToOne
    @JoinColumn(name="createdbyadmin_id")
    private Admin admin;

    @Column(nullable = false)
    private LocalDateTime sentat;

    public Notification(int id, String message, User user, Admin admin, LocalDateTime sentat) {
        this.id = id;
        this.message = message;
        this.user = user;
        this.admin = admin;
        this.sentat = sentat;
    }

    public Notification(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getSentat() {
        return sentat;
    }

    public void setSentat(LocalDateTime sentat) {
        this.sentat = sentat;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }



    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    @PrePersist
    public void setNotificationTimestamps(){
        this.sentat=LocalDateTime.now();
    }
}
