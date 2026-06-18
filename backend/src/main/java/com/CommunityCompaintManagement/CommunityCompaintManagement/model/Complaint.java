package com.CommunityCompaintManagement.CommunityCompaintManagement.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name="complaints")
public class Complaint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String complaint;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;



    @Column(nullable = false)
    private LocalDate complaintDate;

    @Column(nullable = false)
    private LocalTime complaintTime;

    @Column(nullable = false)
    private String complaintStatus="UNSOLVED";

    public Complaint(int id, String complaint, User user) {
        this.id = id;
        this.complaint = complaint;
        this.user = user;
    }

    public Complaint(){

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComplaint() {
        return complaint;
    }

    public void setComplaint(String complaint) {
        this.complaint = complaint;
    }



    public LocalDate getComplaintDate() {
        return complaintDate;
    }

    public void setComplaintDate(LocalDate complaintDate) {
        this.complaintDate = complaintDate;
    }

    public LocalTime getComplaintTime() {
        return complaintTime;
    }

    public void setComplaintTime(LocalTime complaintTime) {
        this.complaintTime = complaintTime;
    }

    @PrePersist
    public void setComplaintTimestamps(){
        this.complaintDate=LocalDate.now();
        this.complaintTime=LocalTime.now();

    }

    public String getComplaintStatus() {
        return complaintStatus;
    }

    public void setComplaintStatus(String complaintStatus) {
        this.complaintStatus = complaintStatus;
    }
}

