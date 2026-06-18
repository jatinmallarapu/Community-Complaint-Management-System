package com.CommunityCompaintManagement.CommunityCompaintManagement.service;

import com.CommunityCompaintManagement.CommunityCompaintManagement.model.*;
import com.CommunityCompaintManagement.CommunityCompaintManagement.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Service
public class AdminService {

    @Autowired
    AdminRepository arepo;

    @Autowired
    ComplaintRepository crepo;

    @Autowired
    NotificationRepository nrepo;

    @Autowired
    UserRepository urepo;


    @Autowired
    MessageRespository mrepo;

    @Autowired
    EventRepository erepo;
    public String login(Admin admin) {
        Admin dbAdmin = arepo.findByadminname(admin.getAdminname());
        if(dbAdmin != null && dbAdmin.getPassword().equals(admin.getPassword()) && dbAdmin.getRole().equals("ADMIN")) {
            return "Success";
        }
        else{
            return "Invalid username or password";
        }

    }

    public List<Complaint> viewComplaints() {
        return crepo.findAll();
    }

    public String editComplaintStatus(int complaintId, String status) {
        Complaint complaint= crepo.findById(complaintId).orElseThrow();
        complaint.setComplaintStatus(status);
        complaint.setComplaintDate(LocalDate.now());
        complaint.setComplaintTime(LocalTime.now());
        crepo.save(complaint);
        return status;
    }

    public int getAdminId(String username) {
        Admin admin = arepo.findByadminname(username);
        if (admin == null) {
            throw new RuntimeException("Admin not found with name: " + username);

        }
        return admin.getAdminId();
    }

    public void addNotification(Notification notification) {
        // Fetch actual user and admin entities
        User user = urepo.findById(notification.getUser().getId()).orElse(null);
        Admin admin = arepo.findById(notification.getAdmin().getAdminId()).orElse(null);

        if (user == null || admin == null){
            throw new RuntimeException("Admin not found");
        }

        notification.setUser(user);
        notification.setAdmin(admin);
        nrepo.save(notification);

    }



    public void addEvent(String title, String description, Date heldDate, int adminId, MultipartFile image,Date lastDateToRegister, double registrationFee) throws IOException {
        Admin admin = arepo.findById(adminId)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        Event event = new Event();
        event.setTitle(title);
        event.setDescription(description);
        event.setHeldDate(heldDate);
        event.setAdmin(admin);
        event.setLastDateToRegister(lastDateToRegister);
        event.setRegistrationFee(registrationFee);

        if (image != null && !image.isEmpty()) {
            event.setImageName(image.getOriginalFilename());
            event.setImageData(image.getBytes());
        }

        erepo.save(event);
    }



    /*
    public String sendNotification(Notification notification, String adminname) {
        Admin admin=arepo.findByadminname(adminname);
        notification.setAdmin(admin);
        nrepo.save(notification);
        return "Notification sent to user";
    }

     */

    public List<Message> getAllAdminUserMessages(int adminId) {
        return mrepo.findAllByadminId(adminId);
    }
}

