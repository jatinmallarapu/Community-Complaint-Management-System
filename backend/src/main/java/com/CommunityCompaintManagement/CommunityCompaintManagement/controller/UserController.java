package com.CommunityCompaintManagement.CommunityCompaintManagement.controller;

import com.CommunityCompaintManagement.CommunityCompaintManagement.model.*;
import com.CommunityCompaintManagement.CommunityCompaintManagement.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500", allowCredentials = "true")
@RequestMapping("/CCM")
public class UserController {

    @Autowired
    UserService service;


    @GetMapping("/greet")
    public String name(){
        return "HI";
    }

    @GetMapping("/csrfToken")
    public CsrfToken getToken(HttpServletRequest request){
        // returns JSON: {token, parameterName, headerName}
        return (CsrfToken) request.getAttribute("_csrf");
    }

    @PostMapping("/login")
    public String login(@RequestBody User user, HttpServletRequest request){
        String result = service.login(user);
        if(result.equals("Success")){

            return "Login Successful";
        }
        return result;
    }

    @PostMapping("/registeruser")
    public String registerUser(@RequestBody User user){
        return service.registerUser(user);
    }

    @GetMapping("/userdetails/{name}")
    public User getUserDetails(@PathVariable String name) {
        return service.getByUsername(name);
    }

    @PostMapping("/registercomplaint/{username}")
    public String registerComplaint(@RequestBody String complaint, @PathVariable String username){
        return service.registerComplaint(complaint, username);
    }

    @GetMapping("/viewcomplaints/{username}")
    public List<Complaint> viewComplaints(@PathVariable String username){
        return service.viewComplaints(username);
    }

    @GetMapping("/getComplaintsByUser/{userId}")
    public List<Complaint> getComplaintsByUser(@PathVariable int userId) {
        return service.getComplaintsByUser(userId);
    }

    @GetMapping("/getnotification/{username}")
    public Notification getNotification(@PathVariable String username){
        return service.getNotification(username);

    }

    @DeleteMapping("/deletecomplaint/{complaintId}")
    public String deleteCompliant(@PathVariable int complaintId){
        return service.deleteComplaint(complaintId);
    }

    @PutMapping("/editcomplaint")
    public void editComplaint(@RequestParam int complaintId, @RequestBody String complaint){
        service.editComplaint(complaintId,complaint);
    }

    @GetMapping("/getuserid")
    public int getUserId(@RequestParam String username){
        return service.getUserId(username);
    }

    @GetMapping("/getallnotifications/{userId}")
    public List<Notification> getAllNotification(@PathVariable int userId){
        return service.getAllNotification(userId);
    }

    @DeleteMapping("/deletenotification")
    public void deleteNotification(@RequestParam int notificationId){
        service.deleteNotification(notificationId);
    }

    @DeleteMapping("/deletecomplaintbasedonresolvednotification")
    public void deleteComplaintBasedOnResolvedNotification(@RequestBody String message){
        service.deleteComplaintBasedOnResolvedNotification(message);
    }



    @GetMapping("/getevent")
    public List<Event> getAllEvents(){
        return service.getAllEvents();
    }

    @PostMapping("/registerevent")
    public String registerEvent(@RequestBody UserEventRegistration userregister){
        return service.register(userregister);
    }

    @GetMapping("/allannouncments")
    public List<Announcment> getAllAnnouncments(){
        return service.getAllAnnouncments();
    }


    @GetMapping("/getalluserids")
    public List<Integer> getAllUserIds(){
        return service.getAllUserIds();
    }

    @GetMapping("/getalluseradminmessages")
    public List<Message> getAllUserAdminMessages(@RequestParam int userId){
        return service.getAllUserAdminMessages(userId);

    }


}
