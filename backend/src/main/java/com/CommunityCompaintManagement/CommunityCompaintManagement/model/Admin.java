package com.CommunityCompaintManagement.CommunityCompaintManagement.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "admin")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int adminId;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String adminname;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String adminlocation;

    @Column(nullable = false)
    private Date admindateofbirth;

    @Column(nullable = false)
    private final String role="ADMIN";

    public Admin(int adminId, String email, String adminname, String password, String adminlocation, Date admindateofbirth) {
        this.adminId = adminId;
        this.email = email;
        this.adminname = adminname;
        this.password = password;
        this.adminlocation = adminlocation;
        this.admindateofbirth = admindateofbirth;
    }

    public Admin(){}

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdminname() {
        return adminname;
    }

    public void setAdminname(String adminname) {
        this.adminname = adminname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAdminlocation() {
        return adminlocation;
    }

    public void setAdminlocation(String adminlocation) {
        this.adminlocation = adminlocation;
    }

    public Date getAdmindateofbirth() {
        return admindateofbirth;
    }

    public void setAdmindateofbirth(Date admindateofbirth) {
        this.admindateofbirth = admindateofbirth;
    }

    public String getRole() {
        return role;
    }
}
