package com.CommunityCompaintManagement.CommunityCompaintManagement.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private int userId;
    @Column(nullable = false)
    private int adminId;

    @Column(nullable = false)
    private String content;
    private String region;
    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy, h:mm:ss a")
    private LocalDateTime timestamp;

    private String response;

    public Message(int id, int userId, int adminId, String content, String region, LocalDateTime timestamp, String response) {
        this.id = id;
        this.userId = userId;
        this.adminId = adminId;
        this.content = content;
        this.region = region;
        this.timestamp = timestamp;
        this.response = response;
    }

    public Message(){

    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    @PrePersist
    public void setTimestamp(){
        this.timestamp=LocalDateTime.now();
    }
}
