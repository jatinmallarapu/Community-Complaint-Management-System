package com.CommunityCompaintManagement.CommunityCompaintManagement.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String password;


    @Column(nullable = false)
    private String apartmentlocation;

    @Column(nullable = false)
    private String apartmentnumber;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
    private Date dateofbirth;

    @Column(nullable = false)
    private final String role="USER";


    public User(int id, String name, String email, String password, String complaint, String apartmentlocation, String apartmentnumber, Date dateofbirth) {
        this.id = id;
        this.email=email;
        this.name = name;
        this.password = password;
        this.apartmentlocation=apartmentlocation;
        this.apartmentnumber=apartmentnumber;
        this.dateofbirth=dateofbirth;
    }
    public User(){

    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public String getApartmentlocation() {
        return apartmentlocation;
    }

    public void setApartmentlocation(String apartmentlocation) {
        this.apartmentlocation = apartmentlocation;
    }

    public String getApartmentnumber() {
        return apartmentnumber;
    }

    public void setApartmentnumber(String apartmentnumber) {
        this.apartmentnumber = apartmentnumber;
    }

    public Date getDateofbirth() {
        return dateofbirth;
    }

    public void setDateofbirth(Date dateofbirth) {
        this.dateofbirth = dateofbirth;
    }
}
