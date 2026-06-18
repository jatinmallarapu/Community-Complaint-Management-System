package com.CommunityCompaintManagement.CommunityCompaintManagement.service;

import com.CommunityCompaintManagement.CommunityCompaintManagement.model.*;
import com.CommunityCompaintManagement.CommunityCompaintManagement.model.UserEventRegistration;
import com.CommunityCompaintManagement.CommunityCompaintManagement.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService {

    @Autowired
    UserRepository repo;

    @Autowired
    ComplaintRepository crepo;

    @Autowired
    NotificationRepository nrepo;

    @Autowired
    MessageRespository mrepo;

    int complaintId;

    @Autowired
    EventRepository erepo;

    @Autowired
    UserEventRegistrationRepository uerrepo;

    @Autowired
    AnnouncmentRepository annrepo;

    public String login(User user){

        User dbUser = repo.findByname(user.getName());
        if(dbUser != null && dbUser.getPassword().equals(user.getPassword()) && dbUser.getRole().equals("USER")) {
            return "Success";
        }
        return "Invalid username or password";
    }

    public String registerUser(User user){
        if(user!=null) {
            repo.save(user);
            return "User Registered";
        }
        else{
            return "Enter correct details";
        }
    }

    public User getByUsername(String name) {
        return repo.findByname(name);
    }

    public String registerComplaint(String complaint, String username) {
        Complaint comp=new Complaint();
        User user = repo.findByname(username);
        if(user!=null) {
            comp.setUser(user);
            comp.setComplaint(complaint);
            //comp.setComplaintDate(LocalDate.now());
            //comp.setComplaintTime(LocalTime.now());
            crepo.save(comp);
            return "Complaint Registered";
        }
        else{
            return "There is a problem in fetching";
        }
    }

    public List<Complaint> viewComplaints(String username) {
        User user=repo.findByname(username);
        if(user!=null) {
            int userId = user.getId();
            return crepo.findAllByUserId(userId);
        }
        return null;
    }

    public List<Complaint> getComplaintsByUser(int userId) {
        return crepo.findByUserId(userId);
    }

    public Notification getNotification(String username) {
        User user=repo.findByname(username);
        if(user!=null) {
            Notification notification = nrepo.findByuser(user);
            return notification;
        }
        return null;
    }

    public String deleteComplaint(int complaintId) {
        crepo.deleteById(complaintId);
        return "Deleted Successfully";
    }

    public void editComplaint(int complaintId, String complaint) {
        Complaint dbcomplaint = crepo.findById(complaintId).orElseThrow();
        dbcomplaint.setComplaint(complaint);
        crepo.save(dbcomplaint);
    }

    public int getUserId(String username) {
        User user = repo.findByname(username);
        if(user==null){
            throw new RuntimeException("Admin not found with name: " + username);
        }
        return user.getId();
    }

    public List<Notification> getAllNotification(int userId) {
        User dbUser=repo.findById(userId).orElseThrow();
        return nrepo.getAllByuser(dbUser);
    }

    public void deleteNotification(int notificationId) {
        nrepo.deleteById(notificationId);
    }

    public void deleteComplaintBasedOnResolvedNotification(String message) {
        Pattern pattern = Pattern.compile("\\d+"); // Matches one or more digits
        Matcher matcher = pattern.matcher(message);

        while (matcher.find()) {
            String extractedNumberString = matcher.group();
            try {
                complaintId = Integer.parseInt(extractedNumberString);
                System.out.println("Extracted integer: " + complaintId);
            } catch (NumberFormatException e) {
                // This catch block might not be strictly necessary if \\d+ ensures valid numbers
                System.out.println("Error parsing extracted number: " + extractedNumberString);
            }
        }
        if(message.contains("RESOLVED")) {
            crepo.deleteById(complaintId);
        }

    }



    public List<Event> getAllEvents() {
        return erepo.findAll();
    }

    public String register(UserEventRegistration userregister){
        uerrepo.save(userregister);
        return "User "+userregister.getUser().getName()+" is registered";
    }

    public List<Announcment> getAllAnnouncments() {
        return annrepo.findAll();
    }

    public List<Integer> getAllUserIds() {
        List<User> userList=repo.findAll();
        List<Integer> userIdsList=new ArrayList<>();
        for(User user: userList){
            userIdsList.add(user.getId());
        }
        return userIdsList;
    }

    public List<Message> getAllUserAdminMessages(int userId) {
        return mrepo.findAllByuserId(userId);
    }


}
