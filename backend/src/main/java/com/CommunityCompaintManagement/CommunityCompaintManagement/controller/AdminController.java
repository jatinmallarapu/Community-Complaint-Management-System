package com.CommunityCompaintManagement.CommunityCompaintManagement.controller;

import com.CommunityCompaintManagement.CommunityCompaintManagement.model.Message;
import com.CommunityCompaintManagement.CommunityCompaintManagement.model.Notification;
import com.CommunityCompaintManagement.CommunityCompaintManagement.service.AdminService;
import com.CommunityCompaintManagement.CommunityCompaintManagement.model.Complaint;
import com.CommunityCompaintManagement.CommunityCompaintManagement.model.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/CCM/admin")
public class AdminController {

    @Autowired
    AdminService service;



    @PostMapping("/login")
    public String login(@RequestBody Admin admin){
        return service.login(admin);
    }

    @GetMapping("/viewcomplaints")
    public List<Complaint> viewComplaints(){
        return service.viewComplaints();
    }



    @PutMapping("/editcomplaintstatus")
    public String editComplaintStatus(@RequestParam int complaintId, @RequestBody String status){
        return service.editComplaintStatus(complaintId,status);
    }

    @GetMapping("/getadminid")
    public int getAdminId(@RequestParam String username){
        return service.getAdminId(username);
    }

    @PostMapping("/addnotification")
    public void addNotification(@RequestBody Notification notification){
        service.addNotification(notification);
    }



    @PostMapping("/addevent")
    public ResponseEntity<String> addEvent(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("heldDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date heldDate,
            @RequestParam("adminId") int adminId,
            @RequestParam(value = "image", required = false) MultipartFile imageFile,
            @RequestParam("lastDateToRegister") @DateTimeFormat(pattern = "yyyy-MM-dd") Date lastDateToRegister,
            @RequestParam("registrationFee") double registrationFee ) throws IOException {

        service.addEvent(title, description, heldDate, adminId, imageFile, lastDateToRegister,registrationFee);
        return ResponseEntity.ok("Event added successfully");
    }

    @GetMapping("/getalladminusermessages")
    public List<Message> getAllAdminUserMessages(@RequestParam int adminId){
        return service.getAllAdminUserMessages(adminId);

    }
}
